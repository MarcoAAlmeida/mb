package br.dev.marcoalmeida.mb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class MbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MbApplication.class, args);
	}

}
