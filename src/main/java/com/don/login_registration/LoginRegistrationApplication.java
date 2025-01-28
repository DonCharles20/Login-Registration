package com.don.login_registration;

import com.don.login_registration.appuser.AppUser;
import com.don.login_registration.appuser.AppUserRepository;
import com.don.login_registration.appuser.AppUserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LoginRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginRegistrationApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AppUserRepository repository,BCryptPasswordEncoder bCryptPasswordEncoder){

		return args -> {
			repository.save(new AppUser("John Doe", "John25", "john.doe@example.com", bCryptPasswordEncoder.encode("123"), AppUserRole.USER));
			repository.save(new AppUser("Jane Doe", "Jane25", "jane.doe@exampl.com", bCryptPasswordEncoder.encode("123"),AppUserRole.USER));
		};
	}

}
