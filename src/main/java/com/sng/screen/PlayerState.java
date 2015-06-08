package com.sng.screen;

import com.sng.image.CheckImage;
import com.sng.screen.figures.Cards;
import com.sng.screen.figures.State;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerState {

    private List<Cards> cardsList;

    public PlayerState() throws IOException {
        cardsList = new ArrayList<Cards>();

        Cards player1Cards = new Cards(293, 488);
        Cards player2Cards = new Cards(90, 328);
        Cards player3Cards = new Cards(90, 179);
        Cards player4Cards = new Cards(406, 47);
        Cards player5Cards = new Cards(760, 47);
        Cards player6Cards = new Cards(1105, 179);
        Cards player7Cards = new Cards(1105, 328);
        Cards player8Cards = new Cards(873, 488);

        cardsList.add(player1Cards);
        cardsList.add(player2Cards);
        cardsList.add(player3Cards);
        cardsList.add(player4Cards);
        cardsList.add(player5Cards);
        cardsList.add(player6Cards);
        cardsList.add(player7Cards);
        cardsList.add(player8Cards);
    }

    protected List<Cards> getCardsList() {
        return cardsList;
    }

    public State getState(BufferedImage image, int seat) throws IOException {
        State state = State.OUT;
        Cards cards = cardsList.get(seat - 1);
        BufferedImage stateImage = image.getSubimage(cards.getX(), cards.getY(),
                Cards.width, Cards.height);

        BufferedImage in = ImageIO.read(new File("images/table/in/" + seat + ".png"));

        if (CheckImage.areEqual(stateImage, in)) {
            return State.IN;
        }

        BufferedImage fold = ImageIO.read(new File("images/table/fold/" + seat + ".png"));
        if (CheckImage.areEqual(stateImage, fold)) {
            return State.FOLD;
        }
        return state;
    }
}
