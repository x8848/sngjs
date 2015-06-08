package com.sng;

import com.sng.image.ScreenCast;

import java.util.Scanner;

public class MainDialog {
    public static void main(String[] args) {
        ScreenCast capture = new ScreenCast();

        Scanner input = new Scanner(System.in);
        System.out.println("Hello ;) What to do ?");

        String inputString = "";

        while (!inputString.equals("exit")) {
            inputString = input.nextLine();

            System.out.println("You entered : " + inputString);

            if (inputString.equals("screen")) {
                capture.start();
            }

            if (inputString.equals("stop")) {
                capture.kill();
                System.out.println("thread stop.");
            }
        }
    }
}

