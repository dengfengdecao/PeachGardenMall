package me.zoro.peachgardenmall.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dengfengdecao on 17/4/17.
 */

public interface GoodsClient {

    @GET("Goods/getGoodsList")
    Call<JsonObject> getGoodes(@QueryMap Map<String, Object> params);

    @GET("Index/getCategory")
    Call<JsonObject> getGoodsCategories(@QueryMap Map<String, Object> params);

    @GET("Goods/seachGoodsByName")
    Call<JsonObject> searchGoodses(@QueryMap Map<String, Object> params);

    @GET("Goods/introduction")
    Call<JsonObject> getGoodsDetail(@Query("goodsId") int goodsId);

    @GET("Goods/getGoodsList")
    Call<JsonObject> getStarGoodses(@QueryMap Map<String, Object> params);

    @POST
    Call<JsonObject> starGoods(@QueryMap Map<String, Object> params);
}
