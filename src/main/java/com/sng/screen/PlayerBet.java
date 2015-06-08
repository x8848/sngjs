package com.sng.screen;

import com.sng.image.CheckImage;
import com.sng.screen.figures.Bet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sng.screen.figures.Bet.*;

public class PlayerBet {

    List<Bet> betList = new ArrayList<Bet>();

    Bet pot;
    Bet bank;
    Map<String, BufferedImage> numbers = new HashMap<String, BufferedImage>();
    BufferedImage comma;
    private static boolean checkComma;

    private int betX;
    private String betValue;
    private boolean isItPot;

    public PlayerBet() throws IOException {
        Bet player0Bet = new Bet(613, 447);
        Bet player1Bet = new Bet(375, 447);
        Bet player2Bet = new Bet(312, 371);
        Bet player3Bet = new Bet(312, 280);
        Bet player4Bet = new Bet(489, 193);
        Bet player5Bet = new Bet(721, 193);
        Bet player6Bet = new Bet(924, 280);
        Bet player7Bet = new Bet(924, 371);
        Bet player8Bet = new Bet(834, 447);

        betList.add(player0Bet);
        betList.add(player1Bet);
        betList.add(player2Bet);
        betList.add(player3Bet);
        betList.add(player4Bet);
        betList.add(player5Bet);
        betList.add(player6Bet);
        betList.add(player7Bet);
        betList.add(player8Bet);

        pot = new Bet(668, 216);
        bank = new Bet(507, 371);

        String fileName = "images/table/numbers/comma.png";
        comma = ImageIO.read(new File(fileName));

        for (int i = 0; i < 10; i++) {
            fileName = "images/table/numbers/" + i + ".png";
            BufferedImage number = ImageIO.read(new File(fileName));
            numbers.put(((Integer) i).toString(), number);
        }

    }

    public int getBank(BufferedImage image) throws IOException {
        isItPot = false;
        return getBet(image, bank);
    }

    public int getPot(BufferedImage image) throws IOException {
        isItPot = true;
        return getBet(image, pot);
    }

    public int getPlayerBet(BufferedImage image, int seat) throws IOException {
        isItPot = false;
        Bet bet = betList.get(seat);
        return getBet(image, bet);
    }

    private int getBet(BufferedImage image, Bet bet) throws IOException {
        checkComma = false;

        int treeCharX = (isItPot) ? 5 : 4;    // $ xxx
        int fourCharX = (isItPot) ? 12 : 11;  // $ x,xxx

        int[] xList = {bet.getX(), bet.getX() - treeCharX, bet.getX() - fourCharX};

        for (int x : xList) {
            betX = x;
            betValue = getValue(image, bet.getY());
            if (x == (bet.getX() - fourCharX)) {
                checkComma = true;
            }
            if (betValue != null) {
                break;
            }
        }

        if (betValue == null) return 0;

        parseRest(image, bet.getY());

        return Integer.parseInt(betValue);
    }

    private void parseRest(BufferedImage image, int y) throws IOException {
        String value = "";

        while (value != null) {
            value = getValue(image, y);
            if (value != null) {
                betValue = betValue + value;
            }
        }
    }

    private String getValue(BufferedImage image, int y) throws IOException {
        if (checkComma) {
            BufferedImage check = image.getSubimage(betX, y,
                    commaWidth, numberHeight);

            check = CheckImage.makeBnW(check);

            if (CheckImage.areEqual(check, comma)) {
                betX = betX + commaWidth;
            }
            checkComma = false;
        }

        BufferedImage numberImage = image.getSubimage(betX, y,
                numberWidth, numberHeight);

        numberImage = CheckImage.makeBnW(numberImage);

        for (String number : numbers.keySet()) {
            if (CheckImage.areEqual(numberImage, numbers.get(number))) {
                betX = betX + numberWidth;
                return number;
            }
        }
        return null;
    }

}