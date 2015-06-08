package com.sng.game.cards;

public class Card implements Comparable<Card>{
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public static Card parse(String s) {
        Rank rank = Rank.parse(s.substring(0, 1));
        Suit suit = Suit.parse(s.substring(1, 2));
        return new Card(rank, suit);
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }

    @Override
    public int compareTo(Card card) {
        return rank.compareTo(card.getRank());
    }
}