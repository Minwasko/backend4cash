package ee.vovtech.backend4cash.a_theory.question6.sheep;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sheep")
public class SheepController {

    private SheepFarm sheepFarm = new SheepFarm();

    @PostMapping
    public Sheep addNewSheep(@RequestBody Sheep sheep) {
        sheepFarm.emptyMethodReturnList().add(sheep);
        return sheep;
    }

    @DeleteMapping("{id}")
    public void deleteSheep(@PathVariable long id) {
        Sheep sheep = getSheep(id); // normally I d get it from the service, but want it to make sense with empty methods
        sheepFarm.emptyMethodReturnList().remove(sheep);
        sheepFarm.emptyMethodVoid();
    }

    @GetMapping("{id}")
    public Sheep getSheep(@PathVariable long id) {
        return sheepFarm.emptyMethodReturn1();
    }
}
