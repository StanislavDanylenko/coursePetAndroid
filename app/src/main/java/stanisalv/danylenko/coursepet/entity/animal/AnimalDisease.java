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
public class AnimalDisease implements Serializable {

    private Long id;

    private Animal animal;
    private Disease disease;

    private Date startData;
    private Date endDate;

    private String treatment;

}
