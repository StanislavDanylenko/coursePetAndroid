package stanisalv.danylenko.coursepet;

import android.app.Application;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import stanisalv.danylenko.coursepet.entity.Disease;
import stanisalv.danylenko.coursepet.entity.Graft;
import stanisalv.danylenko.coursepet.entity.SmartDevice;
import stanisalv.danylenko.coursepet.entity.animal.Animal;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDisease;
import stanisalv.danylenko.coursepet.entity.animal.AnimalGraft;
import stanisalv.danylenko.coursepet.entity.animal.AnimalsBreed;
import stanisalv.danylenko.coursepet.entity.CountByBreedInCountryStatistic;
import stanisalv.danylenko.coursepet.entity.country.CountryWithGraft;
import stanisalv.danylenko.coursepet.entity.user.User;
import stanisalv.danylenko.coursepet.network.RetrofitService;

@Getter
@Setter
public class PetApplication extends Application {

    public static final String BASE_URL = "http://4eb5e1f4.ngrok.io";

    private RetrofitService retrofitService;
    private String TOKEN;

    private User user;
    private List<Animal> animals;
    private List<CountryWithGraft> countries;
    private List<AnimalsBreed> breeds;
    private List<Disease> diseases;
    private List<Graft> grafts;
    private List<CountByBreedInCountryStatistic> statistic;

    private List<AnimalDisease> animalDiseases;
    private List<AnimalGraft> animalGrafts;
    private List<SmartDevice> animalSmartDevices;

    public PetApplication() {
        this.retrofitService = new RetrofitService();
    }

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }

}
