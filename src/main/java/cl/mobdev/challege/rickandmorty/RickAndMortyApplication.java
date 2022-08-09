package cl.mobdev.challege.rickandmorty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RickAndMortyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickAndMortyApplication.class, args);
	}

}
