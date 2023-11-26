package akash.learning.codingChallenge_1.codingChallenge_1.config;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import akash.learning.codingChallenge_1.codingChallenge_1.model.Person;
import akash.learning.codingChallenge_1.codingChallenge_1.model.Role;
import akash.learning.codingChallenge_1.codingChallenge_1.repository.PersonRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepo personRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> user = personRepo.findByEmail(email);
        if (user.isPresent()) {
            // List<GrantedAuthority> role = new ArrayList<>();
            // System.out.println("inside my user details service");
            // System.out.println("User having role " + user.get().getRole());
            // role.add(new SimpleGrantedAuthority(user.get().getRole()));
            Set<Role> fetchedRoles = user.get().getRoles();
            String fetchedRole = "";
            for (Role role : fetchedRoles) {
                fetchedRole += " " + role.getAuthority();
            }
            System.out.println("Role : from UserDetailsService : " + fetchedRole);
            return new User(user.get().getEmail(), user.get().getPassword(), user.get().getRoles());
        } else {
            throw new UsernameNotFoundException("User with email " + email + ", not exists ");
        }
    }

}
