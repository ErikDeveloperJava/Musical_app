package net.musicalWorld.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"musicList","musician"})
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "musician_id")
    private Musician musician;

    @ManyToMany
    @JoinTable(name = "album_music",joinColumns = @JoinColumn(name = "album_id"),
    inverseJoinColumns = @JoinColumn(name = "music_id"))
    private List<Music> musicList;
}