package stanisalv.danylenko.coursepet.entity.country;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanisalv.danylenko.coursepet.entity.Graft;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryWithGraft implements Serializable {

    private Country country;
    private List<Graft> grafts;

    @Override
    public String toString() {
        return country.getName();
    }
}