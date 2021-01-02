package ee.vovtech.backend4cash.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter @Builder
public class NewsDto {

    private String title;
    private String message;

}
