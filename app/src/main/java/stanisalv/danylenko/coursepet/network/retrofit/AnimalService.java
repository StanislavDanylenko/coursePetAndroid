package stanisalv.danylenko.coursepet.network.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import stanisalv.danylenko.coursepet.entity.Animal;
import stanisalv.danylenko.coursepet.entity.AnimalCreateDto;
import stanisalv.danylenko.coursepet.entity.AnimalUpdateDto;

public interface AnimalService {

    @POST("animal")
    Call<Animal> addAnimal(@Header("Authorization") String token, @Body AnimalCreateDto animalDto);

    @PUT("animal/{id}")
    Call<Animal> updateAnimal(@Header("Authorization") String token, @Path("id") Long id, @Body AnimalUpdateDto animalDto);

    @DELETE("animal/{id}")
    Call<Void> deleteAnimal(@Header("Authorization") String token, @Path("id") Long animalId);

    @GET("animal/user/{id}")
    Call<List<Animal>> updateAnimals(@Header("Authorization") String token, @Path("id") Long userId);

}
