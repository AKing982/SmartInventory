package org.example.smartinventory.workbench;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Scanner;

public class PasswordEncoderUtil
{
    public static void main(String[] args)
    {

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.print("Enter a password: ");
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        String encodedPassword = encoder.encode(password);
        System.out.println("Encoded password: " + encodedPassword);

    }
}
