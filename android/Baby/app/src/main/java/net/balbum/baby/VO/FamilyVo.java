package net.balbum.baby.VO;

/**
 * Created by hyes on 2015. 12. 12..
 */

public class FamilyVo {

    public Long fId;
    public String familyImage;
    public String familyName;
    public String familyRole;
    public String familyEmail;
    public boolean isAccepted;

    public FamilyVo(String familyEmail, String familyImage, String familyName, String familyRole, boolean isAccepted) {
        this.familyEmail = familyEmail;
        this.familyImage = familyImage;
        this.familyName = familyName;
        this.familyRole = familyRole;
        this.isAccepted = isAccepted;
    }
}
