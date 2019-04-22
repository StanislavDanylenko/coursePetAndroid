package stanisalv.danylenko.coursepet.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanisalv.danylenko.coursepet.entity.animal.Animal;
import stanisalv.danylenko.coursepet.entity.animal.AnimalsBreed;
import stanisalv.danylenko.coursepet.entity.country.CountryWithGraft;
import stanisalv.danylenko.coursepet.entity.user.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheModel {

    private User user;
    private List<Animal> animals;
    private List<CountryWithGraft> countries;
    private List<AnimalsBreed> breeds;
    private List<Disease> diseases;
    private List<Graft> grafts;
    private List<CountByBreedInCountryStatistic> statistic;

}
