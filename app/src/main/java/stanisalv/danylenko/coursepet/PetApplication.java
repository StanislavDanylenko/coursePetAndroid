package stanisalv.danylenko.coursepet;

import android.app.Application;

import lombok.Getter;
import lombok.Setter;
import stanisalv.danylenko.coursepet.network.RetrofitService;

@Getter
@Setter
public class PetApplication extends Application {

    public static final String BASE_URL = "http://0d4e743a.ngrok.io";

    private RetrofitService retrofitService;
    private String TOKEN;

    public PetApplication() {
        this.retrofitService = new RetrofitService();
    }

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }

}
