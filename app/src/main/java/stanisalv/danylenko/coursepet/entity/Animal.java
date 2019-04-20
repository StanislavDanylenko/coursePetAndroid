package stanisalv.danylenko.coursepet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stanisalv.danylenko.coursepet.entity.enumeration.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal implements Serializable {

    private Long id;

    private String name;
    private String photoURL;

    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    private Double weight;
    private Double height;
    private Double length;

    private String smartCardId;

    private AnimalsBreed animalsBreed;

    private List<SmartDevice> smartDevices = new ArrayList<>();

}
