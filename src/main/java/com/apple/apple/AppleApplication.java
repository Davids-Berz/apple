package com.apple.apple;

import com.apple.apple.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AppleApplication implements CommandLineRunner {

	@Autowired
	private IUploadFileService uploadFileService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AppleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();

		String password = "admin";

		for (int i = 0; i < 2; i++) {
			String bcriptPass = passwordEncoder.encode(password);
			System.out.println("bcriptPass " + i +": "+bcriptPass);
		}
	}
}
