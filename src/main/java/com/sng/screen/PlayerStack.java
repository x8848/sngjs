package com.sng.screen;

import com.sng.image.CheckImage;
import com.sng.screen.figures.Cards;
import com.sng.screen.figures.Stack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sng.screen.figures.Stack.*;

public class PlayerStack extends PlayerState {

    List<Stack> stackList = new ArrayList<Stack>();

    Map<String, BufferedImage> numbers = new HashMap<String, BufferedImage>();
    BufferedImage comma;
    private static boolean checkComma;

    private int stackX;
    private String stackValue;

    public PlayerStack() throws IOException {
        super();
        int seat = 0;
        for (Cards cards : getCardsList()) {
            int x = (seat == 4 || seat == 5 || seat == 6) ? 14 : 13;
            stackList.add(new Stack(cards.getX() + x, cards.getY() + 69));
            seat++;
        }

        String fileName = "images/numbers/comma.png";
        comma = ImageIO.read(new File(fileName));

        for (int i = 0; i < 10; i++) {
            fileName = "images/numbers/" + i + ".png";
            BufferedImage number = ImageIO.read(new File(fileName));
            numbers.put(((Integer) i).toString(), number);
        }
    }

    public int getStack(BufferedImage image, int seat) throws IOException {
        Stack stack = stackList.get(seat - 1);

        checkComma = false;
        stackValue = "";
        String value = "";
        stackX = stack.getX();
        int count = 0;

        while (value != null) {
            value = getValue(image, stack.getY());
            if (value != null) {
                stackValue = stackValue + value;
            }
            count++;
            if (count == 1) checkComma = true;
        }

        if (stackValue == "") return 0;

        return Integer.parseInt(stackValue);
    }

    private String getValue(BufferedImage image, int y) throws IOException {
        if (checkComma) {
            BufferedImage check = image.getSubimage(stackX, y,
                    commaWidth, numberHeight);

            check = CheckImage.makeBnW(check);

            //          ImageIO.write(check, "png", new File("images/" + stackX + ".png"));

            if (CheckImage.areEqual(check, comma)) {
                stackX = stackX + commaWidth;
                checkComma = false;
            }
        }

        BufferedImage numberImage = image.getSubimage(stackX, y,
                numberWidth, numberHeight);

        numberImage = CheckImage.makeBnW(numberImage);

//        ImageIO.write(numberImage, "png", new File("images/" + stackX + ".png"));

        for (String number : numbers.keySet()) {
            if (CheckImage.areEqual(numberImage, numbers.get(number))) {
                stackX = stackX + numberWidth;
                return number;
            }
        }
        return null;
    }
}