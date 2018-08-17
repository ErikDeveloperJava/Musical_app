package net.musicalWorld.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "albums")
public class Musician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    private String biography;

    private String imgUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "musician")
    private List<Album> albums;
}
