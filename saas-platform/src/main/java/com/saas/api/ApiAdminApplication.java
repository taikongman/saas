package com.saas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ApiAdminApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(ApiAdminApplication.class, args);
    }
    
    
}
