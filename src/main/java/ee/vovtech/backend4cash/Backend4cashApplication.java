package ee.vovtech.backend4cash;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@SpringBootApplication
@RestController
public class Backend4cashApplication {

	public static void main(String[] args) {
		SpringApplication.run(Backend4cashApplication.class, args);
	}

	@RequestMapping(value = "/")
	public String hello() throws IOException, UnirestException {
		return bitcoin();
	}

	public String bitcoin() throws IOException, UnirestException {
		HttpResponse<JsonNode> httpResponse = Unirest.get("https://api.coingecko.com/api/v3/coins/bitcoin")
				.queryString("localization", "false")
				.queryString("tickers", "false")
				.queryString("market_data", "false")
       .asJson();

		return httpResponse.getBody().getObject().getJSONObject("description").get("en").toString();
	}

}
