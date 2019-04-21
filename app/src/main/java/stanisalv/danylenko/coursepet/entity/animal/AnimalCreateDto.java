package stanisalv.danylenko.coursepet.entity.animal;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanisalv.danylenko.coursepet.entity.enumeration.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCreateDto implements Serializable {

    private String name;
    private String photoURL;

    private Gender gender;
    private Date birthDate;

    private Double weight;
    private Double height;
    private Double length;

    private Long animalsBreedId;

    private Long userId;

}
