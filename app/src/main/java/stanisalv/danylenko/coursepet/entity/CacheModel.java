package stanisalv.danylenko.coursepet.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheModel {

    private User user;
    private List<Animal> animals;
    private List<CountryWithGraft> countries;
    private List<AnimalsBreed> breeds;
    private List<CountByBreedInCountryStatistic> statistic;

}
