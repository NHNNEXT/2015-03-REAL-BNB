package net.balbum.baby.lib.retrofit;

import net.balbum.baby.VO.LoginVo;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by hyes on 2015. 11. 15..
 */
public interface TaskService {

    @POST("/api/user/login")
    void createTask(@Body LoginVo task, Callback<LoginVo> cb);
}
