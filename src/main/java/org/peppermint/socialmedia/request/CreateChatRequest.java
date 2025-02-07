package org.peppermint.socialmedia.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.peppermint.socialmedia.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateChatRequest {
    private Integer userId;
}
