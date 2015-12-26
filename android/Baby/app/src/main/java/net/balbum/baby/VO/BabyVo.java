package net.balbum.baby.VO;

import org.parceler.Parcel;

/**
 * Created by hyes on 2015. 12. 12..
 */
@Parcel
public class BabyVo {

    public long bId;
    public String babyImg;
    public String babyName;
    public String babyBirth;
    public String babyGender;

    public BabyVo() {
    }

    public BabyVo(String babyName, String babyBirth, String babyGender, String image, Long bId) {
        this.babyName = babyName;
        this.babyBirth = babyBirth;
        this.babyGender = babyGender;
        this.babyImg = image;
        this.bId = bId;
    }

    public BabyVo(String babyName, String babyBirth, String babyGender, String image) {
        this.babyName = babyName;
        this.babyBirth = babyBirth;
        this.babyGender = babyGender;
        this.babyImg = image;
    }
}

