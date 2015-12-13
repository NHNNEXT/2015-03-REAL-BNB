package net.balbum.baby.VO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import io.realm.annotations.Required;

/**
 * Created by hyes on 2015. 11. 10..
 */


public class GeneralCardVo implements Parcelable{

    public enum Type{
        BAD("BAD"), NORMAL("NORMAL"), EVENT("EVENT");
        private final String value;
        Type(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    @Required
    public String modifiedDate;
    public String cardImg;
    public long cId;
    public String content;
    public List<BabyVo> babies;
    public List<Long> names;
    public Type type;

    public GeneralCardVo() {
//        babies = new ArrayList<>();
        names = new ArrayList<>();
    }

    public GeneralCardVo(Parcel in) {
        readFromParcel(in);
    }

    public GeneralCardVo(String content, String cardImg, String modifiedDate) {
        this.content = content;
        this.cardImg = cardImg;
        this.modifiedDate = modifiedDate;
    }

    public GeneralCardVo(String cardImg, long cId, String content, String modifiedDate, List<Long> names, Type type) {
        this.cardImg = cardImg;
        this.cId = cId;
        this.content = content;
        this.modifiedDate = modifiedDate;
        this.names = new ArrayList<Long>();
        for(int i = 0 ; i < babies.size(); i++) {
            names.add(babies.get(i).bId);
            this.type = type;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(modifiedDate);
        dest.writeString(cardImg);
        dest.writeLong(cId);
        dest.writeString(content);
        dest.writeValue(type);
//        dest.writeList(babies);
    }

    private void readFromParcel(Parcel in){
        modifiedDate = in.readString();
        cardImg = in.readString();
        cId = in.readLong();
        content = in.readString();
        type = (Type) in.readValue(Type.class.getClassLoader());
//        babies = new ArrayList<Long>();
//        in.readTypedList(babies, GeneralCardVo.CREATOR);
//        names = new ArrayList<String>();
//        for(int i = 0 ; i < babies.size(); i++){
//            names.add(babies.get(i).toString());
//        }

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public GeneralCardVo createFromParcel(Parcel in) {
            return new GeneralCardVo(in);
        }

        public GeneralCardVo[] newArray(int size) {
            return new GeneralCardVo[size];
        }
    };

}
