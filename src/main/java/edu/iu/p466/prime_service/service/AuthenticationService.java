package edu.iu.p466.prime_service.service;

import edu.iu.p466.prime_service.model.Customer;
import edu.iu.p466.prime_service.repository.IAuthenticationRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Service
public class AuthenticationService implements IAuthenticationService, UserDetailsService {

    IAuthenticationRepository authenticationRepository;

    public AuthenticationService(IAuthenticationRepository authenticationRepository){
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException{
        try {
            Customer customer = authenticationRepository.findByUsername(username);
            if (customer == null) {
                throw new UsernameNotFoundException("");
            }
            return User
                    .withUsername(username)
                    .password(customer.getPassword())
                    .build();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean register(Customer customer) throws IOException{
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordEncoded = bc.encode(customer.getPassword());
        customer.setPassword(passwordEncoded);
        return authenticationRepository.save(customer);
    }

    @Override
    public boolean login(String username, String password) throws IOException {
        return false;
    }

    @RestController
    public class AuthenticationController{

        private TokenService tokenService;
        private final AuthenticationManager authenticationManager;
        private final IAuthenticationService authenticationService;
        public AuthenticationController(IAuthenticationService authenticationService,
                                        AuthenticationManager authenticationManager,
                                        TokenService tokenService){
            this.authenticationService = authenticationService;
            this.authenticationManager = authenticationManager;
            this.tokenService = tokenService;
        }
        @PostMapping("/register")
        public boolean register(@RequestBody Customer customer){
            try{
                return authenticationService.register(customer);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        @PostMapping("/login")
        public String login(@RequestBody Customer customer){
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    customer.getUsername()
                                    , customer.getPassword()));

            return tokenService.generateToken(authentication);
        }
    }

}
