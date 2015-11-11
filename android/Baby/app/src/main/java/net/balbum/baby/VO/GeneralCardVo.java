package net.balbum.baby.VO;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hyes on 2015. 11. 10..
 */
public class GeneralCardVo {

    //카드 생성일, 기록대상일, 아이이름[], 메모, 작성자, 상태(삭제여부 boolean)
    public String createDate;
    public String recordDate;
    public File image;
    public ArrayList<String> names;
    public String memo;
    public String writer;
    public boolean isDeleted;

    public GeneralCardVo(String createDate, String recordDate, File image, ArrayList<String> names, String memo, String writer, boolean isDeleted) {
        this.createDate = createDate;
        this.recordDate = recordDate;
        this.image = image;
        this.names = names;
        this.memo = memo;
        this.writer = writer;
        this.isDeleted = isDeleted;
    }
}
