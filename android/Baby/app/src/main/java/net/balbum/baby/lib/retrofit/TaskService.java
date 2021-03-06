package net.balbum.baby.lib.retrofit;

import net.balbum.baby.VO.AuthVo;
import net.balbum.baby.VO.BabyTimelineVo;
import net.balbum.baby.VO.BabyVo;
import net.balbum.baby.VO.CardIdListVo;
import net.balbum.baby.VO.CardListVo;
import net.balbum.baby.VO.FamilyVo;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.VO.LoginVo;
import net.balbum.baby.VO.ResponseVo;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by hyes on 2015. 11. 15..
 */
public interface TaskService {

    @GET("/api/user/token")
    void tokenCheck(@Query("token") String token, Callback<ResponseVo> cb);

    @POST("/api/user/login")
    void createLogin(@Body LoginVo task, Callback<AuthVo> cb);

    @Multipart
    @POST("/api/user/signup/fb_token/image")
    void sendProfileImage(@Part("image")TypedFile file, @Part("token") String token, Callback<ResponseVo> cb);

    @POST("/api/user/signup/fb_token")
    void createSign(@Body LoginVo task, Callback<AuthVo> cb);

    @GET("/api/card/{cId}")
    void getOneCard(@Path("cId") Long cId, Callback<GeneralCardVo> cb);

//    @GET("/api/card")
//    void getCard(@Body GeneralCardVo)
//    @POST("/api/card")

    @Multipart
    @POST("/api/card")
    void createCard(@Part("image") TypedFile file, @Part("token") String token, @Part("bIds[0]") Long k, @Part("content") String content, @Part("modifiedDate") String date, @Part("type") String type, Callback<ResponseVo> cb);

    @Multipart
    @POST("/api/card/update")
    void updateCard(@Part("image") TypedFile file, @Part("token") String token, @Part("bIds[0]") Long k, @Part("content") String content, @Part("modifiedDate") String date, @Part("type") String type, @Part("cId") Long cId, Callback<ResponseVo> cb);


    //2명
    @Multipart
    @POST("/api/card")
    void createCard(@Part("image") TypedFile file, @Part("token") String token, @Part("bIds[0]") Long k, @Part("bIds[1]") Long l, @Part("content") String content, @Part("modifiedDate") String date, @Part("type") String type, Callback<ResponseVo> cb);

    @Multipart
    @POST("/api/card/update")
    void updateCard(@Part("image") TypedFile file, @Part("token") String token, @Part("bIds[0]") Long k, @Part("bIds[1]") Long l, @Part("content") String content, @Part("modifiedDate") String date, @Part("type") String type, @Part("cId") Long cId, Callback<ResponseVo> cb);

    //3명
    @Multipart
    @POST("/api/card")
    void createCard(@Part("image") TypedFile file, @Part("token") String token, @Part("bIds[0]") Long k, @Part("bIds[1]") Long l, @Part("bIds[2]") Long m, @Part("content") String content, @Part("modifiedDate") String date, @Part("type") String type, Callback<ResponseVo> cb);

    //4명
    @Multipart
    @POST("/api/card")
    void createCard(@Part("image") TypedFile file, @Part("token") String token, @Part("bIds[0]") Long k, @Part("bIds[1]") Long l, @Part("bIds[2]") Long m, @Part("bIds[3]") Long n, @Part("content") String content, @Part("modifiedDate") String date, @Part("type") String type, Callback<ResponseVo> cb);

    //5명
    @Multipart
    @POST("/api/card")
    void createCard(@Part("image") TypedFile file, @Part("token") String token, @Part("bIds[0]") Long k, @Part("bIds[1]") Long l, @Part("bIds[2]") Long m, @Part("bIds[3]") Long n, @Part("bIds[4]") Long o, @Part("content") String content, @Part("modifiedDate") String date, @Part("type") String type, Callback<ResponseVo> cb);


    @Multipart
    @POST("/api/card/update")
    void updateCard(@Part("image") TypedFile file, @Part("token") String token, @Part("bIds[0]") Long k, @Part("bIds[1]") Long l, @Part("bIds[2]") Long m, @Part("content") String content, @Part("modifiedDate") String date, @Part("type") String type, @Part("cId") Long cId, Callback<ResponseVo> cb);


    @GET("/api/card/delete")
    void deleteCard(@Query("cId") Long cId, Callback<ResponseVo> cb);

    @POST("/api/card/list")
    void getCardList(@Body CardIdListVo cardIdListVo, Callback<CardListVo> cb);

    @GET("/api/user/family/findFromMail")
    void findFamily(@Query("email") String email, @Query("token") String token, Callback<ResponseVo> cb);

//    @Multipart
//    @PUT("/api/card")
//    void updateCard(@Part("image") TypedFile file, @Part("cid") Long l, @Part("token") String token, @Part("bIds[0]") Long k, @Part("content") String content, @Part("modifiedDate") String date, Callback<ResponseVo> cb);

//    @Multipart
//    @POST("/api/card")
//    void createCard(@Body TypedFile file, @Body CardFormVo cardFormVo, Callback<ResponseVo> cb);

  //Body body로 나눠 보내보기!


    @GET("/api/card")
    void getCard(@Query("token") String token, Callback<CardListVo> cb);

    @Multipart
    @POST("/api/user/baby/create")
//    void createBabyInfo(@Part("image") TypedFile file, @Body BabyVo babyVo, Callback<ResponseVo> cb);
    void createBabyInfo(@Part("token") String token, @Part("image") TypedFile file,  @Part("babyName") String name, @Part("babyBirth") String birth,  @Part("babyGender") String gender, Callback<ResponseVo> cb);

    @GET("/api/user/baby")
    void getBabies(@Query("token") String token, Callback<ArrayList<BabyVo>> cb);

    @GET("/api/filter/baby")
    void filteringBaby(@Query("token") String token, @Query("bId") Long l,  Callback<CardListVo> cb);


//    @POST("/api/filter/babies")
//    void filteringBabies(@Part("token") String token, @Part("babies") Long[] k, Callback<CardListVo> cb);

    @POST("/api/filter/babies")
    void filteringBabies(@Body BabyTimelineVo babyTimeline, Callback<CardListVo> cb);

    @GET("/api/family")
    void getFamilyGlobalInfo(@Query("token") String token, Callback<FamilyVo> cb);

}
