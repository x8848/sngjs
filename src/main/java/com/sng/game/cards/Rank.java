package com.sng.game.cards;

public enum Rank {
    Two("2"),
    Three("3"),
    Four("4"),
    Five("5"),
    Six("6"),
    Seven("7"),
    Eight("8"),
    Nine("9"),
    Ten("T"),
    Jack("J"),
    Queen("Q"),
    King("K"),
    Ace("A");

    private final String value;

    Rank(String value) {
        this.value = value;
    }

    public static Rank parse(String s) {
        for (Rank rank : Rank.values()) {
            if (s.equalsIgnoreCase(rank.value)) return rank;
        }
        throw new RuntimeException("wrong rank" + s);
    }

    @Override
    public String toString() {
        return value;
    }
}