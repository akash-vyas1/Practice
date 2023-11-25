package akash.learning.codingChallenge_1.codingChallenge_1.Temp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import akash.learning.codingChallenge_1.codingChallenge_1.model.Person;
import akash.learning.codingChallenge_1.codingChallenge_1.model.Role;
import akash.learning.codingChallenge_1.codingChallenge_1.repository.PersonRepo;
import akash.learning.codingChallenge_1.codingChallenge_1.repository.RoleRepo;

// @Component
public class MyCommandLineRunner implements CommandLineRunner {

    // @Autowired
    // private RoleRepo roleRepo;
    @Autowired
    private PersonRepo personRepo;
    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Role adminRole = new Role();
        // adminRole.setName("ROLE_ADMIN");
        // Role userRole = new Role();
        // userRole.setName("ROLE_USER");
        // Role superAdminRole = new Role();
        // superAdminRole.setName("ROLE_SUPER_ADMIN");
        // roleRepo.save(adminRole);
        // roleRepo.save(userRole);
        // roleRepo.save(superAdminRole);
        // int oneSecond = 1000;

        // for (int i = 0; i < 100000000; i++) {
        // continue;
        // }

        // Person admin = new Person();
        // admin.setName("admin");
        // admin.setEmail("admin@email.com");
        // admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
        // System.out.println("getting role from Db");
        // Role fetchedRole = roleRepo.findByName("ROLE_ADMIN").get();
        // System.out.println("Fetched role is :" + fetchedRole.getName());
        // System.out.println("Fetched role id is :" + fetchedRole.getId());
        // admin.setRole(fetchedRole);
        // personRepo.save(admin);

    }

}
