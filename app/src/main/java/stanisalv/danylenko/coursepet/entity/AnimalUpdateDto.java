package stanisalv.danylenko.coursepet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalUpdateDto implements Serializable {

    private Double weight;
    private Double height;
    private Double length;

}
