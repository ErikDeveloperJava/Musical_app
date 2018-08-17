package net.musicalWorld.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestForm {

    @Length(min = 2,max = 255)
    private String name;

    @Length(min = 4,max = 255)
    private String surname;

    @Length(min = 2,max = 255)
    private String username;

    @Length(min = 4,max = 255)
    private String password;

    private MultipartFile image;
}
