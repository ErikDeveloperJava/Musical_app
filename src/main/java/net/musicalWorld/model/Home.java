package net.musicalWorld.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String homeImg;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "home_mp3_id")
    private Music music;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "musician_id")
    private Musician musician;
}