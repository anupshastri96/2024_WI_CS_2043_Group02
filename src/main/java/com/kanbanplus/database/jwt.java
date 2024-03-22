package com.kanbanplus.database;


import java.security.SecureRandom;
import java.util.Base64;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class jwt{

    private static String generateSafeToken(){ 
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[256]; 
        random.nextBytes(bytes); 
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding(); 
        return encoder.encodeToString(bytes);
    }
    public static String encodeToken(String payload){
        byte[] secret = Base64.getMimeDecoder().decode(generateSafeToken());

        @SuppressWarnings("deprecation")
        String jwts = Jwts.builder()
                      .setSubject(payload)
                      .signWith(SignatureAlgorithm.HS256,secret)
                      .compact();
        return jwts;
    }
    public static String decodeToken(String token){
        String password="";
        String[] chunks  = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));
        
        int passwordIndex = payload.indexOf("password:")+9;
        if(passwordIndex != -1){
            for(int i=passwordIndex;i<payload.indexOf("}");i++){
                password+=payload.charAt(passwordIndex);
            }
        }
        return password;
    }
}
