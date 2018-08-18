package net.musicalWorld.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsRequestForm {

    @Length(min = 4,max = 255)
    private String title;

    @Length(min = 8)
    private String description;

    private MultipartFile image;
}
