/**
 * Author's name: Tejaswini Jagtap
 */
package student;

import model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RailroadBaronsMap implements RailroadMap {

    /**
     * list of stations
     */
    private ArrayList<Station> stationList;
    /**
     * list of routes
     */
    private ArrayList<Route> routeList;
    /**
     * list of observer
     */
    private List<RailroadMapObserver> observerList;
    /**
     * 2d array list of spaces to store its location
     */
    private Space[][] spaces;
    /**
     * rows in the map
     */
    private int r;
    /**
     * columns in the map
     */
    private int c;

    /**
     * constructor to initialize stationList, spaces and routeList and assign stations, routes and tracks to the spaces
     * @param stationList list of stations
     * @param routeList list of routes
     */
    public RailroadBaronsMap(ArrayList<Station> stationList, ArrayList<Route> routeList) {
        this.routeList = routeList;
        this.stationList = stationList;
        observerList = new ArrayList<>();

        for (Station station : stationList) {
            if (station.getRow() > r) {
                r = station.getRow();
            }
        }

        for (Station station : stationList) {
            if (station.getCol() > c) {
                c = station.getCol();
            }
        }
        r = r+1;
        c = c + 1;
        spaces = new Space[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                for (Station station : stationList) {
                    if (i == station.getRow() && j == station.getCol()) {
                        spaces[i][j] = station;
                    }
                }
                for (Route route : routeList) {
                    for (Track track : route.getTracks()) {
                        if (track.getRow() == i && track.getCol() == j) {
                            spaces[i][j] = track;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (spaces[i][j] == null) {
                    SpaceImpl space = new SpaceImpl(i, j);
                }
            }
        }
    }

    /**
     * adds observer
     * @param observer The {@link RailroadMapObserver} being added to the map.
     */
    @Override
    public void addObserver(RailroadMapObserver observer) {
        observerList.add(observer);
    }

    /**
     * removes observer
     * @param observer The observer to remove from the collection of
     *                 registered observers that will be notified of
     */
    @Override
    public void removeObserver(RailroadMapObserver observer) {
        observerList.remove(observer);
    }

    /**
     * get number of rows in the map
     * @return rows
     */
    @Override
    public int getRows() {
        return r;
    }

    /**
     * get number of columns in the map
     * @return columns
     */
    @Override
    public int getCols() {
        return c;
    }

    /**
     * get particular space in the map according to the row and column
     * @param row The row of the desired {@link Space}.
     * @param col The column of the desired {@link Space}.
     *
     * @return space
     */
    @Override
    public Space getSpace(int row, int col) {
        return spaces[row][col];
    }

    /**
     * get route on the specific location
     * @param row The row of the location of one of the {@link Track tracks}
     *            in the route.
     * @param col The column of the location of one of the
     * {@link Track tracks} in the route.
     *
     * @return route
     */
    @Override
    public Route getRoute(int row, int col) {
        for (Route route : routeList) {

            if (route.getOrigin().getRow() == row && route.getOrigin().getCol() == col) {
                return route;
            } else if (route.getDestination().getRow() == row && route.getDestination().getCol() == col) {
                return route;
            } else {
                for (Track track : route.getTracks()) {
                    if (track.getRow() == row && track.getCol() == col) {
                        return track.getRoute();
                    }
                }
            }
        }
        return null;
    }

    /**
     * claims the route
     * @param route The {@link Route} that has been claimed.
     */
    @Override
    public void routeClaimed(Route route) {
        if (route.getBaron() != Baron.UNCLAIMED) { //try to claim the route, update the view if you can
            for (RailroadMapObserver observer : observerList) {
                observer.routeClaimed(this, route);
            }
        }
    }

    /**
     * gets the shortest length of the unclaimed route
     * @return
     */
    @Override
    public int getLengthOfShortestUnclaimedRoute() {
        int length = getRows();
        if(getCols() >length){
            length = getCols();
        }
        for (Route route: routeList){
            if(route.getLength() <length){
                length = route.getLength();
            }
        }
        return length;
    }

    /**
     * get the list of routes in the map
     * @return list of routes
     */
    @Override
    public Collection<Route> getRoutes() {
        return routeList;
    }

}
