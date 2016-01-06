package net.balbum.baby.VO;

import java.io.File;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class BabyTagVo {
    public File image;
    public String babyImg;
    public String name;
    public boolean isSelected;
    public Long bid;

    public BabyTagVo(String babyImg, Long bid, boolean isSelected, String name) {
        this.babyImg = babyImg;
        this.bid = bid;
        this.isSelected = isSelected;
        this.name = name;
    }

    public BabyTagVo(File image, String name) {
        this.image = image;
        this.name = name;
    }
}
