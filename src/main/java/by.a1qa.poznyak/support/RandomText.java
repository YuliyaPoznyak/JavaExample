package by.a1qa.poznyak.support;

public class RandomText {

    public static String getRandomText(int maximumLengthOfText ) {
        String TextToInput;
        final String ALPHABET = "abcdefghijklimnopqrstuvwxyz  ";

        int randomNumber1;
        int randomNumber2;
        randomNumber2 = (int) (1+Math.random() * maximumLengthOfText);
        TextToInput = "";
        for (int i=0; i<randomNumber2; i++){
            randomNumber1 =(int) (Math.random() * ALPHABET.length());
            TextToInput += ALPHABET.charAt(randomNumber1);
        }
        return TextToInput;
    }

}
