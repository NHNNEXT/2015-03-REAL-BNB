package net.balbum.baby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kakao.AppActionBuilder;
import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;

import net.balbum.baby.Util.Define;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hyes on 2015. 12. 29..
 */
public class CardShareActivity extends AppCompatActivity {

    KakaoLink kakaoLink;
    KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;
    Long cid;
    GeneralCardVo card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        cid = intent.getLongExtra("card", 0);
        Log.d("test", "cid " + cid);

        initKakaoLink();
        setCard();

    }

    private void setCard() {
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getOneCard(cid, new Callback<GeneralCardVo>() {
            @Override
            public void success(GeneralCardVo generalCardVo, Response response) {
                card = generalCardVo;
                sendKakaoMessage();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
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
            if(!card.getContent().matches("")){
                kakaoTalkLinkMessageBuilder.addText(card.getContent());
            }
            kakaoTalkLinkMessageBuilder.addImage(Define.URL + card.cardImg, 128, 128);
//            http://dev.balbum.net/imgs/dummy/baby2.jpeg"
            kakaoTalkLinkMessageBuilder.addWebLink("BALBUM 홈페이지로 이동", "http://www.balbum.net/card/"+card.linkUrl);
            kakaoTalkLinkMessageBuilder.addAppButton("BALBUM으로 이동", new AppActionBuilder()
                    .setAndroidExecuteURLParam("target=main")
                    .setIOSExecuteURLParam("target=main", AppActionBuilder.DEVICE_TYPE.PHONE).build());
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(), this);


        }catch(KakaoParameterException e){
            e.printStackTrace();
        }
    }
}
