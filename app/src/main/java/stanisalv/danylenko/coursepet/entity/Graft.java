package stanisalv.danylenko.coursepet.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graft implements Serializable {

    private Long id;

    private String name;
    private Double frequency;
}
