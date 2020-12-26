package ee.vovtech.backend4cash.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @Builder
public class PostDto {

    private String message;
    private String username;

}
