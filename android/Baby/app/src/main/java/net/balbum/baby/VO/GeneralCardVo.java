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

}
