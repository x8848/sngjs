package com.sng.screen.figures;

import java.util.ArrayList;
import java.util.List;

public class Game {
    int gameNumber;
    int tournamentNumber;
    int playersNumber;
    private String playerName;

    int bigBlind;
    int buttonSeat;

    Street preFlop;

    List<Street> streetList = new ArrayList<>();

    public List<Street> getStreetList() {
        return streetList;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Game(String playerName, int tournamentNumber) {
        this.playerName = playerName;
        this.tournamentNumber = tournamentNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }

    public void setButtonSeat(int buttonSeat) {
        this.buttonSeat = buttonSeat;
    }

    public Street getPreFlop() {
        return preFlop;
    }

    public void setPreFlop(Street preFlop) {
        this.preFlop = preFlop;
    }

    public void addStreet(Street street) {
        streetList.add(street);
    }

    public Street getLastStreet() {
        int size = streetList.size();
        if (size != 0) return streetList.get(size - 1);
        else
            return preFlop;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameNumber=" + gameNumber +
                ", playerName='" + playerName + '\'' +
                ", bigBlind=" + bigBlind +
                ", button=" + buttonSeat +
                ", preFlop=" + preFlop +
                ", streetList=" + streetList +
                '}';
    }
}