package net.balbum.baby.VO;

import java.io.File;

/**
 * Created by hyes on 2015. 12. 12..
 */
public class BabyVo {
    public File image;
    public String baby_name;
    public String baby_birthday;
    public int baby_sex;

    public BabyVo(String baby_birthday, String baby_name, int baby_sex, File image) {
        this.baby_birthday = baby_birthday;
        this.baby_name = baby_name;
        this.baby_sex = baby_sex;
        this.image = image;
    }
}
