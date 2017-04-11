package me.zoro.peachgardenmall.api;

import com.google.gson.JsonObject;

import java.util.Map;

import me.zoro.peachgardenmall.common.AppConfig;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dengfengdecao on 16/11/11.
 */

public interface UserClient {

    @POST("sms/captcha/{recPhoneNum}")
    Call<JsonObject> fetchCaptcha(@Path("recPhoneNum") String tel);

    @POST("user/reg")
    Call<JsonObject> saveUser(@Body Map<String, Object> params);

    @FormUrlEncoded
    @POST(AppConfig.SERVER_HOST + "/happy-help/login")
    Call<JsonObject> login(@Field("username") String username, @Field("password") String password);

    //获取用户详细信息
    @GET("user/{username}")
    Call<JsonObject> getUserInfo(@Path("username") String username);

    //修改用户信息
    @POST("user")
    Call<JsonObject> userInfoRevise(@QueryMap Map<String, Object> params);

    @Multipart
    @POST("user")
    Call<JsonObject> uploadAvatar(@PartMap Map<String, RequestBody> partMap,
                                  @Part MultipartBody.Part file);

    //登出
    @POST(AppConfig.SERVER_HOST + "/happy-help/logout")
    Call<JsonObject> logout(@Query("sid") String sid);

    //忘记密码
    @POST("anon/user/new/password")
    Call<JsonObject> forgetPassword(@QueryMap Map<String, Object> params);

    //修改密码
    @POST("user/new/password")
    Call<JsonObject> changePassword(@QueryMap Map<String, Object> params);
}
