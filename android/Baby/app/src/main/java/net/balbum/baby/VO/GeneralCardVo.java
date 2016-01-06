package net.balbum.baby.VO;

import org.parceler.Parcel;

import java.util.ArrayList;
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
    public List<Long> names;
    public String type;
    public boolean isSelected;
    public String linkUrl;


    public GeneralCardVo() {

    }

    private void init() {
         this.names = new ArrayList<Long>();
    }

    public GeneralCardVo(String cardImg, Long cid, String content, String modifiedDate, List<BabyVo> babies, String type) {
        init();
        this.cardImg = cardImg;
        this.cid = cid;
        this.content = content;
        this.modifiedDate = modifiedDate;
        this.babies = babies;
        //getNames();
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public List<BabyVo> getBabies() {
        return babies;
    }
}
