package student;

import model.Card;
import model.Deck;
import model.Pair;

public class PairImpl implements Pair {

    /**
     * Fields for a Pair of cards
     * Deck: deck that has cards or not
     */
    private Card firstCard;
    private Card secondCard;

    /**
     * Constructor to assign the pair of cards to a pair
     * @param firstCard the firstCard part of the pair
     * @param secondCard the secondCard part of the pair
     */
    public PairImpl(Card firstCard, Card secondCard){
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    /**
     * Get the first card in the pair
     * @return Card firstCard
     */
    @Override
    public Card getFirstCard() {
        return this.firstCard;
    }

    /**
     * Get the second card in the pair
     * @return Card secondCard
     */
    @Override
    public Card getSecondCard() {
        return this.secondCard;
    }
}
