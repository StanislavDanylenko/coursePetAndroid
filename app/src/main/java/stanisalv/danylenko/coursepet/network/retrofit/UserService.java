package stanisalv.danylenko.coursepet.network.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import stanisalv.danylenko.coursepet.entity.CacheModel;
import stanisalv.danylenko.coursepet.entity.user.User;
import stanisalv.danylenko.coursepet.entity.user.UserDto;

public interface UserService {

    @GET("user/{id}")
    Call<User> getUser(@Header("Authorization") String token, @Path("id") Long id);

    @GET("cache/{id}")
    Call<CacheModel> getCache(@Header("Authorization") String token, @Path("id") Long id);

    @PUT("user/{id}")
    Call<User> updateUser(@Header("Authorization") String token, @Path("id") Long id, @Body UserDto userDto);

}
