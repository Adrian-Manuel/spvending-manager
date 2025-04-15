package com.SmartPadel.spvendingManagerApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SPVendingMangmentApplication {

	public static void main(String[] args) {

		SpringApplication.run(SPVendingMangmentApplication.class, args);

	}

}
