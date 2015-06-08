package com.sng.screen.figures;

public class Move {
    State state;
    long bet;
    long stack;

    public Move(State state, long bet, long stack) {
        this.state = state;
        this.bet = bet;
        this.stack = stack;
    }

    public State getState() {
        return state;
    }

    public long getBet() {
        return bet;
    }

    public long getStack() {
        return stack;
    }

    @Override
    public String toString() {
        return "Move{" +
                "state=" + state +
                ", bet=" + bet +
                ", stack=" + stack +
                '}';
    }
}
