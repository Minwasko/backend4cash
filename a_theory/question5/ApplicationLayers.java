package ee.vovtech.backend4cash.a_theory.question5;

public class ApplicationLayers {

    //todo
    // Architects insist on having layered architecture in the back-end... ¯\_(ツ)_/¯

    //todo p1
    // Name 3 layers of back-end architecture. Give a brief description for each.
    //1 Data access layer
    //Description: contains all the classes for doing technical stuff like taking data from the DB.
    // example: JDBC, ORM
    //2 domain layer
    //Description: contains basic structure to be used in the app layer. Models, services, etc
    //3 application layer
    //Description: fills functional requirements independent of the domain rules. (Controllers)

    //todo p2
    // Do you agree with the architects? Why?
    //Jes
    //Because: because it seems logical. Also everyone uses this architecture so it makes sense to follow the rules
    // so it is easier for other people to work with my code and for me to understand other people's code.

    //todo p3
    // We use objects to transport data between different layers.
    // What is the difference between Entity and Dto? What is same between them?
    //Answer:
    // Same: they represent the same object
    // Difference: way to represent this object. Entity is used in the business logic of the app and contains
    // data that is needed for such action. Dto is used to represent the object without the unnecessary data.

}
