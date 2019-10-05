package stanisalv.danylenko.coursepet.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountByBreedInCountryStatistic implements Serializable {

    private String breed;
    private Long count;

}
