package edu.iu.p466.prime_service.security;

import java.security.KeyPair;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

public class Jwks {
    private Jwks() {}

    public static RSAKey generateRsa() {
        KeyPair keyPair = KeyGeneratorUtils.generateRsakey();
        RSAPublicKey publickey = (RSAPublickey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privatekey(privatekey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }
}
