package com.sng.screen;

import com.sng.image.CheckImage;
import com.sng.screen.figures.Button;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerButton {

    List<Button> buttonList;
    private BufferedImage buttonImage = ImageIO.read(new File("images/table/button.png"));

    public PlayerButton() throws IOException {
        buttonList = new ArrayList<Button>();

        Button player0Button = new Button(498, 465);
        Button player1Button = new Button(420, 465);
        Button player2Button = new Button(269, 390);
        Button player3Button = new Button(269, 190);
        Button player4Button = new Button(396, 150);
        Button player5Button = new Button(814, 150);
        Button player6Button = new Button(968, 190);
        Button player7Button = new Button(968, 390);
        Button player8Button = new Button(788, 465);

        buttonList.add(player0Button);
        buttonList.add(player1Button);
        buttonList.add(player2Button);
        buttonList.add(player3Button);
        buttonList.add(player4Button);
        buttonList.add(player5Button);
        buttonList.add(player6Button);
        buttonList.add(player7Button);
        buttonList.add(player8Button);
    }


    public boolean checkButton(BufferedImage image, int seat) throws IOException {
        Button button = buttonList.get(seat);
        BufferedImage check = image.getSubimage(button.getX(), button.getY(),
                Button.width, Button.height);
        check = CheckImage.makeBnW(check);

        if (CheckImage.areEqual(check, buttonImage)) {
            return true;
        }
        return false;
    }
}
