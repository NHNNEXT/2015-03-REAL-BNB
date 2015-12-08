package net.balbum.baby.VO;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.annotations.Required;

/**
 * Created by hyes on 2015. 11. 10..
 */
public class GeneralCardVo{

    //카드 생성일, 기록대상일, 아이이름[], 메모, 작성자, 상태(삭제여부 boolean)
    @Required
    public String modifiedDate;
    public String imgUrl;
    public long cId;
    public String content;
    public List<Long> babies;
    public List<String> names;

    public GeneralCardVo() {

    }

    public GeneralCardVo(String content, String imgUrl, String modifiedDate) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.modifiedDate = modifiedDate;
    }

    public GeneralCardVo(List<Long> babies, long cId, String content, String imgUrl, String modifiedDate) {
        Log.d("test", "ㅎㄸㄸㄸㄷ옸따");
        this.babies = babies;
        this.cId = cId;
        this.content = content;
        this.imgUrl = imgUrl;
        this.modifiedDate = modifiedDate;
        this.names = new ArrayList<String>();
        for(int i = 0 ; i < babies.size(); i++){
            names.add(babies.get(i).toString());
        }

    }
}
