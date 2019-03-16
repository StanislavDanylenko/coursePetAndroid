package stanisalv.danylenko.coursepet.network;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static stanisalv.danylenko.coursepet.PetApplication.BASE_URL;

public class RetrofitService {

    private Retrofit retrofit;

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            createRetrofit(BASE_URL);
        }
        return retrofit;
    }

    public void createRetrofit(String url) {
        BASE_URL = url;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

}
