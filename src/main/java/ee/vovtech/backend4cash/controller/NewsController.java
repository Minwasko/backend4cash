package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.dto.NewsDto;
import ee.vovtech.backend4cash.service.user.ForumPostService;
import ee.vovtech.backend4cash.service.user.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping
    public void saveNews(@RequestBody NewsDto newsDto){
        newsService.save(newsDto);
    }

    @GetMapping
    public List<NewsDto> getNewsFrom(@RequestParam long amount){
        return newsService.findFrom(amount);
    }

    @GetMapping("{id}")
    public NewsDto findById(@PathVariable long id){
        return newsService.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id){
        newsService.delete(id);
    }

    // save
    // find from
    // findbyid
    // delete


}
