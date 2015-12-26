package net.balbum.baby.VO;

/**
 * Created by hyes on 2015. 11. 15..
 */
public class LoginVo {

    public String email;
    public String password;
    public String role;
    public String fb_token;

    public LoginVo(String role, String fb_token) {
        this.role = role;
        this.fb_token = fb_token;
    }

    public LoginVo(String email, String role, String token) {
        this.email = email;
        this.role = role;
        this.fb_token = token;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
