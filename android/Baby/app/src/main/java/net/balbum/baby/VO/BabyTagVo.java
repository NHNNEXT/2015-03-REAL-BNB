package net.balbum.baby.VO;

import java.io.File;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class BabyTagVo {
    public File image;
    public String name;

    public BabyTagVo(File image, String name) {
        this.image = image;
        this.name = name;
    }
}
