package com.sng.screen.figures;

import java.util.LinkedList;

public class Player implements Cloneable {
    private int seat;
    private String name;
    private long stack;
    private LinkedList<Move> moves;

    public Player(int seat, String name, int stack) {
        this.seat = seat;
        this.name = name;
        this.stack = stack;
    }

    public LinkedList<Move> getMoves() {
        return moves;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "seat=" + seat +
                ", moves=" + moves +
                ", stack=" + stack +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    protected Player clone() {
        try {
            Player clone = (Player) super.clone();
            clone.clearMoves();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new FileParseException();
        }
    }

    private void clearMoves() {
        moves = null;
    }

    public void setStack(long stack) {
        this.stack = stack;
    }

    public long getStack() {
        return stack;
    }

    public void addMove(State state, long bet) {
        if (moves == null) moves = new LinkedList<>();
        if (stack - bet == 0) state = State.ALL_IN;
        moves.add(new Move(state, bet, stack));
        stack = stack - bet;
    }

}