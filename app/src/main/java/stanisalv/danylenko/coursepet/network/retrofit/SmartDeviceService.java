package stanisalv.danylenko.coursepet.network.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import stanisalv.danylenko.coursepet.entity.SmartDevice;
import stanisalv.danylenko.coursepet.entity.SmartDeviceDto;
import stanisalv.danylenko.coursepet.entity.animal.AnimalGraft;
import stanisalv.danylenko.coursepet.entity.animal.AnimalGraftCreateDto;

public interface SmartDeviceService {

    @DELETE("smartDevice/{id}")
    Call<Void> deleteSmartDevice(@Header("Authorization") String token, @Path("id") Long smartDeviceId);

    @POST("smartDevice")
    Call<SmartDevice> addSmartDevice(@Header("Authorization") String token, @Body SmartDeviceDto smartDeviceDto);

}
