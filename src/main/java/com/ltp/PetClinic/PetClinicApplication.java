package com.ltp.PetClinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ltp.PetClinic.entity.Role;
import com.ltp.PetClinic.entity.RoleType;
import com.ltp.PetClinic.repository.RoleRepository;

@SpringBootApplication
public class PetClinicApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		Role[] roles = new Role[] {
				new Role(1L, RoleType.user),
				new Role(2L, RoleType.admin)
		};

		for (int i = 0; i < roles.length; i++) {
			roleRepository.save(roles[i]);
		}

	}
}
