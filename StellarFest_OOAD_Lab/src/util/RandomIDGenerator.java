package util;

import java.util.Random;
import java.util.UUID;

public class RandomIDGenerator {
    public static String generateUniqueID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 2; i++) {
            int digit = random.nextInt(10); 
            id.append(digit);
        }

        for (int i = 0; i < 3; i++) {
            char letter = (char) ('A' + random.nextInt(26));
            id.append(letter);
        }

        return id.toString();
    }
}
