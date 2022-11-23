package com.demoappfeky;

import com.demoappfeky.model.Users;
import com.demoappfeky.services.userServices.UsersServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoAppFekyApplication implements CommandLineRunner {

    private final UsersServices usersServices;
    public DemoAppFekyApplication(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoAppFekyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Users users = new Users();

        users.setFirstName("admin");
        users.setLastName("user");
        users.setUserName("adminUser");
        users.setPassword("password");
        users.setRole("admin");

        usersServices.createUser(users);
    }
}
