package stanisalv.danylenko.coursepet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanisalv.danylenko.coursepet.entity.enumeration.AnimalState;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record implements Serializable {

    private Long id;

    private Double temperature;
    private Double pulse;
    private Double longitude;
    private Double latitude;

    private AnimalState animalState;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

}
