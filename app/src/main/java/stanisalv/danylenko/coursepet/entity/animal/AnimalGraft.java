package stanisalv.danylenko.coursepet.entity.animal;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanisalv.danylenko.coursepet.entity.Graft;
import stanisalv.danylenko.coursepet.entity.animal.Animal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalGraft implements Serializable {

    private Long id;

    private Animal animal;
    private Graft graft;

    private Date date;

}
