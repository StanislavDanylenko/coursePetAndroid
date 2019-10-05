package stanisalv.danylenko.coursepet.entity.animal;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalFullInfoDto implements Serializable {


    private Animal animal;
    private List<AnimalDisease> diseases;
    private List<AnimalGraft> grafts;
}
