package stanisalv.danylenko.coursepet.network.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import stanisalv.danylenko.coursepet.entity.User;
import stanisalv.danylenko.coursepet.entity.auth.AuthenticationRequestModel;
import stanisalv.danylenko.coursepet.entity.auth.AuthenticationResponseModel;

public interface UserService {

    @GET("user/{id}")
    Call<User> getUser(@Header("Authorization") String token, @Path("id") Long id);

}
