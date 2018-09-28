/**
 * Author's name: Tejaswini Jagtap
 */
package student;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class RouteImpl implements Route {

    /**
     * name of the player
     */
    private Baron baron;
    /**
     * lis tof tracks int he route
     */
    private ArrayList<Track> listTrack;
    /**
     * origin station of the route
     */
    private Station origin;
    /**
     * destination station of the route
     */
    private Station destination;
    /**
     * orientation of the route
     */
    private Orientation orientation;


    /**
     * construtor to assign baron, list of tracks, origin station and destination station
     * @param origin origin station
     * @param destination destination station
     * @param baron name
     */
    public RouteImpl(Station origin, Station destination, Baron baron){
        this.baron = baron;
        this.listTrack = new ArrayList<>();
        if (origin.getRow() == destination.getRow()){
            orientation = Orientation.HORIZONTAL;
        }
        else if (origin.getCol() == destination.getCol()){
            orientation = Orientation.VERTICAL;
        }
        if (orientation==Orientation.HORIZONTAL){
            for (int j = origin.getCol()+1; j < destination.getCol(); j++){
                Track track = new TrackImpl(Orientation.HORIZONTAL, baron, origin.getRow(), j, this);
                listTrack.add(track);
            }
        }
        else{
            for (int i = origin.getRow()+1; i< destination.getRow(); i++){
                Track track = new TrackImpl(Orientation.VERTICAL, baron, i, origin.getCol(), this);
                listTrack.add(track);
            }
        }
        this.origin = origin;
        this.destination = destination;
    }

    /**
     *gets the baron
     * @return baron
     */
    @Override
    public Baron getBaron() {
        return baron;
    }

    /**
     * gets the origin station
     * @return origin station
     */
    @Override
    public Station getOrigin() {
        return this.origin;
    }

    /**
     * gets the destination station
     * @return destination station
     */
    @Override
    public Station getDestination() {
        return this.destination;
    }

    /**
     * get orientation of the route
     * @return orientation of the route
     */
    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * get list of tracks that route has
     * @return lis tof tracks
     */
    @Override
    public List<Track> getTracks() {
        return this.listTrack;
    }

    /**
     * get length of the list of tracks i.e. get no of tracks in the route
     * @return length of list of tracks
     */
    @Override
    public int getLength() {
        return listTrack.size();
    }

    /**
     * gets the points earned by the baron
     * @return points
     */
    @Override
    public int getPointValue() {
        if (this.getLength()==1){
            return 1;
        }
        else if(this.getLength()==2){
            return 2;
        }
        else if(this.getLength()==3){
            return 4;
        }
        else if(this.getLength()==4){
            return 7;
        }
        else if(this.getLength()==5){
            return 10;
        }
        else if(this.getLength()==6){
            return 15;
        }
        else{
            return (5*this.getLength()-3);
        }
    }

    /**
     * checks whether the route has any spaces
     * @param space The {@link Space} that may be in this route.
     *
     * @return boolean
     */
    @Override
    public boolean includesCoordinate(Space space) {
        for (Track track: listTrack){
            if (space.getRow() == track.getRow() && space.getCol() == track.getCol()){
                return true;
            }
            else if(space.getRow() == origin.getRow() && space.getCol() == origin.getCol()){
                return true;
            }
            else if(space.getRow() == destination.getRow() && space.getCol() == destination.getCol()){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    /**
     * checks whether the route can be claimed by the claimant claims the route
     * @param claimant The {@link Baron} attempting to claim the route. Must
     *                 not be null or {@link Baron#UNCLAIMED}.
     * @return boolean
     */
    @Override
    public boolean claim(Baron claimant) {
        if (  claimant == null || claimant == Baron.UNCLAIMED){
            return false;
        }
        else if(this.baron.equals(Baron.UNCLAIMED)){
            this.baron = claimant;
            return true;
        }
        return false;
    }
}
