package com.sng.screen.figures;

import com.sng.GameParser;
import com.sng.game.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Street {
    int bank;
    int pot;
    List<Card> cards;

    List<Player> playerList = new ArrayList<>();

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void setCard(Card card) {
        if (cards == null) cards = new ArrayList<>();
        cards.add(card);
        if (cards.size() > 1) {
            Collections.sort(cards);
        }
    }

    public void parseMove(String token) {
        int bet = 0;
        int moveIndex = 1;
        int betIndex = 2;

        String[] split = token.split(" ");

        int index = getPlayerIndex(split[0]);
        Player player = getPlayer(index);

        if ("posts".equals(split[1])) {
            moveIndex = 2;
            betIndex = 4;
        }

        State state = parseState(split[moveIndex]);

        if (state == State.ANTE) {
            betIndex = 3;
        }

        if (state != State.CHECK && state != State.FOLD) {
            bet = GameParser.getInt(split[betIndex]);
            bank = bank + bet;
            pot = pot + bet;
        }
        player.addMove(state, bet);
        setPlayer(index, player);
    }

    public State parseState(String string) {
        switch (string) {
            case "small":
                return State.SMALL_BLIND;
            case "big":
                return State.BIG_BLIND;
            case "folds":
                return State.FOLD;
            case "checks":
                return State.CHECK;
            case "bets":
                return State.BET;
            case "calls":
                return State.CALL;
            case "raises":
                return State.RAISE;
            case "ante":
                return State.ANTE;
            default:
                throw new FileParseException();
        }
    }

    public Player getPlayer(int index) {
        return playerList.get(index);
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public int getPlayerIndex(String name) {
        for (int i = 0; i < playerList.size(); i++) {
            if (name.equals(playerList.get(i).getName())) return i;
        }
        return 0;
    }

    public List<Player> getNextStreetPlayerList() {
        List<Player> nextStreetPlayerList = new ArrayList<>();

        for (Player player : playerList) {
            State state = player.getMoves().getLast().getState();
            if (state != State.FOLD && state != State.ALL_IN) {
                Player clone = player.clone();
                nextStreetPlayerList.add(clone);
            }
        }
        return nextStreetPlayerList;
    }

    public void setPlayer(int smallBlindPlayerIndex, Player player) {
        playerList.set(smallBlindPlayerIndex, player);
    }

    @Override
    public String toString() {
        return "Street{" +
                "cards='" + cards + '\'' +
                ", bank=" + bank +
                ", pot=" + pot +
                ", playerList=" + playerList +
                '}';
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void skipGame() {
        cards = null;
    }
}