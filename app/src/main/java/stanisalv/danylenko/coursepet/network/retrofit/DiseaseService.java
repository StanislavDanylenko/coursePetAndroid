package stanisalv.danylenko.coursepet.network.retrofit;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DiseaseService {

    @DELETE("animalDisease/{id}")
    Call<Void> deleteDisease(@Header("Authorization") String token, @Path("id") Long diseaseId);

}
