package stanisalv.danylenko.coursepet.entity.animal;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanisalv.danylenko.coursepet.entity.Disease;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDiseaseCreateDto implements Serializable {

    private Long id;

    private Long animalId;
    private Long diseaseId;

    private Date startData;
    private Date endDate;

    private String treatment;

}
