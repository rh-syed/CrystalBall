package com.futuristicproduct.crystalball;
import java.util.Random;
/**
 * Created by Syed on 2016-12-10.
 */

public class CrystalBall {

    // Member variables (properties about the object)
    public String[] mAnswers = {
            "It is certain",
            "It is decidedly so",
            "All signs say YES",
            "The stars are not aligned",
            "My reply is no",
            "It is doubtful",
            "Better not to tell you now",
            "Concentrate and ask again",
            "Unable to answer now" };

    // Methods (abilities: things it can do)
    public String getAnAnswer() {
        String answer = "";

        // Randomly select one of three answers: Yes, No, or Maybe
        Random randomGenerator = new Random(); // Construct a new Random number generator
        int randomNumber = randomGenerator.nextInt(mAnswers.length);

        answer = mAnswers[randomNumber];

        return answer;
    }
}
