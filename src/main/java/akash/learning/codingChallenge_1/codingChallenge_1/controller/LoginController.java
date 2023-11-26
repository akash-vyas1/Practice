package akash.learning.codingChallenge_1.codingChallenge_1.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import akash.learning.codingChallenge_1.codingChallenge_1.model.Person;
import akash.learning.codingChallenge_1.codingChallenge_1.model.RegisterDetails;
import akash.learning.codingChallenge_1.codingChallenge_1.model.Role;
import akash.learning.codingChallenge_1.codingChallenge_1.repository.PersonRepo;
import akash.learning.codingChallenge_1.codingChallenge_1.repository.RoleRepo;

@RestController
public class LoginController {

    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user-login")
    // @PreAuthorize("permitAll()")
    public ResponseEntity<String> loginUser() {
        return ResponseEntity.ok("If your credentials are correct, your JWT Token will be in header !");
    }

    @PostMapping("/add-new-user")
    // @PreAuthorize("permitAll()")
    public ResponseEntity<String> addUser(@RequestBody RegisterDetails userDetails) throws NameNotFoundException {
        Person user = new Person();
        if (personRepo.existsByEmail(userDetails.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email " + userDetails.getEmail() + " is already registered !");
        } else {
            user.setEmail(userDetails.getEmail());
            user.setName(userDetails.getName());
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            String[] roles = userDetails.getRole().split(",");
            Set<Role> authorities = new HashSet<>();
            System.out.println("Roles : from login controller : " + userDetails.getRole());
            for (String role : roles) {
                Optional<Role> fetchedRole = roleRepo.findByName("ROLE_" + role);
                if (fetchedRole.isPresent()) {
                    authorities.add(fetchedRole.get());
                } else {
                    throw new NameNotFoundException("role not found !");
                }
            }
            user.setRoles(authorities);
            // user.setRole("ROLE_" + userDetails.getRole());
            Person savedUser = personRepo.save(user);
            return ResponseEntity.ok("User successfully saved and id is " + savedUser.getId());

        }
    }

    @GetMapping("/get-user/{id}")
    // @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<String> getUserDetails(@PathVariable Long id) {
        Optional<Person> fetchedUser = personRepo.findById(id);
        if (fetchedUser.isPresent()) {
            // String responseString = "User id is : " + fetchedUser.get().getId() + "\n";
            // responseString += "user email is : " + fetchedUser.get().getEmail();
            return ResponseEntity.status(HttpStatus.FOUND).body(fetchedUser.get().toString());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user with id " + id + ", not found !");
            // throw new UsernameNotFoundException("user with id " + id + ", not found !");
        }
    }

    @GetMapping("/contact")
    // @PreAuthorize("permitAll()")
    public String contact() {
        return "Contact details";
    }

    @GetMapping("/super-admin")
    // @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String superAdmin() {
        return "Super Admin";
    }

    @GetMapping("/admin")
    // @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public String admin() {
        return "Super Admin";
    }

    @GetMapping("validate")
    // @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','USER')")
    public String validate() {
        return "validation";
    }

}
