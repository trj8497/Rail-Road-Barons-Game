/**
 * Author's name: Tejaswini Jagtap
 */
package student;

import model.Space;

public class SpaceImpl implements Space {

    /**
     * row of the space
     */
    private int row;
    /**
     * column of the space
     */
    private int col;

    /**
     * constructor to initialize the row and column
     * @param row row
     * @param col column
     */
    public SpaceImpl(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * get the row of the space
     * @return row
     */
    @Override
    public int getRow() {
        return this.row;
    }

    /**
     * get the column fof the space
     * @return column
     */
    @Override
    public int getCol() {
        return this.col;
    }

    /**
     * checks whether this space and some other space come at the same location
     * @param other The other space to which this space is being compared for
     *              collocation.
     *
     * @return boolean
     */
    @Override
    public boolean collocated(Space other) {
        if (other.getRow()==this.row && other.getCol()==this.col){
            return true;
        }
        return false;
    }
}
