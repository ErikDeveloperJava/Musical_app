package net.musicalWorld.form;

import lombok.*;
import net.musicalWorld.model.Music;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicResponseForm {

    private boolean isNameError;

    private boolean isYearError;

    private boolean isMusicError;

    private boolean isSuccess;

    private Music music;
}
