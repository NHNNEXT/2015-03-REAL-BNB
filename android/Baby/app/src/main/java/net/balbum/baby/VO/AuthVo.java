package net.balbum.baby.VO;

/**
 * Created by hyes on 2015. 11. 23..
 */
public class AuthVo {
    public String token;
    public String message;

    public AuthVo(String token, String message) {
        this.token = token;
        this.message = message;
    }
}
