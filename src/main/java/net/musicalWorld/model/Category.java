package net.musicalWorld.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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

    private String nameEn;

    private String nameRu;

    private String nameArm;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private List<Music> musicList;
}
