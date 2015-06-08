package com.sng;

import com.sng.game.cards.Card;
import com.sng.screen.figures.Game;
import com.sng.screen.figures.Player;
import com.sng.screen.figures.Street;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GameParser {
    static Game game;
    static List<Game> gameList = new ArrayList<>();
    public static String block;

    private static String name = "PapaShango19";
    private int tournamentNumber;
    // private static String name = "fftttt";

    void parseGame(String gameInput) {
        game = new Game(name, tournamentNumber);

        String[] tokens = gameInput.split(Pattern.quote(" **"));

        for (int i = 0; i < tokens.length; i++) {
            block = tokens[i];
            switch (i) {
                case 0:
                    parseGameNumber();
                    break;
                case 1:
                    parseBigBlind();
                    break;
                case 2:
                    parsePlayers();
                    break;
                case 3:
                    parsePreFlop();
                    break;
                default:
                    if (game.getPreFlop().getCards() == null) break;
                    if (!block.split("\\n")[0].equals("")) parseStreet();
                    break;
            }
        }
        if (game.getPreFlop().getCards() != null) gameList.add(game);
    }

    private static void parsePreFlop() {
        Street preFlop = game.getPreFlop();

        String[] tokens = block.split("\\n");

        if (tokens[1].startsWith("Dealt")) {
            for (String s : tokens[1].split("\\[")[1].split(",")) {
                preFlop.setCard(Card.parse(s.substring(1, 3)));
            }
        } else
            return;

        for (int i = 0; i < tokens.length - 3; i++) {
            preFlop.parseMove(tokens[i + 2]);
        }

        if(preFlop.getPlayer(0).getName().equals(""))
            preFlop.skipGame();                             // empty names text file bug

        game.setPreFlop(preFlop);
    }

    private static void parseStreet() {
        Street street = new Street();
        Street lastStreet = game.getLastStreet();

        street.setBank(lastStreet.getBank());
        String[] tokens = block.split("\\n");

        for (String s : tokens[0].substring(2).split(",")) {
            street.setCard(Card.parse(s.substring(1, 3)));
        }

        if (tokens.length == 2)
            street.setPlayerList(lastStreet.getPlayerList());
        else
            street.setPlayerList(lastStreet.getNextStreetPlayerList());

        for (int i = 0; i < tokens.length - 2; i++) {
            street.parseMove(tokens[i + 1]);
        }
        game.addStreet(street);
    }

    private static void parsePlayers() {
        String[] tokens = block.split("Seat ");
        int buttonSeat = tokens[1].charAt(0) - '0';
        game.setButtonSeat(buttonSeat);

        Street preFlop = new Street();

        int number = (tokens[1].split(": ")[1].charAt(0) - '0');
        game.setPlayersNumber(number);

        for (int i = 0; i < number; i++) {
            String[] playerData = tokens[i + 2].split(" ");
            int seat = playerData[0].charAt(0) - '0';

            String name = playerData[1];

            int stack = getInt(playerData[3]);
            Player player = new Player(seat, name, stack);

            preFlop.addPlayer(player);

            if (i == (number - 1)) {
                String[] split = tokens[i + 2].split("\\n");
                for (int j = 0; j < split.length - 2; j++) {
                    preFlop.parseMove(split[j + 1]);
                }
            }
        }
        game.setPreFlop(preFlop);
    }

    private static void parseBigBlind() {
        game.setBigBlind(getInt(block.split(Pattern.quote("$"))[2].split(" ")[0]));
    }

    private static void parseGameNumber() {
        int number = getInt(block.split(" ")[3]);
        game.setGameNumber(number);
    }

    public static int getInt(String line) {
        return Integer.parseInt(line.replaceAll("\\D", ""));
    }

    public List<Game> getAllGames() throws IOException {
        for (File file : new File("hands/").listFiles()) {
            String fileName = file.getPath();
            if (fileName.endsWith("Holdem.txt")) {
                tournamentNumber = getInt(fileName.split(Pattern.quote(" ("))[1]);
                parseFile(readFile(fileName));
            } //else file.delete();
        }
        return gameList;
    }

    static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        br.close();
        return sb.toString();
    }

    public void parseFile(String fileInput) {
        for (String gameInput : fileInput.split("#Game")) {
            if (gameInput.equals("")) continue;
            parseGame(gameInput);
        }
    }
}
