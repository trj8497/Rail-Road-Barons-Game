package student;

import model.Card;
import model.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DeckImpl implements Deck {

    /**
     * Fields to save the deck of cards and to save the
     * cards that are removed so that when the deck is reset
     * the removed cards can be added back in
     * deck: ArrayList of Cards
     * removed: ArrayList of Cards
     */
    private ArrayList<Card> deck;
    private ArrayList<Card> removed;

    /**
     * Constructor that adds 20 cards of each kind of card to
     * the deck.
     * Creates a new arraylist for deck and removed
     */
    public DeckImpl(){
        deck = new ArrayList<>();
        removed = new ArrayList<>();
        for(Card c: Card.values()){
            if (!(c.equals(Card.BACK)) && !(c.equals(Card.NONE))){
                for(int i = 0; i < 20; i++){
                    deck.add(c);
                }
            }
        }
        Collections.shuffle(deck);
    }


    /**
     * Reset the deck of cards
     * This method adds back in all the cards from removed
     * (the cards that were removed) back into the deck.
     * All the cards are then shuffled
     */
    @Override
    public void reset() {
        deck.addAll(removed);
        Collections.shuffle(deck);
    }

    /**
     * Draw a card(remove a card from the deck); Add the removed card
     * to the removed arrayList to save the cards that are removed
     * @return the card that was removed
     */
    @Override
    public Card drawACard() {
        if(deck.isEmpty()){
            return Card.NONE;
        }
        Card cardRemoved = deck.remove(0);
        removed.add(cardRemoved);
        return cardRemoved;
    }

    /**
     * Find the number of cards that are left in the deck.
     * Using size() method; return the size of the arrayList
     * @return size of the arrayList/number of cards in the deck
     */
    @Override
    public int numberOfCardsRemaining() {
        return this.deck.size();
    }
}
