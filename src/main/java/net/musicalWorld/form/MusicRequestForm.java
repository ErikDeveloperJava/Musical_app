package net.musicalWorld.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicRequestForm {

    @Length(min = 2,max = 255)
    private String name;

    private String year;

    private List<Integer> categories;

    private List<Integer> albums;

    private MultipartFile music;
}
