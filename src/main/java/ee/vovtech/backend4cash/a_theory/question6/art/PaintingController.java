package ee.vovtech.backend4cash.a_theory.question6.art;

import ee.vovtech.backend4cash.a_theory.question6.art.Painting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PaintingController {

    private ArtCollector artCollector = new ArtCollector();

    @GetMapping
    public List<Painting> getPaintings(@RequestParam(defaultValue = "1", required = false) int page
            , @RequestParam(defaultValue = "50", required = false) int pageSize) {
        return artCollector.emptyMethodReturnList().subList((page - 1) * pageSize, page * pageSize);
    }

    @PutMapping("{Id}")
    public Painting addPainting(@PathVariable long Id, @RequestBody Painting painting) {
        return artCollector.emptyMethodReturn1();
    }

    @GetMapping("{Id}")
    public Painting getPainting(@PathVariable long Id) {
        return artCollector.emptyMethodReturn1();
    }
}