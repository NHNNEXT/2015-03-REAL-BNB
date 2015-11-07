package cafe.nhnnext.org.babydear.vo;

import cafe.nhnnext.org.babydear.R;

/**
 * Created by hyes on 2015. 11. 3..
 */
public class CardGeneralModel {
    public String date;
    public int img;
    String tag1, tag2;
    public String text;

    public CardGeneralModel(String date, String tag1, String tag2, String text) {
        this.date = date;
        img = R.mipmap.ic_launcher;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.text = text;
    }

    public CardGeneralModel(String date, int img, String tag1, String tag2, String text) {
        this.date = date;
        this.img = img;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.text = text;
    }

    public CardGeneralModel(String date, int img, String tag1, String text) {
        this.date = date;
        this.img = img;
        this.tag1 = tag1;
        this.text = text;
    }

    public CardGeneralModel(String date, String tag1, String text) {
        this.date = date;
        this.tag1 = tag1;
        this.text = text;
    }

}
