package edu.iu.p466.prime_service.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimeServiceTest {
    PrimeService PS = new PrimeService();

    @Test
    void _45IsNotPrime() {
        int n = 45;
        boolean expected = false;
        boolean actual = PS.isPrime(n);
        assertEquals(expected, actual);
    }

    @Test
    void _539828945930573IsNotPrime() {
        long n = 539828945930573L;
        boolean expected = false;
        boolean actual = PS.isPrime(n);
        assertEquals(expected, actual);
    }

    @Test
    void _285191IsPrime() {
        long n = 285191;
        boolean expected = true;
        boolean actual = PS.isPrime(n);
        assertEquals(expected, actual);
    }
}