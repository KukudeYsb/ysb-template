package com.constants;

/**
 * 返回码定义
 * 规定:
 * #200表示成功
 * #400表示错误
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * #后面对什么的操作自己在这里注明就行了
 *
 * @author : 杨世博
 * @date: Created in 23:55 2023/5/30
 */
public enum ResultCode implements CustomizeResultCode {
    /* 成功 */
    SUCCESS(200, "成功"),
    /*错误*/
    ERROR(400, "错误失败"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    GOODS_NOT_EXIST(1003, "商品不存在"),
    USER_NAME_EXIST(1004,"用户名称已存在"),
    TOKEN_EXCEPTION(1005,"token异常"),
    TOKEN_TIME_OUT(1006,"token超时"),
    REFUSE_TO_ADD(1007,"该用户拒绝添加好友"),
    FILE_IS_EXIST(1008,"文件已存在，是否替换源文件？"),
    VIDEO_NOT_FIND(1009, "该视频已丢失"),

    /* 用户错误 */
    PASSWORD_ERROR(2001,"密码错误"),
    USER_ACCOUNT_ERROR(2002, "账号不存在"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    /*运行时异常*/
    ENCODING_ANOMALY(3001,"系统编码错误"),
    NULL_POINT_EXCEPTION(3002, "空指针异常"),
    USE_MESSAGE_ERROR(3003, "用户信息异常"),
    VIDEO_LOADING_ERROR(3004, "视频信息加载失败"),
    UPLOAD_ERROR(3005, "上传失败"),
    ARITHMETIC_EXCEPTION(9001, "算数异常");
    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
