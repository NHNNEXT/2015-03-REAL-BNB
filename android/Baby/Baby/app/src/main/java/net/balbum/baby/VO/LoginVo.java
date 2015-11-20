package net.balbum.baby.VO;

/**
 * Created by hyes on 2015. 11. 15..
 */
public class LoginVo {

    private String email;
    private String password;
    private String token;

    public LoginVo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

}
