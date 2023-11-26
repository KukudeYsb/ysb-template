package com.data.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 杨世博
 */
@Data
public class Token implements Serializable {


    private String accessToken;
    private String refreshToken;

    public Token() {
    }

    public Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
