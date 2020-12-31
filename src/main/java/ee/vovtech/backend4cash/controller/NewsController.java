package ee.vovtech.backend4cash.controller;

import ee.vovtech.backend4cash.dto.NewsDto;
import ee.vovtech.backend4cash.security.Roles;
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

    @Secured(Roles.ADMIN)
    @PostMapping
    public void saveNews(@RequestBody NewsDto newsDto){
        newsService.save(newsDto);
    }

    @GetMapping
    public List<NewsDto> getNewsFromNewest(@RequestParam long amount){
        return newsService.findFromNewest(amount);
    }

    @GetMapping("{id}")
    public NewsDto findById(@PathVariable long id){
        return newsService.findById(id);
    }

    @Secured(Roles.ADMIN)
    @DeleteMapping("{id}")
    public void delete(@PathVariable long id){
        newsService.delete(id);
    }
}
