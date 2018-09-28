/**
 * Author's Name: Tejaswini Jagtap
 */

package student;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class PlayerImpl implements Player {

    /**
     * fields for every player, whatever they need such as
     * score, the number of train pieces they have left,
     * the cards in their hand etc.
     */
    private int score;
    private int numTrainPieces;
    private ArrayList<Route> claimedRoutes;
    private Baron baron;
    private Pair pair;
    private HashMap<Card, Integer> hand;
    private ArrayList<PlayerObserver> observers;
    private boolean hasclaimed;
    private Graph graph;
    private RailroadBarons map;

    /**
     * constructor for player using the classic railroad barons
     * implementation
     * @param baron what color they are
     * @param map the map they are playing on
     */
    public PlayerImpl(Baron baron, RailroadBaronsImpl map){
        this.baron = baron;
        this.score = 0;
        this.numTrainPieces = 45;
        this.claimedRoutes = new ArrayList<>();
        this.hand = new HashMap<>();
        this.observers = new ArrayList<>();
        hasclaimed = false;
        this.map = map;
        graph = new Graph(map);
    }

    /**
     * constructor for player using the lonely railroad baron implementation
     * @param baron what color they are
     * @param map the map they are playing on
     */
    public PlayerImpl(Baron baron, RailroadBaronLonelyImpl map){
        this.baron = baron;
        this.score = 0;
        this.numTrainPieces = 45;
        this.claimedRoutes = new ArrayList<>();
        this.hand = new HashMap<>();
        this.observers = new ArrayList<>();
        hasclaimed = false;
        this.map = map;
        graph = new Graph(map);
    }

    /**
     * This is called at the start of every game to reset the player to its
     * initial state:
     * <ul>
     *     <li>Number of train pieces reset to the starting number of 45.</li>
     *     <li>All remaining {@link Card cards} cleared from hand.</li>
     *     <li>Score reset to 0.</li>
     *     <li>Claimed {@link Route routes} cleared.</li>
     *     <li>Sets the most recently dealt {@link Pair} of cards to two
     *     {@link Card#NONE} values.</li>
     * </ul>
     *
     * @param dealt The hand of {@link Card cards} dealt to the player at the
     *              start of the game. By default this will be 4 cards.
     */
    @Override
    public void reset(Card... dealt) {
        /*
        add the cards that the player is dealt to a list of dealt cards
         */
        ArrayList<Card> dealtCards  = new ArrayList<>();
        dealtCards.addAll(Arrays.asList(dealt));
        /*
        reset the number of train pieces to 45
         */
        this.numTrainPieces = 45;
        /*
        get the number of each type of card and replace it in the
        hashmap
         */
        for (Card card: dealtCards){
            if (hand.containsKey(card)){
                hand.replace(card, hand.get(card) + 1);
            }
            else{
                hand.put(card, 1);
            }
        }
        /*
        reset the score to 0, claimed routes to an empty arraylist because it is
        the start of the game so they havent claimed routes yet, set the pair of
        cards to none, and notify the observers
         */
        this.score = 0;
        this.claimedRoutes = new ArrayList<>();
        this.pair = new PairImpl(Card.NONE, Card.NONE);
        for (PlayerObserver observer: observers){
            observer.playerChanged(this);
        }
    }

    /*
    toString to properly print out the player baron
     */
    public String toString(){
        return this.baron.toString();
    }

    /**
     * Adds an {@linkplain PlayerObserver observer} that will be notified when
     * the player changes in some way.
     *
     * @param observer The new {@link PlayerObserver}.
     */
    @Override
    public void addPlayerObserver(PlayerObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an {@linkplain PlayerObserver observer} so that it is no longer
     * notified when the player changes in some way.
     *
     * @param observer The {@link PlayerObserver} to remove.
     */
    @Override
    public void removePlayerObserver(PlayerObserver observer) {
        observers.remove(observer);
    }

    /**
     * The {@linkplain Baron baron} as which this player is playing the game.
     *
     * @return The {@link Baron} as which this player is playing.
     */
    @Override
    public Baron getBaron() {
        return this.baron;
    }

    /**
     * Used to start the player's next turn. A {@linkplain Pair pair of cards}
     * is dealt to the player, and the player is once again able to claim a
     * {@linkplain Route route} on the {@linkplain RailroadMap map}.
     *
     * @param dealt a {@linkplain Pair pair of cards} to the player. Note that
     * one or both of these cards may have a value of {@link Card#NONE}.
     */
    @Override
    public void startTurn(Pair dealt) {
        this.pair = dealt;
        hasclaimed = false;
        if (hand.containsKey(pair.getFirstCard())){
            for (Card card: Card.values()){
                if (pair.getFirstCard().equals(card)) {
                    int a = hand.get(pair.getFirstCard());
                    a = a +1;
                    hand.replace(card, a);
                }
            }
        }
        else {
            hand.put(pair.getFirstCard(), 1);
        }
        if (hand.containsKey(pair.getSecondCard())){
            for (Card card: Card.values()){
                if (pair.getSecondCard().equals(card)) {
                    int a = hand.get(pair.getSecondCard());
                    a = a +1;
                    hand.replace(card, a);
                }
            }
        }
        else {
            hand.put(pair.getSecondCard(), 1);
        }
        for (PlayerObserver observer: observers){
            observer.playerChanged(this);
        }

    }

    /**
     * Returns the most recently dealt {@linkplain Pair pair of cards}. Note
     * that one or both of the {@linkplain Card cards} may have a value of
     * {@link Card#NONE}.
     *
     * @return The most recently dealt {@link Pair} of {@link Card Cards}.
     */
    @Override
    public Pair getLastTwoCards() {
        return this.pair;
    }

    /**
     * Returns the number of the specific kind of {@linkplain Card card} that
     * the player currently has in hand. Note that the number may be 0.
     *
     * @param card The {@link Card} of interest.
     * @return The number of the specified type of {@link Card} that the
     * player currently has in hand.
     */
    @Override
    public int countCardsInHand(Card card) {
        int num =0;
        for(Card c: hand.keySet()){
            if(c.equals(card)){
                num += hand.get(card);
            }
        }
        return num;
    }

    /**
     * Returns the number of game pieces that the player has remaining. Note
     * that the number may be 0.
     *
     * @return The number of game pieces that the player has remaining.
     */
    @Override
    public int getNumberOfPieces() {
        return this.numTrainPieces;
    }

    /**
     * Returns true iff the following conditions are true:
     *
     * <ul>
     *     <li>The {@linkplain Route route} is not already claimed by this or
     *     some other {@linkplain Baron baron}.</li>
     *     <li>The player has not already claimed a route this turn (players
     *     are limited to one claim per turn).</li>
     *     <li>The player has enough {@linkplain Card cards} (including ONE
     *     {@linkplain Card#WILD wild card, if necessary}) to claim the
     *     route.</li>
     *     <li>The player has enough train pieces to claim the route.</li>
     * </ul>
     *
     * @param route The {@link Route} being tested to determine whether or not
     *              the player is able to claim it.
     * @return True if the player is able to claim the specified
     * {@link Route}, and false otherwise.
     */
    @Override
    public boolean canClaimRoute(Route route) {
        int routeLength = route.getLength();
        int wild  = 0;
        int no =  0;
        int greatestCardNum = 0;
        if (hand.containsKey(Card.WILD)) {
            wild = 1;
            no = hand.get(Card.WILD);
        }

        hand.remove(Card.WILD);
        for (Card card: hand.keySet()){
            int cardNum = this.countCardsInHand(card);
            if (cardNum > greatestCardNum){
                greatestCardNum = cardNum;

            }
        }
        hand.put(Card.WILD, no);
        greatestCardNum += wild;
        if(greatestCardNum >= routeLength && route.getBaron().equals(Baron.UNCLAIMED) && getNumberOfPieces() >= routeLength && !hasclaimed){
            return true;
        }

        return false;
    }

    /**
     * Claims the given {@linkplain Route route} on behalf of this player's
     * {@linkplain Baron Railroad Baron}. It is possible that the player has
     * enough cards in hand to claim the route by using different
     * combinations of {@linkplain Card card}. It is up to the implementor to
     * employ an algorithm that determines which cards to use, but here are
     * some suggestions:
     * <ul>
     *     <li>Use the color with the lowest number of cards necessary to
     *     match the length of the route.</li>
     *     <li>Do not use a wild card unless absolutely necessary (i.e. the
     *     player has length-1 cards of some color in hand and it is the most
     *     numerous card that the player holds).</li>
     * </ul>
     *
     * @param route The {@link Route} to claim.
     *
     * @throws RailroadBaronsException If the {@link Route} cannot be claimed,
     * i.e. if the {@link #canClaimRoute(Route)} method returns false.
     */
    @Override
    public void claimRoute(Route route) throws RailroadBaronsException {
        if(this.canClaimRoute(route)){
            hasclaimed = true;
            claimedRoutes.add(route);
            this.score += route.getPointValue();
            this.numTrainPieces -= route.getLength();
            route.claim(this.baron);
            graph.addStation(route.getOrigin(), route.getDestination());
            int i = this.score;
            if (graph.findRoute(graph.getEndStation()).equals("westernmost") || graph.findRoute(graph.getEndStation()).equals("easternmost")){
                i = i + (5 * this.map.getRailroadMap().getCols());
            }
            if (graph.findRoute(graph.getEndStation()).equals("northernmost") || graph.findRoute(graph.getEndStation()).equals("southernmost")){
                i = i + (5 * this.map.getRailroadMap().getRows());
            }
            this.score = i;
            int a = 0;
            Card card0 = Card.WILD;
            for (Card card: hand.keySet()){
                if (hand.get(card) > a && card != Card.WILD){
                    a = hand.get(card);
                    card0 = card;
                }
            }
            if (a >= route.getLength()) {
                a = a - route.getLength();
                hand.remove(card0);
                hand.put(card0, a);
            }
            else if (a + 1 == route.getLength()){
                if (hand.containsKey(Card.WILD)){
                    int wild = hand.get(Card.WILD);
                    wild = wild - 1;
                    hand.remove(Card.WILD);
                    hand.put(Card.WILD, wild);
                    hand.remove(card0);
                }
            }
            for(PlayerObserver po: observers){
                po.playerChanged(this);
            }
        }
        else{
            throw new RailroadBaronsException("You attempted to claim a route that can't be claimed");
        }
    }

    /**
     * Returns the {@linkplain Collection collection} of {@linkplain Route
     * routes} claimed by this player.
     *
     * @return The {@link Collection} of {@linkplain Route Routes} claimed by
     * this player.
     */
    @Override
    public Collection<Route> getClaimedRoutes() {
        return this.claimedRoutes;
    }

    /**
     * Returns the players current score based on the
     * {@linkplain Route#getPointValue() point value} of each
     * {@linkplain Route route} that the player has currently claimed.
     *
     * @return The player's current score.
     */
    @Override
    public int getScore() {
         return this.score;
    }

    /**
     * Returns true iff the following conditions are true:
     *
     * <ul>
     *     <li>The player has enough {@linkplain Card cards} (including
     *     {@linkplain Card#WILD wild cards}) to claim a
     *     {@linkplain Route route} of the specified length.</li>
     *     <li>The player has enough train pieces to claim a
     *     {@linkplain Route route} of the specified length.</li>
     * </ul>
     *
     * @param shortestUnclaimedRoute The length of the shortest unclaimed
     *                               {@link Route} in the current game.
     *
     * @return True if the player can claim such a {@link Route route}, and
     * false otherwise.
     */
    @Override
    public boolean canContinuePlaying(int shortestUnclaimedRoute) {
        int wild = 0;
        int no = 0;
        if (hand.containsKey(Card.WILD)){
            wild = 1;
            no = hand.get(Card.WILD);
        }
            hand.remove(Card.WILD);
            for (Card card : hand.keySet()) {
                if (numTrainPieces >= shortestUnclaimedRoute && countCardsInHand(card) + wild >= shortestUnclaimedRoute) {
                    return true;
                }
            }
            hand.put(Card.WILD, no);
        return false;
    }
}
