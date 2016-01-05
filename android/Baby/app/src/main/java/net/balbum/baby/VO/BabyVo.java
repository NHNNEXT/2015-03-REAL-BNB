package net.balbum.baby.VO;

import org.parceler.Parcel;

/**
 * Created by hyes on 2015. 12. 12..
 */
@Parcel
public class BabyVo {

    public Long bid;
    public String babyImg;
    public String babyName;
    public String babyBirth;
    public String babyGender;
    public String babyDate;

    public BabyVo() {
    }

    public BabyVo(String babyName, String babyBirth, String babyGender, String image, Long bid) {
        this.babyName = babyName;
        this.babyBirth = babyBirth;
        this.babyGender = babyGender;
        this.babyImg = image;
        this.bid = bid;
    }

    public BabyVo(String babyName, String babyBirth, String babyGender, String image) {
        this.babyName = babyName;
        this.babyBirth = babyBirth;
        this.babyGender = babyGender;
        this.babyImg = image;
    }

    public BabyVo(String babyBirth, String babyDate, String babyGender, String babyImg, String babyName, Long bid) {
        this.babyBirth = babyBirth;
        this.babyDate = babyDate;
        this.babyGender = babyGender;
        this.babyImg = babyImg;
        this.babyName = babyName;
        this.bid = bid;
    }
}

