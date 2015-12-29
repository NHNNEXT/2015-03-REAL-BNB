package net.balbum.baby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kakao.AppActionBuilder;
import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;

/**
 * Created by hyes on 2015. 12. 29..
 */
public class CardShareActivity extends AppCompatActivity {

    private KakaoLink kakaoLink;
    private KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Long cid = intent.getLongExtra("card", 0);
        Log.d("test", "cid " + cid);

        initKakaoLink();
        sendKakaoMessage();
    }


    private void initKakaoLink(){
        try{
            kakaoLink = KakaoLink.getKakaoLink(this);
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
        }catch(KakaoParameterException e){
            e.printStackTrace();
        }
    }


    private void sendKakaoMessage(){
        try{
            kakaoTalkLinkMessageBuilder.addText("@미녀들과 야수");
            kakaoTalkLinkMessageBuilder.addImage("http://dev.balbum.net/imgs/dummy/baby2.jpeg", 128, 128);
            kakaoTalkLinkMessageBuilder.addWebLink("홈 페이지 이동", "http://www.balbum.net");
            kakaoTalkLinkMessageBuilder.addAppButton("BALBUM으로 이동", new AppActionBuilder()
                    .setAndroidExecuteURLParam("target=main")
                    .setIOSExecuteURLParam("target=main", AppActionBuilder.DEVICE_TYPE.PHONE).build());
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(), this);
        }catch(KakaoParameterException e){
            e.printStackTrace();
        }
    }
}
