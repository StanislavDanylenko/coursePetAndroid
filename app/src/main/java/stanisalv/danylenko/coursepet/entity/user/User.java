package stanisalv.danylenko.coursepet.entity.user;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanisalv.danylenko.coursepet.entity.country.Country;
import stanisalv.danylenko.coursepet.entity.enumeration.Localization;
import stanisalv.danylenko.coursepet.entity.enumeration.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;
    private List<Role> roles = new ArrayList<>();
    private Localization localization;

    private Country country;

}
