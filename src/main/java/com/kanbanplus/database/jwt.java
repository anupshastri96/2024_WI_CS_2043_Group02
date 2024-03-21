package com.kanbanplus.database;


import java.security.SecureRandom;
import java.util.Base64;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class jwt{

    @SuppressWarnings("deprecation")
    public static void main(String[] args){
        String password  = "hellow";
        SecureRandom random = new SecureRandom();
        System.out.println(random);
        byte[] secret = Base64.getDecoder().decode(generateSafeToken());

        String jwts = Jwts.builder()
                      .setSubject(password)
                      .signWith(SignatureAlgorithm.HS256,secret)
                      .compact();
        System.out.println(jwts);
    }
    private static String generateSafeToken(){ 
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[256]; 
        random.nextBytes(bytes); 
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding(); 
        return encoder.encodeToString(bytes);
    }
    // public static String encodeToken(String payload){

    // }
    // public static String decodeToken(String token){
    //     String[] chunks  = token.split("\\.");
    //     Base64.Decoder decoder = Base64.getUrlDecoder();

    //     String header = new String(decoder.decode(chunks[0]));
    //     String payload = new String(decoder.decode(chunks[1]));
        
    //     int passwordIndex = payload.indexOf("password:")+9;
    //     if(passwordIndex != -1){
    //         String password="";
    //         for(int i=passwordIndex;i<payload.indexOf(""))
    //     }
    // }
}
