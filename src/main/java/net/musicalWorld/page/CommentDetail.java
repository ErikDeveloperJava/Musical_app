package net.musicalWorld.page;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "childrenList")
public class CommentDetail {

    private net.musicalWorld.model.Comment comment;

    private List<CommentDetail> childrenList;
}
