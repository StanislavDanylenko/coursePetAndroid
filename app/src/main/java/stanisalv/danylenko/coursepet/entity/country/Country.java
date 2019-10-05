package stanisalv.danylenko.coursepet.entity.country;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country implements Serializable {

    private Long id;
    private String name;
    private String description;

}
