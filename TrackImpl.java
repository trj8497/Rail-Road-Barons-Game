/**
 * Author's name: Tejaswini Jagtap
 */
package student;

import model.*;

public class TrackImpl implements Track {

    /**
     * orientation of the route that has this track
     */
    private Orientation orientation;
    /**
     * name of the player who owns this track
     */
    private Baron baron;
    /**
     * row of the track
     */
    private int row;
    /**
     * column of the track
     */
    private int col;
    /**
     * route that has this track
     */
    private Route route;

    /**
     * constructor for initializing orientation, baron, row, column, route
     * @param orientation orientation of the route that has this track
     * @param baron name of the player who owns it
     * @param row row of the track
     * @param col column of the track
     * @param route route that this track
     */
    public TrackImpl(Orientation orientation, Baron baron, int row, int col, Route route){
        this.baron = baron;
        this.col = col;
        this.orientation = orientation;
        this.row = row;
        this.route = route;
    }

    /**
     * returns the orientation of the route that has this track
     * @return orientation
     */
    @Override
    public Orientation getOrientation() {
        return this.orientation;
    }

    /**
     * get baron
     * @return baron
     */
    @Override
    public Baron getBaron() {
        return this.baron;
    }

    /**
     * gets the route that has this track
     * @return route
     */
    @Override
    public Route getRoute() {
        return this.route;
    }

    /**
     * get the row of the track
     * @return row
     */
    @Override
    public int getRow() {
        return this.row;
    }

    /**
     * get the column of the track
     * @return column
     */
    @Override
    public int getCol() {
        return this.col;
    }

    /**
     * checks whether this track and some other space comes on the same location
     * @param other The other space to which this space is being compared for
     *              collocation.
     *
     * @return boolean
     */
    @Override
    public boolean collocated(Space other) {
        if(other.getCol() == this.col && other.getRow() == this.row){
            return true;
        }
        return false;
    }
}
