package com.resume.ApiResource;

/**
 * This is Retrofit API Interface to call the API endpoints
 */

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    /* ============== */
    /*      APIs      */
    /* ============== */

    @Multipart
    @POST("/rest/upload/cv")
    Call<ResponseBody> uploadResume(@Part MultipartBody.Part file,
                                    @Part("firstname") RequestBody firstname,
                                    @Part("lastname") RequestBody lastname,
                                    @Part("email") RequestBody email,
                                    @Part("jobtitle") RequestBody jobtitle,
                                    @Part("source") RequestBody source);
}