/**
 * Author's name: Tejaswini Jagtap
 */
package student;

import model.Space;
import model.Station;

import java.util.ArrayList;
import java.util.Objects;

public class StationImpl implements Station {

    /**
     * name of the station
     */
    private String name;
    /**
     * row of the station
     */
    private int row;
    /**
     * column of the station
     */
    private int col;
    /**
     * id of the statiom
     */
    private int id;

    /**
     * constructor to initialize the name, row, column, id
     * @param id id of the station
     * @param row row of the station
     * @param col column of the station
     * @param name name of the station
     */
    public StationImpl(int id, int row, int col, String name){
        this.name = name;
        this.row = row;
        this.col = col;
        this.id = id;
    }

    /**
     * get the name of the station
     * @return name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * get the row of the station
     * @return row
     */
    @Override
    public int getRow() {
        return this.row;
    }

    /**
     * get the column of the station
     * @return column
     */
    @Override
    public int getCol() {
        return this.col ;
    }


    /**
     * checks whether thi station and some other space comes on the same location
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

    /**
     * equals method for the class
     * @param o object of the class
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationImpl)) return false;
        StationImpl station = (StationImpl) o;
        return getRow() == station.getRow() &&
                getCol() == station.getCol() &&
                id == station.id &&
                Objects.equals(getName(), station.getName());
    }

    /**
     * hashCode for the class
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRow(), getCol(), id);
    }

    public String toString(){
        return "" + id;
    }
}
