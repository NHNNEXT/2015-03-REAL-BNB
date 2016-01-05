package net.balbum.baby.VO;

import java.util.List;

/**
 * Created by hyes on 2016. 1. 5..
 */
public class BabyTimelineVo {
    String token;
    List<Long> babies;

    public BabyTimelineVo(List<Long> babies, String token) {
        this.babies = babies;
        this.token = token;
    }
}
