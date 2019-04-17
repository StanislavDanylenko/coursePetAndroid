package stanisalv.danylenko.coursepet.entity.auth;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseModel implements Serializable {

    private Long id;
    private String token;
    private String role;

}