package ee.vovtech.backend4cash.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @Builder
public class NewForumPostDto {

    private String authorEmail;
    private String content;
    private String title;


}
