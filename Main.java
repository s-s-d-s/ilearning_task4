package org.example;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        Game game = new Game();
        game.checkarg(args);
        String key = game.generateKey();
        int computerChoice = game.computerChoice(args);
        System.out.println("HMAC: " + game.genereteHmac(key, args[computerChoice]));
        int userChoice = game.userChoice(args);
        System.out.println("Computer move: " + args[computerChoice]);
        System.out.println(game.winAlgorithm(args, computerChoice, userChoice));
        System.out.println("HMAC key: " + key);
    }
}

class Game {

    public void checkarg(String[] args){
        HashSet<String> uniqueargs = new HashSet<>();
        uniqueargs.addAll(Arrays.asList(args));

        if (args.length < 3) {
            System.out.println("Error#1 --> 3 or more than 3 arguments must be specified! For example: rock paper scissors");
            System.exit(0);
        } else if (args.length % 2 == 0){
            System.out.println("Error#2 --> An odd number of arguments must be specified! For example: rock paper scissors");
            System.exit(0);
        } else if (args.length > uniqueargs.size()) {
            System.out.println("Error#3 --> Non-duplicate arguments must be specified! For example: rock paper scissors");
            System.exit(0);
        }
    }

    public String toHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }

        return result.toString();
    }

    public String generateKey() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] values = new byte[16];
        random.nextBytes(values);
        String key = toHexString(values);

        return key;
    }

    public int computerChoice(String[] args) {
        SecureRandom randomchoice = new SecureRandom();
        int computerChoice = randomchoice.nextInt(args.length);

        return computerChoice;
    }

    public String genereteHmac(String key, String computerchoice) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec keySpec = new SecretKeySpec(
                key.getBytes(),
                "HmacSHA256");

        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(keySpec);
        byte[] result = hmac.doFinal(computerchoice.getBytes());
        String Hmac = toHexString(result);
        return Hmac;
    }

    public int userChoice(String[] args) {
        System.out.println("Available moves:");
        int i = 1;
        for (String str : args) {
            System.out.println(i++ + " - " + str);
        }
        System.out.println("0 - exit");
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your move: ");
        int userchoice = in.nextInt();
        if (userchoice == 0) {
            System.exit(0);
        } else if (userchoice > args.length) {
            System.out.println("Error#4 --> This element does not exist! Please, select available from the list!");
            return userChoice(args);
        }
        System.out.println("Your move: " + args[userchoice-1]);
        in.close();
        return userchoice-1;
    }

    public String winAlgorithm(String[] args, int computerChoice, int userChoice){
        int half = args.length / 2 + 1;
        String[] doubleargs = new String[args.length*2];
        for (int i = 0; i < doubleargs.length; i++) {
            doubleargs[i] = args[i % args.length];
        }

        String[] whowincomputer = Arrays.copyOfRange(doubleargs, computerChoice+1, computerChoice+half);

        if (computerChoice == userChoice) {
            return "Draw";
        } else if (Arrays.asList(whowincomputer).contains(args[userChoice])) {
            return "You win!";
        } else
            return "Computer win!";
    }
}