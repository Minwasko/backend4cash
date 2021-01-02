package ee.vovtech.backend4cash.phones;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phones")
public class PhonesController {

    //todo for question 14 there are 4 assignments in total
    // Each person has to do only 1. So 2 person team has to do 2 different ones, 3 person - 3, 4 person - 4.
    // Make sure to commit under your user otherwise points won't count.
    // I didn't number these so you can pick your favorite

    //todo
    // You are creating a rest controller for lessons. Think of a phone shop.
    // You need to add necessary annotations and methods to this class.
    // This class should compile.
    // It should run successfully when moved to your application package.
    // Method body is not important and will not be graded.
    // Modifying other classes is unnecessary and will not be graded.

    //todo A add necessary annotations on the class

    //todo B create a method to query phones (plural)
    //todo I modify correct method to support searching by manufacturer while keeping original functionality
    //todo J modify correct method to support searching by price range: priceFrom-priceTo while keeping original functionality
    //todo K modify correct method to order/sort chairs
    // * by latest released date first
    // * by earliest released date first
    // (you can assume that by default it searches most popular first)
    @GetMapping // Adding I, J and K here, as it looks like a logical place for it
    public List<Phone> getPhones(@RequestParam(required = false) String manufacturer,
                                 @RequestParam(required = false) float priceFrom, @RequestParam(required = false) float priceTo,
                                 @RequestParam(required = false) String sortByDateOrder) {
        // List<Phone> phones = new Arraylist<>();
        // if (manufacturer != null) { phones.addAll(phoneRepository.findAll().stream
        // .filter(e -> e.manufacturer.equals(manufacturer)).collect(Collectors.toList()));
        // } else if ( priceFrom != null && priceTo != null) { phones.addAll(phoneRepository.findAll().stream
        // .filter(e -> e.price >= priceFrom && e.price <= priceTo).collect(Collectors.toList()));
        // } else {
        // phones.addAll(phoneRepository.findAll());
        // }
        // if (sortByDateOrder.equals("asc")) phones.stream().sorted(Comparator.comparing(Phone::getReleaseDate)).collect(Collectors.toList());
        // else if (sortByDateOrder.equals("desc"))phones.stream().sorted(Comparator.comparing(Phone::getReleaseDate).reversed()).collect(Collectors.toList());
        return null;
    }

    //todo C create a method to query single phone
    @GetMapping("{name}")
    public Phone getPhoneByName(@PathVariable String name) { // name == id
        // return phoneRepository.findByName(name)
        return null;
    }

    //todo D create a method to save a phone
    @PostMapping
    public void savePhone(@RequestBody Phone phone) {
        // phoneRepository.save(phone) after checking that fields are not null
    }

    //todo E create a method to update a phone
    @PutMapping("{name}")
    public void updatePhone(@PathVariable String name, @RequestBody Phone newPhone) {
        // Phone phone = phoneRepository.findByName(name);
        // phone.setName(newPhone.getName());
        // repeat for other fields
        // phoneRepository.save(phone)
    }

    //todo F create a method to delete a phone
    @DeleteMapping("{name}")
    public void deletePhone(@PathVariable String name) {
        // phoneRepository.delete(phoneRepository.findByName(name))
    }

    //todo G assuming each phone has apps installed (one-to-many relation) create a method to query phone's apps
    @GetMapping("{name}/apps")
    public List<App> getPhoneApps(@PathVariable String name) {
        // phoneRepository.findByName(name).getApps()
        return null;
    }

    //todo H create a method to update phone's price (and nothing else)
    @PutMapping("{name}/price")
    public void updatePhonePrice(@PathVariable String name, @RequestParam float price) {
        // Phone phone = phoneRepository.findByName(name);
        // phone.setPrice(price);
        // phoneRepository.save(phone)
    }

    // I am going to assume based on other controllers, that I should order phones by releaseDate here
    //todo K modify correct method to order/sort chairs
    // * by latest released date first
    // * by earliest released date first
    // (you can assume that by default it searches most popular first)
}
