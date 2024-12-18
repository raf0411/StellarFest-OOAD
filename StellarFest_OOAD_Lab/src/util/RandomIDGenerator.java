package util;

import java.util.Random; // Import the Random class for generating random values

public class RandomIDGenerator {
    
    /**
     * Generates a unique identifier consisting of 2 digits followed by 3 uppercase letters.
     * The format of the generated ID will be something like: "12ABC".
     * 
     * @return A randomly generated unique ID as a String
     */
    public static String generateUniqueID() {
        // Create a new Random object to generate random numbers and characters
        Random random = new Random();
        
        // StringBuilder is used to efficiently build the final string
        StringBuilder id = new StringBuilder();

        // Generate 2 random digits (0-9) and append them to the id
        for (int i = 0; i < 2; i++) {
            int digit = random.nextInt(10); // Generate a random digit between 0 and 9
            id.append(digit); // Append the digit to the id string
        }

        // Generate 3 random uppercase letters (A-Z) and append them to the id
        for (int i = 0; i < 3; i++) {
            // Generate a random character between 'A' and 'Z'
            char letter = (char) ('A' + random.nextInt(26)); // 'A' + random number between 0 and 25
            id.append(letter); // Append the letter to the id string
        }

        // Convert the StringBuilder to a String and return it
        return id.toString();
    }
}
