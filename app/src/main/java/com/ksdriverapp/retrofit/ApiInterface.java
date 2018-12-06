package com.ksdriverapp.retrofit;

import com.ksdriverapp.models.SignUpModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by SONI on 9/29/2018.
 */

public interface ApiInterface {

   /* @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @FormUrlEncoded
    @POST("user/edit")
    Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);


    @FormUrlEncoded
    @POST("LoginNew.php/")
    Call<Login> Login(@FieldMap Map<String, String> fields);


    @Headers("Content-type: application/json")
    @POST("GetJSONResponse.php/")
    Call<JsonObject> postRawJSON(@Body JsonObject locationPost);

    @Multipart
    @POST("user/photo")
    Call<User> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);

    @Multipart
    @POST("uploadNew.php")
    Call<UploadImage> postImage(@Part MultipartBody.Part image, @PartMap Map<String, RequestBody> map);

    @GET("ShowXml.php")
    Call<ROOT> getXmlData();*/

    //The
   /* @Multipart
    @POST("uploadNew.php")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @PartMap Map<String, RequestBody> map);*/
    /*
     */



    @Multipart
    @POST("oauth/driversignup")
    Call<SignUpModel> signUp(@Part MultipartBody.Part image, @PartMap Map<String, RequestBody> map);




}
