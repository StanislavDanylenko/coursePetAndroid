package stanisalv.danylenko.coursepet.entity.animal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalGraftCreateDto implements Serializable {

    private Long id;

    private Long animalId;
    private Long graftId;
    private Date date;

}
