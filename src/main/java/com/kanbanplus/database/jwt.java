package com.kanbanplus.database;


import java.security.SecureRandom;
import java.util.Base64;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class jwt{

    private static String generateSafeToken(){ 
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[258]; 
        random.nextBytes(bytes); 
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding(); 
        return encoder.encodeToString(bytes);
    }
    private static String findPassword(String input){
        String password = "";
        int startingIndex = input.indexOf(':');
        int endingIndex=input.indexOf('}');
        for(int i = startingIndex+2;i<endingIndex-1;i++){
            password+=input.charAt(i);
        }
        return password;
    }
    public static String encodeToken(String payload){
        byte[] secret = Base64.getUrlDecoder().decode(generateSafeToken());
        @SuppressWarnings("deprecation")
        String jwts = Jwts.builder()
                      .setSubject(payload)
                      .signWith(SignatureAlgorithm.HS256,secret)
                      .compact();
        return jwts;
    }
    public static String decodeToken(String tokenIn){
        String[] chunks = tokenIn.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String decodedPayload = new String(decoder.decode(chunks[1]));
        return findPassword(decodedPayload);
    }
}
