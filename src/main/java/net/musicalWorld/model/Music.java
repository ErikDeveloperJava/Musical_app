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
@ToString(exclude = {"albums","categories","users"})
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String mp3;

    private int year;

    @JsonIgnore
    @ManyToMany(mappedBy = "musicList")
    private List<Album> albums;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "category_music",joinColumns = @JoinColumn(name = "music_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @JsonIgnore
    @ManyToMany(mappedBy = "musicList")
    private List<User> users;
}
