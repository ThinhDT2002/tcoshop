package com.tcoshop.util;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
	
	public char[] generatePassword(int length) {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "1234567890";
		String combinedChars = capitalCaseLetters + lowerCaseLetters + numbers;
		Random random = new Random();
		char[] password = new char[length];
		password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		password[2] = numbers.charAt(random.nextInt(numbers.length()));

		for (int i = 3; i < length; i++) {
			password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}
		return password;
	}
	
	public boolean validatePassword(String password) {
	    if(password.trim().length() == 0) {
            return false;        
        } else if(password.contains(" ")) {
            return false;            
        } else if(password.length() < 6 || password.length() > 30){
            return false;           
        }
	    return true;
	}
}
