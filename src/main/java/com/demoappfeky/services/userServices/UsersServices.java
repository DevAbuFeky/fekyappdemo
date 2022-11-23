package com.demoappfeky.services.userServices;

import com.demoappfeky.model.Users;
import com.demoappfeky.repository.userRepo.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsersServices {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOG = LoggerFactory.getLogger(UsersServices.class);

    public UsersServices(UsersRepo usersRepo, PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Users> findAllUsers(){
        return usersRepo.findAll();
    }

    public Optional<Users> findByUserId(Long id){
        return usersRepo.findById(id);
    }

    public Users findByUserName(String userName){
        return usersRepo.findByUserName(userName);
    }

    public Users registerUser(String userName ,String email , String firstName,String lastName, String password){
        if (userName == null || password == null) {
            return null;
        } else {
            if (usersRepo.findByUserName(userName) != null){
                System.out.println("Duplicate Username");
                return null;
            }
            Users users = new Users();
            users.setUserName(userName);
            users.setEmail(email);
            users.setFirstName(firstName);
            users.setLastName(lastName);
            users.setPassword(password);

            return usersRepo.save(users);
        }
    }

    public Users authenticate(String username, String password){
        return usersRepo.findByUserNameAndPassword(username,password).orElse(null);
    }



    //to create local user using commandLineRunner
    @Transactional
    public Users createUser(Users users) throws Exception{
        Users localUser = usersRepo.findByUserName(users.getUserName());

        if(localUser != null) {
            LOG.info("user {} already exists. Nothing will be done", users.getUserName());
        }else {
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            localUser = usersRepo.save(users);
        }

        return localUser;
    }

}
