package com.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.commen.SystemException;
import com.constants.RedisConstant;
import com.constants.ResultCode;
import com.data.bo.Token;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil implements InitializingBean {


    public static String USERNAME = "USERNAME";
    public static Map<String, Object> header = new HashMap<>();
    public static Algorithm algorithm;
    static {
        header.put("type","Jwt");
        header.put("alg","HS256");
    }

    @Autowired
    RedisUtil redisUtil;

    private String accessTokenHeader = RedisConstant.ACCESS_TOKEN;
    private String refreshTokenHeader = RedisConstant.REFRESH_TOKEN;
    private Long accessTokenExpire = RedisConstant.ACCESS_TOKEN_EXPIRE;
    private Long refreshTokenExpire = RedisConstant.REFRESH_TOKEN_EXPIRE;
    private String secret = RedisConstant.SOCIAL;

    @Override
    public void afterPropertiesSet() throws Exception {
        algorithm = Algorithm.HMAC256(secret);
    }
    /**
     * 生成AccessTokenJWT
     * @param username 用户名
     * @return
     */
    public String generateAccessToken(String username) {
        Date nowDate = new Date();
        //设置token过期时间
        Date expireDate = new Date(nowDate.getTime() + 1000 * accessTokenExpire);
        //秘钥是密码则省略
        return JWT.create()
                .withHeader(header)
                .withSubject(username)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    /**
     * 生成refreshTokenJWT
     * @param username
     * @return
     */
    public String generateRefreshToken(String username) {
        Date nowDate = new Date();
        //设置token过期时间
        Date expireDate = new Date(nowDate.getTime() + 1000 * refreshTokenExpire);
        //秘钥是密码则省略
        return JWT.create()
                .withHeader(header)
                .withSubject(username)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    // 解析JWT
    public DecodedJWT getClaimsByToken(String token) {
        if (token==null || "".equals(token.trim())){
            throw new SystemException(ResultCode.TOKEN_EXCEPTION);
        }
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    // 判断JWT是否过期
    public boolean isTokenExpired(DecodedJWT decodedJWT) {
        final Date currentTime = new Date();
        final Date expirationTime = decodedJWT.getExpiresAt();
        // 比较当前时间与过期时间
        if (expirationTime != null && expirationTime.before(currentTime)) {
            // Token 已过期
            return true;
        } else {
            // Token 未过期
            return false;
        }

    }


    /**
     * 创建token并存入redis
     * @param username
     * @return
     */
    public Token createTokenAndSaveToKy(String username){
        final String accessToken = generateAccessToken(username);
        final String refreshToken = generateRefreshToken(username);
        final Token tokenPair = new Token(accessToken, refreshToken);
        //redis保存 方便鉴权
        String hKey = RedisConstant.SOCIAL+ RedisConstant.JWT_TOKEN+username;
        redisUtil.setObject(hKey, JsonUtil.object2StringSlice(tokenPair),refreshTokenExpire);
        return tokenPair;
    }

    /**
     * 获取用户名
     * @param request
     * @return
     */
    public String getSubject(HttpServletRequest request){
        return getClaimsByToken(request.getHeader(getRefreshTokenHeader())).getSubject();
    }

    /**
     * 退出时将Redis中的对象移除
     * @param username
     * @return
     */
    public boolean removeToken(String username){
        String hKey = RedisConstant.SOCIAL+ RedisConstant.JWT_TOKEN+username;
        return redisUtil.removeJson(hKey);
    }
}