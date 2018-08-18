package net.musicalWorld.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Past;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicianRequestForm {

    @Length(min = 2,max = 255)
    private String name;

    @Length(min = 4,max = 255)
    private String surname;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "yyyy-MM-dd")
    @Past
    private Date birthDate;

    @Length(min = 10)
    private String biography;

    private MultipartFile image;
}