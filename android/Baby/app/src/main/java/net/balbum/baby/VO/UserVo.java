package net.balbum.baby.VO;

/**
 * Created by hyes on 2015. 12. 12..
 */

public class UserVo {

    public long uid;
    public String userImg;
    public String userRole;
    public boolean isAccepted;

    public UserVo(String userImg, String userRole, boolean isAccepted) {
        this.userImg = userImg;
        this.userRole = userRole;
        this.isAccepted = isAccepted;
    }
}
