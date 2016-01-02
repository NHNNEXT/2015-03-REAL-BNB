package net.balbum.baby.VO;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by hyes on 2015. 11. 10..
 */

@Parcel
public class GeneralCardVo{

    public String modifiedDate;
    public String cardImg;
    public long cid;
    public String content;
    public List<BabyVo> babies;
    public List<NamesVo> names;
    public String type;
    public boolean isSelected;

    public GeneralCardVo() {

    }



    public GeneralCardVo(String content, String cardImg, String modifiedDate, Long cid) {
        this.content = content;
        this.cardImg = cardImg;
        this.modifiedDate = modifiedDate;
        this.cid = cid;
    }

    public GeneralCardVo(String cardImg, long cid, String content, String modifiedDate, List<Long> names, String type) {
        this.cardImg = cardImg;
        this.cid = cid;
        this.content = content;
        this.modifiedDate = modifiedDate;
       // this.names = new ArrayList<Long>();
        for(int i = 0 ; i < babies.size(); i++) {
            names.add(babies.get(i).bId);
        }
        this.type = type;
    }

    public List<BabyVo> getBabies() {
        return babies;
    }

    public void setBabies(List<BabyVo> babies) {
        this.babies = babies;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<NamesVo> getNames() {
        return names;
    }

    public void setNames(List<NamesVo> names) {
        this.names = names;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
