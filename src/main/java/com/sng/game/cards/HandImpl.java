package com.sng.game.cards;

import com.sng.dao.domain.Hand;

import java.util.*;

public class HandImpl implements com.sng.game.cards.Hand {
    List<Card> playerCards;
    List<Card> bestHand = new ArrayList<>();
    Hand type = Hand.HighCard;

    NavigableMap<Rank, List<Card>> rankMap = new TreeMap<>();
    Map<Suit, List<Card>> suitMap = new TreeMap<>();

    public HandImpl(List<Card> playerCards) {
        this.playerCards = playerCards;
        bestHand = playerCards;

        if (playerCards.get(0).getRank() == playerCards.get(1).getRank()) {
            type = Hand.OnePair;
        }
        setCards(playerCards);
    }

    public HandImpl() {
    }

    public void setCards(List<Card> cards) {
        for (Card card : cards) {
            setCards(card);
        }
    }

    public void setCards(Card card) {
        Rank rank = card.getRank();
        Suit suit = card.getSuit();

        List<Card> rankCards = rankMap.get(rank);
        List<Card> suitCards = suitMap.get(suit);

        if (rankCards != null) {
            rankCards.add(card);
        } else {
            rankCards = new ArrayList<>();
            rankCards.add(card);
        }
        rankMap.put(rank, rankCards);

        if (suitCards != null) {
            suitCards.add(card);
        } else {
            suitCards = new ArrayList<>();
            suitCards.add(card);
        }
        suitMap.put(suit, suitCards);
    }

    private Hand checkType() {
        Hand straightType = checkStraight();
        if (straightType == Hand.StraightFlush) {
            return straightType;
        }

        Map<Integer, LinkedList<Rank>> sizeMap = getSizeMap();
        Set<Integer> sizes = sizeMap.keySet();

        if (sizes.contains(4)) {
            bestHand = new ArrayList<>();
            Rank rank = sizeMap.get(4).getLast();
            bestHand.addAll(rankMap.get(rank));
            Card card = rankMap.lastEntry().getValue().get(0);
            bestHand.add(card.getRank().equals(rank) ? rankMap.lowerEntry(rank).getValue().get(0) : card);
            return Hand.Quads;
        }
        if (sizes.contains(3)) {
            LinkedList<Rank> ranks = sizeMap.get(3);
            if (ranks.size() == 2 || (sizes.contains(2))) {
                bestHand = new ArrayList<>();
                bestHand.addAll(rankMap.get(ranks.getLast()));
                ranks.removeLast();
                if (ranks.size() != 0)
                    bestHand.addAll(rankMap.get(ranks.getLast()).subList(0, 2)); // take first 2 cards from the lower set
                else
                    bestHand.addAll(rankMap.get(sizeMap.get(2).getLast()));
                return Hand.FullHouse;
            }
        }

        if (checkFlush()) {
            return Hand.Flush;
        }

        if (straightType != null) return Hand.Straight;

        if (sizes.contains(3)) {
            bestHand = new ArrayList<>();
            bestHand.addAll(rankMap.get(sizeMap.get(3).getLast()));

            LinkedList<Rank> ranks = sizeMap.get(1);

            while (bestHand.size() != 5) {
                bestHand.addAll(rankMap.get(ranks.getLast()));
                ranks.removeLast();
            }
            return Hand.Set;
        }
        if (sizes.contains(2)) {
            bestHand = new ArrayList<>();

            LinkedList<Rank> ranks = sizeMap.get(2);
            while (ranks.size() != 0 && bestHand.size() != 4) {
                bestHand.addAll(rankMap.get(ranks.getLast()));
                ranks.removeLast();
            }
            if (bestHand.size() == 2) {
                ranks = sizeMap.get(1);
                while (bestHand.size() != 5) {
                    bestHand.addAll(rankMap.get(ranks.getLast()));
                    ranks.removeLast();
                }
                return Hand.OnePair;
            } else {
                Rank last = null;
                if (sizeMap.containsKey(1)) {
                    last = sizeMap.get(1).getLast();
                }
                if (ranks.size() != 0) {
                    Rank first = ranks.getFirst();
                    if (last != null) {
                        if (first.compareTo(last) > 0) last = first;
                    } else
                        last = first;
                }
                bestHand.add(rankMap.get(last).get(0));
                return Hand.TwoPairs;
            }
        }

        ArrayList<Card> allCards = new ArrayList<>();
        for (List<Card> cards : rankMap.values())
            allCards.addAll(cards);
        int size = allCards.size();
        bestHand = allCards.subList(size - 5, size);
        return Hand.HighCard;
    }

