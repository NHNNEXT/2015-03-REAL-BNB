package net.balbum.baby.VO;

import java.util.List;

/**
 * Created by hyes on 2015. 11. 17..
 */
public class CardFormVo {
    public Long cId;
    public String token;
    public List<Long> babies;
    public String content;
    public String modifiedDate;


    public CardFormVo(String content) {
        this.content = content;
    }
}
