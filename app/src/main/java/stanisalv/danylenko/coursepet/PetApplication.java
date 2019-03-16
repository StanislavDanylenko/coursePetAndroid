package stanisalv.danylenko.coursepet;

import android.app.Application;

import stanisalv.danylenko.coursepet.network.RetrofitService;

public class PetApplication extends Application {

    public static String BASE_URL = "http://fbddd8bd.ngrok.io";

    private RetrofitService retrofitService;

    public PetApplication() {
        this.retrofitService = new RetrofitService();
    }

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }

}
