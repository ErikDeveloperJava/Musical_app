package net.musicalWorld.model;

import lombok.*;
import net.musicalWorld.model.enums.UserRole;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "musicList")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String imgUrl;

    @ManyToMany
    @JoinTable(name = "bookmark",joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "music_id"))
    private List<Music> musicList;
}
