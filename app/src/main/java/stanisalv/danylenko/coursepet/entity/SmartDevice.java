package stanisalv.danylenko.coursepet.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartDevice implements Serializable {

    private Long id;

    private String mac;
    private String name;
    private Boolean isActive;
    private Double batteryLevel = 0.0;

    private List<Record> records = new ArrayList<>();

}
