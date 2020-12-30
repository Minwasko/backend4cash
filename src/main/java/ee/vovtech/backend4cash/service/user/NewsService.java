package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.dto.NewsDto;
import ee.vovtech.backend4cash.exceptions.InvalidNewsException;
import ee.vovtech.backend4cash.model.News;
import ee.vovtech.backend4cash.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public void save(NewsDto newsDto) {
        if(newsDto.getTitle().isEmpty()) throw new InvalidNewsException("Title empti");
        if(newsDto.getMessage().isEmpty()) throw new InvalidNewsException("Text empti");
        News news = new News(newsDto.getTitle(), newsDto.getMessage());
        newsRepository.save(news);
    }

    public List<NewsDto> findFrom(long amount) {

        List<NewsDto> toReturn = new ArrayList<>();
        int lastPostId = newsRepository.findAll().size();
        for (long i = lastPostId; i > lastPostId - amount; i--){
            if(newsRepository.existsById(i)) {
                toReturn.add(createNewsDto(newsRepository.findById(i).get()));
            }
        }
        return toReturn;
    }

    private NewsDto createNewsDto(News news){
        return NewsDto.builder()
                .title(news.getTitle())
                .message(news.getMessage())
                .build();
    }

    public NewsDto findById(long id){
        return createNewsDto(newsRepository.findById(id).orElseThrow(() -> new InvalidNewsException("No news wit that id")));
    }

    public void delete(long id){
        newsRepository.delete(newsRepository.findById(id).orElseThrow(() -> new InvalidNewsException("No news wit that id")));
    }

    //save
    //find from
    //findbyid
    //delete
}
