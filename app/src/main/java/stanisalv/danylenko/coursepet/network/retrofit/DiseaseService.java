package stanisalv.danylenko.coursepet.network.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDisease;
import stanisalv.danylenko.coursepet.entity.animal.AnimalDiseaseCreateDto;

public interface DiseaseService {

    @DELETE("animalDisease/{id}")
    Call<Void> deleteDisease(@Header("Authorization") String token, @Path("id") Long diseaseId);

    @POST("animalDisease")
    Call<AnimalDisease> addDisease(@Header("Authorization") String token, @Body AnimalDiseaseCreateDto animalDiseaseDto);

}
