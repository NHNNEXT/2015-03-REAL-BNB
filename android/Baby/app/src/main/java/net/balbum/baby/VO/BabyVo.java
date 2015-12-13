package net.balbum.baby.VO;

/**
 * Created by hyes on 2015. 12. 12..
 */
public class BabyVo {
    public enum Gender {
        GIRL("GIRL"), BOY("BOY"), PREGNANCY("PREGNANCY"), UNDEFINED("UNDEFINED");
        private final String value;
        Gender(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
    public Long bId;
    public String babyImg;
    public String babyName;
    public String babyBirth;
    public Gender babyGender;

    public BabyVo() {
    }

    public BabyVo(String babyName, String babyBirth, Gender babyGender, String image, Long bId) {
        this.babyName = babyName;
        this.babyBirth = babyBirth;
        this.babyGender = babyGender;
        this.babyImg = image;
        this.bId = bId;
    }
}
