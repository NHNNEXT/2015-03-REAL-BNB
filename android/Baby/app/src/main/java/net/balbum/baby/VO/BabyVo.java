package net.balbum.baby.VO;

import java.io.File;

/**
 * Created by hyes on 2015. 12. 12..
 */
public class BabyVo {
    public enum Gender {
        GIRL(0), BOY(1), UNDEFINED(2);
        private final int value;
        Gender(int value){
            this.value = value;
        }
    }
    public File image;
    public String baby_name;
    public String baby_birthday;
    public Gender baby_gender;

    public BabyVo(String baby_birthday, String baby_name, Gender baby_gender, File image) {
        this.baby_birthday = baby_birthday;
        this.baby_name = baby_name;
        this.baby_gender = baby_gender;
        this.image = image;
    }
}
