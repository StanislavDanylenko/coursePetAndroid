package stanisalv.danylenko.coursepet.network.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import stanisalv.danylenko.coursepet.entity.CountByBreedInCountryStatistic;

public interface StatisticService {

    @GET("statistic/{countryId}")
    Call<List<CountByBreedInCountryStatistic>> getStatisticInCountry(@Header("Authorization") String token, @Path("countryId") Long countryId);

}
