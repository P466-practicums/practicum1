package edu.iu.p466.prime_service.controller;

import edu.iu.p466.prime_service.service.IPrimeService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/primes")

public class PrimesController {
    IPrimeService PS;
    public PrimesController(IPrimeService PS){
        this.PS = PS;
    }
    @GetMapping("/{n}")
    public boolean isPrime(@PathVariable int n){
        return PS.isPrime(n);
    }
}
