package com.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.commen.SystemException;
import com.constants.ResultCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义MybatisPlus工具类
 *
 * @author Joy Yang
 */
public class MybatisPlusUtil {
    /**
     * 简化利用mp查询操作
     * @param object
     * @return
     */
    public static Map<String,Object> getMap(Object ...object) {
        if(object.length%2==1) {
            throw new SystemException(ResultCode.ENCODING_ANOMALY);
        }else{
            Map<String,Object> mp = new HashMap<>();
            String key = null;
            for (int i = 0; i < object.length; i++) {
                if(i%2==0){
                    if(object[i] instanceof String){
                        key = (String) object[i];
                    }else{
                        throw new SystemException(ResultCode.ENCODING_ANOMALY);
                    }
                }else{
                    mp.put(key, object[i]);
                }
            }
            return mp;
        }
    }

    /**
     * 简化利用QueryWrapperEq查询操作
     * @param object
     * @return
     */
    public static <T> QueryWrapper<T> queryWrapperEq(Object ...object) {
        if(object.length%2==1) {
            throw new SystemException(ResultCode.ENCODING_ANOMALY);
        }else{
            QueryWrapper<T> queryWrapper = new QueryWrapper<>();
            String key = null;
            for (int i = 0; i < object.length; i++) {
                if(i%2==0){
                    if(object[i] instanceof String){
                        key = (String) object[i];
                    }else{
                        throw new SystemException(ResultCode.ENCODING_ANOMALY);
                    }
                }else{
                    queryWrapper.eq(key, object[i]);
                }
            }
            return queryWrapper;
        }
    }

}