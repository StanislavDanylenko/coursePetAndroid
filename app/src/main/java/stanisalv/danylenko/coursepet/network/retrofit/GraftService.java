package stanisalv.danylenko.coursepet.network.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDisease;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDiseaseCreateDto;
import stanisalv.danylenko.coursepet.entity.animal.AnimalGraftCreateDto;

public interface GraftService {

    @DELETE("animalGraft/{id}")
    Call<Void> deleteGraft(@Header("Authorization") String token, @Path("id") Long diseaseId);

    @POST("animalGraft")
    Call<AnimalDisease> addGraft(@Header("Authorization") String token, @Body AnimalGraftCreateDto animalGraftDto);

}
