package net.musicalWorld.page;


import lombok.*;
import net.musicalWorld.model.Album;
import net.musicalWorld.model.Music;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumDetail {

    private Album album;

    private List<Album> albums;

    private List<Music> musicList;
}
