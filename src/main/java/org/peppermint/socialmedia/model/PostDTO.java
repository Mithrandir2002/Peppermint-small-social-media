package org.peppermint.socialmedia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private Integer id;
    private String image;
    private int likeCount;
    private int commentCount;
    private String owner;
    private Page<Comment> comments;
}
