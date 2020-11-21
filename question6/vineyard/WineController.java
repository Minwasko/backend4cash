package ee.vovtech.backend4cash.a_theory.question6.vineyard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/wine")
public class WineController {

    private Vineyard vineyard = new Vineyard();

    @GetMapping()
    public List<Wine> getWinesByRegionYearNameGrape(@RequestBody(required = false) String region, @RequestBody(required = false)
            int year, @RequestBody(required = false) String name, @RequestBody(required = false) String grape) {
        return vineyard.emptyMethodReturnList().stream().filter(wine -> wine.getRegion().equals(region) &&
                wine.getYear().equals(year) && wine.getName().equals(name) && wine.getGrape().equals(grape))
                .collect(Collectors.toList());
    }
    @PutMapping("{id}")
    public Wine updateWine(@PathVariable long id) {
        return vineyard.emptyMethodReturn1();
    }

}
