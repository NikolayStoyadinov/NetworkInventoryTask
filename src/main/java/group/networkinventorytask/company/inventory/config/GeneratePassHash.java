package group.networkinventorytask.company.inventory.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePassHash {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String hash = encoder.encode("YourStrongPassword123!");

        System.out.println(hash);
    }
}
