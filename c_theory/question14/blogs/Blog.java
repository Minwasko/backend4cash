package ee.vovtech.backend4cash.blogs;

import java.sql.Timestamp;
import java.util.List;

public class Blog {

    private String name;
    private Integer views;
    private String url;
    private List<String> tags;
    private Timestamp timestamp;
//    ... many more
    private Author author;
}
