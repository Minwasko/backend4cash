package ee.vovtech.backend4cash.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;

@Getter @Setter @Builder
public class NewsDto {

    private String title;
    @Lob
    private String message;

}
