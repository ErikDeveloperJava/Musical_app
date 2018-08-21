package net.musicalWorld.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "musicList")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 2,max = 255)
    private String nameEn;

    @Length(min = 2,max = 255)
    private String nameRu;

    @Length(min = 2,max = 255)
    private String nameArm;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private List<Music> musicList;
}
