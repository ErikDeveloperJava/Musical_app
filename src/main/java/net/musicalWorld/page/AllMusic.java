package net.musicalWorld.page;

import lombok.*;
import net.musicalWorld.model.Music;
import net.musicalWorld.model.News;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllMusic {

    private List<Music> musicList;

    private List<News> newsList;
}
