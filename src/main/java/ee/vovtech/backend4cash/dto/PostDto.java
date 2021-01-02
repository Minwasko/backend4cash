package ee.vovtech.backend4cash.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @Builder
public class PostDto {

    private String title;
    private String content;
    private String authorName;
    private long id;

}
