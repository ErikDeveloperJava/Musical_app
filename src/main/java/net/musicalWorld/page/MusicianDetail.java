package net.musicalWorld.page;

import lombok.*;
import net.musicalWorld.model.Album;
import net.musicalWorld.model.Music;
import net.musicalWorld.model.Musician;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicianDetail {

    private Musician musician;

    List<Album> albums;

    List<Music> musicList;
}
