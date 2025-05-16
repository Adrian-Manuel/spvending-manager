package com.smart_padel.spvending_management_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.web.config.EnableSpringDataWebSupport;


@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SPVendingManagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(SPVendingManagementApplication.class, args != null ? args : new String[]{});

	}

}

