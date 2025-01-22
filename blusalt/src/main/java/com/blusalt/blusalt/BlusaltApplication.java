package com.blusalt.blusalt;

import com.blusalt.blusalt.entity.BaseUser;
import com.blusalt.blusalt.entity.Role;
import com.blusalt.blusalt.enums.UserType;
import com.blusalt.blusalt.service.JpaService.BaseUserJPAService;
import com.blusalt.blusalt.service.JpaService.RoleJPAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
public class BlusaltApplication {

	private final BaseUserJPAService baseUserJPAService;
	private final RoleJPAService roleJPAService;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BlusaltApplication.class, args);
	}

	@Bean
	CommandLineRunner initialize() {
		return args -> {
			log.info("Running CommandLineRunner...");

			List<Role> rolesToCreate = new ArrayList<>();


			if (roleJPAService.findByName(UserType.ADMIN.name()).isEmpty()) {
				log.info("Creating ADMIN role...");
				Role adminRole = Role.builder()
						.name(UserType.ADMIN.name())
						.build();
				rolesToCreate.add(adminRole);
				log.info("ADMIN role created.");
			}

			if (roleJPAService.findByName(UserType.USER.name()).isEmpty()) {
				log.info("Creating USER role...");
				Role userRole = Role.builder()
						.name(UserType.USER.name())
						.build();
				rolesToCreate.add(userRole);
				log.info("USER role created.");
			}

			if (!rolesToCreate.isEmpty()) {
				roleJPAService.save(rolesToCreate);
				log.info("Roles created and saved.");
			}
			if (baseUserJPAService.findByEmail("admin@bluesalt.com").isEmpty()) {
				log.info("Creating admin user...");


				Role adminRole = roleJPAService.findByName(UserType.ADMIN.name()).get();

				BaseUser adminUser = BaseUser.builder()
						.email("admin@bluesalt.com")
						.name("Blue salt")
						.password(passwordEncoder.encode("adminPassword"))
						.role(adminRole)
						.enabled(true)
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.build();

				baseUserJPAService.saveUser(adminUser);
				log.info("Admin user created.");
			} else {
				log.info("Admin user already exists.");
			}
		};
	}

}
