package stanisalv.danylenko.coursepet;

import android.app.Application;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import stanisalv.danylenko.coursepet.entity.Animal;
import stanisalv.danylenko.coursepet.entity.AnimalsBreed;
import stanisalv.danylenko.coursepet.entity.CountByBreedInCountryStatistic;
import stanisalv.danylenko.coursepet.entity.CountryWithGraft;
import stanisalv.danylenko.coursepet.entity.User;
import stanisalv.danylenko.coursepet.network.RetrofitService;

@Getter
@Setter
public class PetApplication extends Application {

    public static final String BASE_URL = "http://1c752e31.ngrok.io";

    private RetrofitService retrofitService;
    private String TOKEN;

    private User user;
    private List<Animal> animals;
    private List<CountryWithGraft> countries;
    private List<AnimalsBreed> breeds;
    private List<CountByBreedInCountryStatistic> statistic;

    public PetApplication() {
        this.retrofitService = new RetrofitService();
    }

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }

}
