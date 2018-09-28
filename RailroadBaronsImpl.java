/**
 * Author's name: Tejaswini Jagtap
 */
package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;

public class RailroadBaronsImpl implements RailroadBarons {

    /**
     * list of players playing game
     */
    private ArrayList<Player> playerList;
    /**
     * map for which players will play
     */
    private RailroadMap map;
    /**
     * deck of cards
     */
    private Deck deck;
    /**
     * list of observers updated when a change is made
     */
    private ArrayList<RailroadBaronsObserver> observerList;
    /**
     * player that is playing currently
     */
    private Player currentPlayer;

    /**
     * constructor for initializing playerList, observerList and creating four players
     */
    public RailroadBaronsImpl(){
        playerList = new ArrayList<>();
        playerList.add(new PlayerImpl(Baron.YELLOW, this));
        playerList.add(new PlayerImpl(Baron.BLUE, this));
        playerList.add(new PlayerImpl(Baron.GREEN, this));
        playerList.add(new PlayerImpl(Baron.RED, this));
        observerList = new ArrayList<>();
    }

    /**
     * adds observers to update them
     * @param observer The {@link RailroadBaronsObserver} to add to the
     */
    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {
        observerList.add(observer);
    }

    /**
     * remove observers to stop updating them
     * @param observer The {@link RailroadBaronsObserver} to remove.
     */
    @Override
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer) {
        observerList.remove(observer);
    }

    /**
     * this method starts the game with given map
     * @param map The {@link RailroadMap} on which the game will be played.
     */
    @Override
    public void startAGameWith(RailroadMap map) {
        this.map = map;
        deck = new DeckImpl();
        deck.reset();
        for (Player player: playerList){
            player.reset(deck.drawACard(), deck.drawACard(), deck.drawACard(), deck.drawACard());
        }
        Pair pair = new PairImpl(deck.drawACard(), deck.drawACard());
        currentPlayer = playerList.remove(0);
        currentPlayer.startTurn(pair);

        for (RailroadBaronsObserver observer: observerList){
            observer.turnStarted(this, currentPlayer);
        }
    }

    /**
     * this method starts the game with given map and deck
     * @param map The {@link RailroadMap} on which the game will be played.
     * @param deck The {@link Deck} of cards used to play the game. This may
     *             be ANY implementation of the {@link Deck} interface,
     *             meaning that a valid implementation of the
     *             {@link RailroadBarons} interface should use only the
     */
    @Override
    public void startAGameWith(RailroadMap map, Deck deck) {
        this.map = map;
        this.deck = deck;
        deck.reset();
        Pair pair = new PairImpl(deck.drawACard(), deck.drawACard());
        for (Player player: playerList){
            player.reset(deck.drawACard(), deck.drawACard(), deck.drawACard(), deck.drawACard());
            System.out.println(player.getNumberOfPieces());
        }
        currentPlayer = playerList.remove(0);
        currentPlayer.startTurn(pair);

        for (RailroadBaronsObserver observer: observerList){
            observer.turnStarted(this, currentPlayer);
        }
    }

    /**
     * get the map being used
     * @return map
     */
    @Override
    public RailroadMap getRailroadMap() {
        return map;
    }

    /**
     * get the number of cards remaining in the deck
     * @return number of cards remaining in the deck
     */
    @Override
    public int numberOfCardsRemaining() {
        return deck.numberOfCardsRemaining();
    }

    /**
     * this method checks whether the current player can claim the route of given row and column
     * @param row The row of a {@link Track} in the {@link Route} to check.
     * @param col The column of a {@link Track} in the {@link Route} to check.
     * @return boolean
     */
    @Override
    public boolean canCurrentPlayerClaimRoute(int row, int col) {
        Route route = map.getRoute(row, col);
        if (route.getBaron().equals(Baron.UNCLAIMED) && currentPlayer.canClaimRoute(route)){
            return true;
        }
        return false;
    }

    /**
     * claims the route of given row and column
     * @param row The row of a {@link Track} in the {@link Route} to claim.
     * @param col The column of a {@link Track} in the {@link Route} to claim.
     * @throws RailroadBaronsException
     */
    @Override
    public void claimRoute(int row, int col) throws RailroadBaronsException {
        Route route = map.getRoute(row, col);
        if (currentPlayer.canClaimRoute(route)) {
            currentPlayer.claimRoute(route);
            map.routeClaimed(route);
        }
    }

    /**
     * ends the turn of current player and changes current player
     * if the game is over then it looks for the player with the highest score
     * and sets the winner to that player and notifies the observer of who
     * the winner is
     */
    @Override
    public void endTurn() {
        /*
        notifies whose turn it is
         */
        playerList.add(currentPlayer);
        for (RailroadBaronsObserver observer : observerList) {
            observer.turnEnded(this, currentPlayer);
        }

        /*
        changes the player, and checks if the game is over
         */
        currentPlayer = playerList.remove(0);
        if(gameIsOver()) {
            /*
            if the game is over then it looks for the player with the highest score
             */
            playerList.add(currentPlayer);
            Player winner = playerList.get(0);
            for(int i = 1; i< playerList.size(); i++){
                if (winner.getScore() <= playerList.get(i).getScore()){
                    winner = playerList.get(i);
                }
            }
            playerList.remove(currentPlayer);
            /*
            notifies the observers of who the winner is
             */
            for (RailroadBaronsObserver observer : observerList) {
                observer.gameOver(this, winner);
            }
        } else {
            Pair pair = new PairImpl(deck.drawACard(), deck.drawACard());
            //currentPlayer = playerList.remove(0);
            currentPlayer.startTurn(pair);
            for (RailroadBaronsObserver observer: observerList){
                observer.turnStarted(this, currentPlayer);
            }
        }
    }

    /**
     * get current player
     * @return current player
     */
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * get list of player playing the game
     * @return list of players
     */
    @Override
    public Collection<Player> getPlayers() {
        return playerList;
    }

    /**
     * checks whether the game is over
     * @return boolean
     */
    @Override
    public boolean gameIsOver() {
        for (Route route : map.getRoutes()) {
            if (route.getBaron() == Baron.UNCLAIMED) {
                return false;
            }
        }
        if (deck.numberOfCardsRemaining() == 0) {
            return true;
        }
        for (Player player : playerList) {
            if (player.canContinuePlaying(map.getLengthOfShortestUnclaimedRoute())) {
                return false;
            }
        }
        return false;
    }
}