    private Hand checkStraight() {
        if (rankMap.keySet().size() < 5) return null;

        Iterator<Rank> iterator = rankMap.keySet().iterator();
        Rank previous = null;
        LinkedList<Card> straightCards = new LinkedList<>();

        while (iterator.hasNext()) {
            Rank current = iterator.next();

            if (previous == null) {
                previous = current;
                continue;
            }
            if (previous.ordinal() + 1 == current.ordinal()) {
                if (straightCards.size() == 0) {
                    straightCards.add(rankMap.get(previous).get(0));
                }
                straightCards.add(rankMap.get(current).get(0));
            } else {
                if (straightCards.size() >= 4) break;
                straightCards = new LinkedList<>();
            }
            previous = current;
        }

        if ((straightCards.getFirst().getRank() == Rank.Two) && (rankMap.keySet().contains(Rank.Ace))) {
            List<Card> aces = rankMap.get(Rank.Ace);
            Card ace = aces.get(0);

            if (aces.size() > 1) {
                if (isItFlushOrDraw(straightCards.subList(0, 4))) {
                    Suit suit = straightCards.getFirst().getSuit();

                    for (Card card : aces) {
                        if (card.getSuit().equals(suit)) {
                            ace = card;
                            break;
                        }
                    }
                }
            }
            straightCards.addFirst(ace);
        }

        int size = straightCards.size();

        if (size >= 5) {
            LinkedList<List<Card>> flushList = new LinkedList<>();

            for (int i = 0; i <= size - 5; i++) {
                bestHand = straightCards.subList(i, 5 + i);
                if (isItFlushOrDraw(bestHand)) {
                    flushList.add(bestHand);
                }
            }
            if (flushList.size() != 0) {
                bestHand = flushList.getLast();
                return Hand.StraightFlush;
            }
            return Hand.Straight;
        }

        return null;
    }

    private boolean isItFlushOrDraw(List<Card> cards) {
        Iterator<Card> iterator = cards.iterator();
        Suit previous = null;
        while (iterator.hasNext()) {
            Suit current = iterator.next().getSuit();
            if (previous != null) {
                if (!previous.equals(current)) return false;
            }
            previous = current;
        }
        return true;
    }

    private boolean checkFlush() {
        for (List<Card> cards : suitMap.values()) {
            int size = cards.size();
            if (size >= 5) {
                Collections.sort(cards);
                bestHand = new ArrayList<>();
                bestHand.addAll(cards.subList(size - 5, size));
                return true;
            }
        }
        return false;
    }

    @Override
    public Hand getType() {
        type = checkType();
        return type;
    }

    private Map<Integer, LinkedList<Rank>> getSizeMap() {
        Map<Integer, LinkedList<Rank>> rankSizes = new TreeMap<>();
        for (Rank rank : rankMap.keySet()) {
            int size = rankMap.get(rank).size();
            LinkedList<Rank> ranks = rankSizes.get(size);
            if (ranks == null) {
                ranks = new LinkedList<>();
            }
            ranks.add(rank);
            Collections.sort(ranks);
            rankSizes.put(size, ranks);
        }
        return rankSizes;
    }

    public List<Card> getBestHand() {
        Collections.sort(bestHand);
        return bestHand;
    }

    public boolean PlayerCardsTakePart() {
        for (Card playerCard : playerCards) {
            if (bestHand.contains(playerCard)) return true;
        }
        return false;
    }
}