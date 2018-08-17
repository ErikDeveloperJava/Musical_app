package net.musicalWorld.page;

import lombok.*;
import net.musicalWorld.model.Album;
import net.musicalWorld.model.Music;
import net.musicalWorld.model.Musician;
import net.musicalWorld.model.News;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Main {

    private List<News> newsList;

    private List<Musician> musicianList;

    private List<Album> albumList;

    private List<Music> popularsMusicList;

    private List<Music> newMusicList;

    private Music music;
}
