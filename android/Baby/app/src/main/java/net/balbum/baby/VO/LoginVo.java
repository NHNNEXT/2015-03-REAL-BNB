package net.balbum.baby.VO;

/**
 * Created by hyes on 2015. 11. 15..
 */
public class LoginVo {

    public String email;
    public String password;
    public String role;
    public String fb;

    public LoginVo(String role, String fb) {
        this.role = role;
        this.fb = fb;
    }

    public LoginVo(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
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
