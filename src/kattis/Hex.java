package kattis;

/**
 * An axial coordinate in a hex grid
 * 	 /\
 *  |  |   
 *	/\/\
 * |  |	|
 *	\/\/
 * See: https://www.redblobgames.com/grids/hexagons/ 
 *
 */

class Hex {
	private int column = 0;
	private int row = 0;

	public Hex(int q, int r) {
		this.column = q;
		this.row= r;
	}
	
	public int getQ() {
		return column;
	}
	
	public int getR() {
		return row;
	}
}
