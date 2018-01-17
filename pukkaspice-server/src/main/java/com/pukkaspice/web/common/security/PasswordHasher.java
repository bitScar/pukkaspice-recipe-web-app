package com.pukkaspice.web.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.pukkaspice.web.common.exception.AppException;

public class PasswordHasher {
    
    /** Salt is a little bit of text that only we know about we add for extra security */
    private static final String SALT = "pukkaspice-20160221-extra";
    
    public static String generateHash(String input) {
        input = input + SALT;
        
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new AppException("Error generating hash", e);
        }

        return hash.toString();
    }
    
}
