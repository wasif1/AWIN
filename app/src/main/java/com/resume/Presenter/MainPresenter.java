package com.resume.Presenter;

import android.app.Activity;

import com.resume.ApiResource.ApiClient;
import com.resume.Interface.MainInterface;
import com.resume.R;
import com.resume.utills.InternetCheckHelper;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    private Activity activity;
    private MainInterface mainInterface;

    public MainPresenter(Activity activity, MainInterface mainInterface) {
        this.activity = activity;
        this.mainInterface = mainInterface;
    }

    public void uploadResume(File file, String firstname, String lastName, String email, String jobTitle, String source) {

        if (!InternetCheckHelper.isConnected()) {
            mainInterface.failure(activity.getString(R.string.internet_error));
        } else {
            mainInterface.loader(true);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
            MultipartBody.Part finalFile = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            RequestBody firstname_ = RequestBody.create(MediaType.parse("string"), firstname);
            RequestBody lastName_ = RequestBody.create(MediaType.parse("string"), lastName);
            RequestBody email_ = RequestBody.create(MediaType.parse("string"), email);
            RequestBody jobTitle_ = RequestBody.create(MediaType.parse("string"), jobTitle);
            RequestBody source_ = RequestBody.create(MediaType.parse("string"), source);

            Call<ResponseBody> call = ApiClient
                    .getInstance()
                    .getApi()
                    .uploadResume(finalFile, firstname_, lastName_, email_, jobTitle_, source_);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    mainInterface.loader(false);
                    if (response.code() == 200) {
                        mainInterface.success(response.message());
                    } else {
                        mainInterface.failure(response.message());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mainInterface.loader(false);
                    mainInterface.failure(t.getMessage());
                }
            });
        }
    }
}
