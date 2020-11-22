package ee.vovtech.backend4cash.a_theory.question6.chocolate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cakes")
public class CakeController {

    private Chocolate chocolate = new Chocolate();

    @GetMapping()
    public List<Cake> getCakesByIngredients(@RequestBody(required = false) List<String> ingredients,
                                            @RequestBody(required = false) List<String> toppings) {
        return chocolate.emptyMethodReturnList().stream().filter(e -> e.getIngredients().containsAll(ingredients) &&
                e.getToppings().containsAll(toppings)).collect(Collectors.toList());
    }

    @PostMapping
    public Cake addCake(@RequestBody Cake cake) {
        return chocolate.emptyMethodReturn1();
    }

    @PutMapping("{id}")
    public Cake updateCake(@PathVariable long id, @RequestBody Cake cake) {
        Cake cakeFromDB = chocolate.emptyMethodReturn1();
        // make changes to cake
        chocolate.emptyMethodVoid();
        // save new cake
        chocolate.emptyMethodVoid();
        // return updated cake
        return chocolate.emptyMethodReturn1();
    }
}
