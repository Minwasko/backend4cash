package ee.vovtech.backend4cash.blogs;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogsController {

    //@Autowired
    //blogService


    //todo B create a method to query blogs (plural)
    @GetMapping
    public List<Blog> getAllBlogs(@RequestParam(defaultValue = "1") long page,
                                  @RequestParam(defaultValue = "20") long size,
                                  @RequestParam(defaultValue = "popularFirst") String sortMethod)
    {
        //return blogservice.getAllBlogs(page, size, sortMethod)
        return null;
    }

    //todo C create a method to query single blog
    @GetMapping("{id}")
    public Blog getBlogById(@PathVariable long id){
        // return blogService.findById(id)
        return null;
    }

    //todo D create a method to save a new blog
    @PostMapping
    public void postBlog(@RequestBody Blog blog){
        //blogService.save(blog)
    }

    //todo E create a method to update a bog
    @PutMapping("{id}")
    public void updateBlog(@PathVariable long id, @RequestBody Blog blog){
        //blogService.update(id, blog);
    }

    //todo F create a method to delete a blog
    @DeleteMapping("{id}")
    public void deleteBlog(@PathVariable long id){
        //blogService.deleteById(id)
    }

    //todo G assuming each blog has only 1 author (one-to-one relation) create a method to query blog's author
    @GetMapping("{id}/author")
    public Author getBlogAuthor(@PathVariable long id){
        //return blogService.findById(id).getAuthor();
        return null;
    }

    //todo H create a method to update blog url (and nothing else)
    @PutMapping("{id}/url")
    public void updateBlogUrl(@PathVariable long id, @RequestParam String url){
        //blogService.updateUrl(id, url);
    }


    //todo I-J modify correct method to support pagination, pagination is done by page and size

    //done in b method
    //todo I add page (pagination starts at page 1)
    //todo J add size (default page size is 20)


    //done in b method
    //todo K modify correct method to order blogs
    // * by most recent first
    // * by least recent first
    // (you can assume that by default it searches by most popular first)

}
