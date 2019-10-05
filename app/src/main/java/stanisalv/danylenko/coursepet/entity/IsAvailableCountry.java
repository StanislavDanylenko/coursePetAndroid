package stanisalv.danylenko.coursepet.entity;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IsAvailableCountry implements Serializable {

    private Boolean isAvailable;
    private Set<Graft> grafts;

}
