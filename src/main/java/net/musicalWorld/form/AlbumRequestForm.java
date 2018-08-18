package net.musicalWorld.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Past;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumRequestForm {

    @Length(min = 2,max = 255)
    private String name;

    @Length(min = 8)
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "yyyy-MM-dd")
    @Past
    private Date releaseDate;

    private MultipartFile image;

    private int musicianId;
}
