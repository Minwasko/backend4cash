package ee.vovtech.backend4cash;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class Backend4cashApplication {

	public static void main(String[] args) {
		SpringApplication.run(Backend4cashApplication.class, args);
	}



}
