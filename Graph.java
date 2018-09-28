/**
 * Author's name: Tejaswini Jagtap
 */

package student;

import model.RailroadBarons;
import model.RailroadMap;
import model.Station;

import java.util.*;

public class Graph {

    /**
     * hashmap that has all the border stations with value as its direction
     */
    private HashMap<Station, String> endStations;
    /**
     * hashmap of all station with value as their neighbors
     */
    private HashMap<Station, HashSet<Station>> listHashMap;
    /**
     * counter for the no. of routes claimed
     */
    private int i;
    /**
     * railroadBarons
     */
    private RailroadBarons railroadBarons;

    /**
     * Constructor for graph
     * @param railroadBarons railroadBarons
     */
    public Graph(RailroadBarons railroadBarons) {
        this.railroadBarons = railroadBarons;
        listHashMap = new HashMap<>();
        endStations = new HashMap<>();
        this.i = 0;
    }

    /**
     * add station to the listHashmap
     * @param station1 one of the stations to be added
     * @param station2 one o the stations to be added
     */
    public void addStation(Station station1, Station station2) {

        if (!listHashMap.containsKey(station1)) {
            HashSet<Station> neighbors = new HashSet<>();
            neighbors.add(station2);
            listHashMap.put(station1, neighbors);
        } else {
            HashSet space = listHashMap.get(station1);
            space.add(station2);
        }

        if (!listHashMap.containsKey(station2)) {
            HashSet<Station> neighbors = new HashSet<>();
            neighbors.add(station1);
            listHashMap.put(station2, neighbors);
        } else {
            HashSet space = listHashMap.get(station2);
            space.add(station1);
        }
    }

    /**
     * adding border stations to the hashmap and returning it
     * @return hashmap that contains station with their direction
     */
    public HashMap<Station, String> getEndStation() {
        for (Station station : listHashMap.keySet()) {
            if (station.getRow() == 0) {
                endStations.put(station, "northernmost");
            }
            if (station.getCol() == 0) {
                endStations.put(station, "westernmost");
            }
            if (station.getRow() + 1 == this.railroadBarons.getRailroadMap().getRows()) {
                endStations.put(station, "southernmost");
            }
            if (station.getCol() + 1 == this.railroadBarons.getRailroadMap().getCols()) {
                endStations.put(station, "easternmost");
            }
        }
        return endStations;
    }

    /**
     * findroute from one border station to the other if any
     * @param endStations hashmap  of border stations with their direction
     * @return direction of the station if any border to border route exist from that station
     */
    public String findRoute (HashMap<Station, String> endStations){
        boolean check;
        for (Station station: endStations.keySet()){
            Set<Station> visited = new HashSet<>();
            visited.add(station);
            check = visitDfs(station, station, visited, 0);
            if (check){
                return endStations.get(station);
            }
        }
        return "No Cross-Country";
    }

    /**
     * helpinf function for searching the route by using DFS
     * @param startStation start station (border station)
     * @param station current station
     * @param visited list of visited station
     * @param i counter for no. of routes claimed
     * @return boolean to if any route exist
     */
    public boolean visitDfs(Station startStation, Station station, Set<Station> visited, int i) {

        for (Station station1: listHashMap.get(station)){
            if (!visited.contains(station1)){
                visited.add(station1);
                i++;
                if (isGoal(startStation, station1) && i >= 3){
                    return true;
                }
                else {
                    return visitDfs(startStation, station1, visited, i);
                }
            }
        }
        return false;
    }

    /**
     * check whether the current station is goal with the start station
     * @param start start station
     * @param finish current station
     * @return boolean
     */
    public boolean isGoal(Station start, Station finish){
        if (endStations.containsKey(start) && endStations.containsKey(finish)) {
            if (getEndStation().get(start).equals("westernmost") && getEndStation().get(finish).equals("easternmost") || getEndStation().get(start).equals("easternmost") && getEndStation().get(finish).equals("westernmost") || getEndStation().get(start).equals("northernmost") && getEndStation().get(finish).equals("southernmost") || getEndStation().get(start).equals("southernmost") && getEndStation().get(finish).equals("northernmost")) {
                return true;
            }
        }
        return false;
    }

}