package stanisalv.danylenko.coursepet.network.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import stanisalv.danylenko.coursepet.entity.auth.AuthenticationRequestModel;
import stanisalv.danylenko.coursepet.entity.auth.GetAuthenticationModel;

public interface AuthService {

    @POST("auth/signin")
    Call<GetAuthenticationModel> authorize(@Body AuthenticationRequestModel credentials);

}
