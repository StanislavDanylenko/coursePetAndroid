package stanisalv.danylenko.coursepet.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartDeviceDto implements Serializable {

    private String mac;
    private String name;
    private Long animalId;
    private Double batteryLevel;

}
