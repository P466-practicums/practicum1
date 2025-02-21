package edu.iu.p466.prime_service.service;

import edu.iu.p466.prime_service.model.Customer;
import edu.iu.p466.prime_service.repository.IAuthenticationRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Service
public class AuthenticationService implements IAuthenticationService {
    IAuthenticationRepository authenticationRepository;

    public AuthenticationService(IAuthenticationRepository authenticationRepository){
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public boolean register(Customer customer) throws IOException{
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordEncoded = bc.encode(customer.getPassword());
        customer.setPassword(passwordEncoded);
        return authenticationRepository.save(customer);
    }

    @RestController
    public class AuthenticationController{
        private final IAuthenticationService authenticationService;
        public AuthenticationController(IAuthenticationService authenticationService){
            this.authenticationService = authenticationService;
        }
        @PostMapping("/register")
        public boolean register(@RequestBody Customer customer){
            try{
                return authenticationService.register(customer);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }

}
