package com.sng.screen;

import com.sng.image.CheckImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TableCards {

    Map<String, BufferedImage> cards = new HashMap<String, BufferedImage>();

    static int cardWidth = 16;
    static int cardHeight = 24;

    static int hand1X = 545;
    static int hand2X = 625;

    static int handY = 465;

    static int flop1X = 425;
    static int flop2X = 513;
    static int flop3X = 600;
    static int turnX = 687;
    static int riverX = 774;

    static int cardY = 232;

    public TableCards() throws IOException {

        File folder = new File("images/cards/");

        for (File file : folder.listFiles()) {
            cards.put(file.getName().substring(0, 2), ImageIO.read(file));
        }
    }

    public String getHand(BufferedImage image) {
        String cards = "";

        int[] X = {hand1X, hand2X};
        for (int x : X) {
            String card = getHand(image, x, handY);
            cards = cards + card;
        }

        return cards;
    }


    public String getFlopTurnRiver(BufferedImage image) {
        String cards = "";
        int[] X = {flop1X, flop2X, flop3X, turnX, riverX};


        for (int x : X) {
            String card = getHand(image, x, cardY);
            cards = cards + card;
        }

        return cards;
    }

    private String getHand(BufferedImage image, int x, int y) {
        BufferedImage cardImage = image.getSubimage(x, y, cardWidth, cardHeight);

        for (String card : cards.keySet()) {
            if (CheckImage.areEqual(cardImage, cards.get(card))) {
                return card;
            }
        }
        return "";
    }
}