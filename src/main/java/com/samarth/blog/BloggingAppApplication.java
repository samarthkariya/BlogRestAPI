package com.samarth.blog;

import com.samarth.blog.config.AppConstant;
import com.samarth.blog.entity.Role;
import com.samarth.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BloggingAppApplication implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    private Logger logger = LoggerFactory.getLogger(BloggingAppApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(BloggingAppApplication.class, args);

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Role role = new Role(AppConstant.ADMIN_ROLE, "ADMIN_ROLE");
            Role role1 = new Role(AppConstant.NORMAL_ROLE, "NORMAL_ROLE");

            List<Role> list = List.of(role, role1);

           roleRepo.saveAll(list);
        } catch (Exception e) {
            logger.error("error "+e.getMessage());
        }
    }
}
