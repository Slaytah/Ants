package honeycomb;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import graph.Graph;
import graph.GraphNode;
import shapes.Cube;
import shapes.Hex;

/**
 * 
 * @author tom
 * 
 * https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples?
 * except it's not a singleton, it just gets used as one in Ants. Wanted to save
 * the url though.
 */

public class HoneycombMap implements Graph{
	private int mEdgeLength;
	HoneycombCell mHexArray[][];
	Point xyArray[];
	
	static class HoneycombCellComparator implements Comparator<HoneycombCell> {
		@Override
		public int compare(HoneycombCell o1, HoneycombCell o2) {
			if (o1.getPriority() > o2.getPriority()) {
				return 1;
			} else if (o1.getPriority() == o2.getPriority()) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	static class NonPriorityComparator implements Comparator<HoneycombCell> {

		@Override
		public int compare(HoneycombCell arg0, HoneycombCell arg1) {
			return 0;
		}
	}

	static Hex cubeToAxial(Cube c) {
		return new Hex(c.getX(), c.getZ());
	}

	static Cube axialToCube(Hex h) {
		return new Cube(h.getQ(), h.getR(), -h.getQ() - h.getR());
	}

	int getDistance(int startCellId, int destCellId, List<HoneycombCell> hexList) {
		return getHexDistance(hexList.get(startCellId).getPos(), hexList.get(destCellId).getPos());
	}

	int getHexDistance(Hex start, Hex destination) {
		Cube s = axialToCube(start);
		Cube d = axialToCube(destination);

		return Cube.getCubeDistance(s, d);
	}

	/**
	 * Construct a map of honeycomb cells
	 * 
	 * @param edgeLength nmber of cells oer edge
	 * @param waxCells array of cell ids 
	 */
	private void generateHoneycombMap(int edgeLength, int[] waxCells) {
		int waxIndex = 0;
		int nbounds = (edgeLength * 2) - 1;//edge 4 = 7 columns and rows (37 cells in total)  edge 5 = 9 = 65?
		int rightCounter = nbounds - 1;
		int id = 1;
		
		mEdgeLength = edgeLength;
		mHexArray = new HoneycombCell[(mEdgeLength * 2 - 1)][(mEdgeLength * 2 - 1)];
		

		// construct map with axial coordinates.
		//row by row..	
		for (int row = 0; row < nbounds; row++) {
			if (row <= (nbounds - 1) / 2) {//break after handle middle (longest) row
				for (int q = (mEdgeLength - 1) - row; q < nbounds; q++) {
					HoneycombCell hexNode = new HoneycombCell(new Hex(q, row), id);

					if (waxIndex < waxCells.length) {
						if (waxCells[waxIndex] == id) {
							hexNode.setWax(true);
							hexNode.setPriority(10000);
							waxIndex++;
						}
					}
					mHexArray[row][q] = hexNode;//shuold probably swap this as we have y coordinate first..
					id++;
				}
			} else {
				// now it swaps around so LEFT side is the same and RIGHT decreases
				for (int q = 0; q < rightCounter; q++) {
					HoneycombCell hexNode = new HoneycombCell(new Hex(q, row), id);
					mHexArray[row][q] = hexNode;
					id++;
				}
				rightCounter--;
			}
		}
	}

	public HoneycombMap(int edgeLength, int[] waxCells) {
		super();
		generateHoneycombMap(edgeLength,waxCells);
	}
	
	public HoneycombCell[][] getMap() {
		return mHexArray;
	}

	static int getNextColumnRight(HoneycombCell cell) {
		return cell.getColumn() + 1;
	}

	static int getNextNextColumnLeft(HoneycombCell cell) {
		return cell.getColumn()- 1;
	}

	static int getNextRowDown(HoneycombCell cell) {
		return cell.getRow() + 1;
	}

	static int getNextRowUp(HoneycombCell cell) {
		return cell.getRow()  - 1;
	}

	/**
	 * Find the neighbouring (n) cells adjacent to cell position P in following order:
	 * 
	 *	   n5 n6 
	 *    n4 P n1
	 *     n3 n2
	 * 
	 * 
	 * This is the fun bit. Honeycomb Cells are stored in hexArray which looks like
	 * this (in an edge = 2  map):
	 * 
	 * Array pos:	0	1	2			 
	 * ID:
	 * 					1	2
	 * 				3 	4	5
	 * 					6	7 
	 * 
	 * So... if P = 4 [1][1],  neighbours = 5(n1),7(n2),6(n3),3(n4),1(n5),2(n6)
	 * 
	 * at array pos [1][2], [2][2],
	 * 
	 * @param cell     the cell whose neighbours you want to find.
	 * @param hexArray 2d array of all cells in the map
	 * @return list of neighbours as HoneycombCells
	 */
	static ArrayList<HoneycombCell> getHexNeighbours(HoneycombCell cell, HoneycombCell[][] hexArray) {
		ArrayList<HoneycombCell> neighbours = new ArrayList<HoneycombCell>();
		HoneycombCell neighbour;

		/**
		 * get cell to right of parameter cell (cells[p+1][r])
		 * 
		 *					  0  1  2		 
		 * 		 x x 		0	[x][x]
		 * 		x p * 		1[x][p][*]	
		 * 		 x x		2	[x][x]
		 */
		neighbour = getHoneycombCell(getNextColumnRight(cell), cell.getRow() , hexArray);
		
		if (neighbour != null) {
			neighbours.add(neighbour);
		}

		/**
		 * get cell below and right of parameter cell 
		 * 		 x x
		 * 		x p x
		 * 		 x *
		 */
		neighbour = getHoneycombCell(cell.getColumn(), getNextRowDown(cell), hexArray);
		
		if (neighbour != null) {
			neighbours.add(neighbour);
		}

		/**
		 * get cell to left and below 
		 * 		 x x
		 * 		x p x
		 * 		 * x
		 */
		if (getNextNextColumnLeft(cell) >= 0) {
			neighbour = getHoneycombCell(getNextNextColumnLeft(cell), getNextRowDown(cell), hexArray);
			
			if (neighbour != null) {
				neighbours.add(neighbour);
			}
			
			 /** ..along with cell left
			 * 
			 *		 x x 
			 *		* p x 
			 *		 x x
			 */
			neighbour = getHoneycombCell(getNextNextColumnLeft(cell), cell.getRow(), hexArray);
			
			if (neighbour != null) {
				neighbours.add(neighbour);
			}
		}
		
		/**
		 * get cell to left and above 
		 * 		 * x
		 * 		x p x
		 * 		 x x
		 * 
		 * ..along with cell right and above
		 *		 x * 
		 *		x p x
		 *		 x x
		 */
		if (getNextRowUp(cell) >= 0) {
			neighbour = getHoneycombCell(cell.getColumn(), getNextRowUp(cell), hexArray);
			
			if (neighbour != null) {
				neighbours.add(neighbour);
			}
			
			neighbour = getHoneycombCell(getNextColumnRight(cell), getNextRowUp(cell), hexArray);
			
			if (neighbour != null) {
				neighbours.add(neighbour);
			}
		}
		return neighbours;
	}
	
	/**
	 * Find the neighbouring (n) cells adjacent to cell position P in following order:
	 * 
	 *	   n5 n6 
	 *    n4 P n1
	 *     n3 n2
	 * 
	 * 
	 * This is the fun bit. Honeycomb Cells are stored in hexArray which looks like
	 * this (in an edge = 2  map):
	 * 
	 * Array pos:	0	1	2			 
	 * ID:
	 * 					1	2
	 * 				3 	4	5
	 * 					6	7 
	 * 
	 * So... if P = 4 [1][1],  neighbours = 5(n1),7(n2),6(n3),3(n4),1(n5),2(n6)
	 * 
	 * at array pos [1][2], [2][2],
	 * 
	 * @param cell     the cell whose neighbours you want to find.
	 * @param hexArray 2d array of all cells in the map
	 * @return list of neighbours as HoneycombCells
	 */
	static ArrayList<GraphNode> getNeighbours(HoneycombCell cell, HoneycombCell[][] hexArray) {
		ArrayList<GraphNode> neighbours = new ArrayList<GraphNode>();
		HoneycombCell neighbour;

		/**
		 * get cell to right of parameter cell (cells[p+1][r])
		 * 
		 *					  0  1  2		 
		 * 		 x x 		0	[x][x]
		 * 		x p * 		1[x][p][*]	
		 * 		 x x		2	[x][x]
		 */
		neighbour = getHoneycombCell(getNextColumnRight(cell), cell.getRow() , hexArray);
		
		if (neighbour != null) {
			neighbours.add(neighbour);
		}

		/**
		 * get cell below and right of parameter cell 
		 * 		 x x
		 * 		x p x
		 * 		 x *
		 */
		neighbour = getHoneycombCell(cell.getColumn(), getNextRowDown(cell), hexArray);
		
		if (neighbour != null) {
			neighbours.add(neighbour);
		}

		/**
		 * get cell to left and below 
		 * 		 x x
		 * 		x p x
		 * 		 * x
		 */
		if (getNextNextColumnLeft(cell) >= 0) {
			neighbour = getHoneycombCell(getNextNextColumnLeft(cell), getNextRowDown(cell), hexArray);
			
			if (neighbour != null) {
				neighbours.add(neighbour);
			}
			
			 /** ..along with cell left
			 * 
			 *		 x x 
			 *		* p x 
			 *		 x x
			 */
			neighbour = getHoneycombCell(getNextNextColumnLeft(cell), cell.getRow(), hexArray);
			
			if (neighbour != null) {
				neighbours.add(neighbour);
			}
		}
		
		/**
		 * get cell to left and above 
		 * 		 * x
		 * 		x p x
		 * 		 x x
		 * 
		 * ..along with cell right and above
		 *		 x * 
		 *		x p x
		 *		 x x
		 */
		if (getNextRowUp(cell) >= 0) {
			neighbour = getHoneycombCell(cell.getColumn(), getNextRowUp(cell), hexArray);
			
			if (neighbour != null) {
				neighbours.add(neighbour);
			}
			
			neighbour = getHoneycombCell(getNextColumnRight(cell), getNextRowUp(cell), hexArray);
			
			if (neighbour != null) {
				neighbours.add(neighbour);
			}
		}
		return neighbours;
	}
	/**
	 * go through array until you find id. Could be optimised. Compare id with length of array
	 * 
	 * 
	 * @param id
	 * @param hexArray
	 * @return
	 */
	public static HoneycombCell getHoneycombCell(int id, HoneycombCell[][] cellArray) {
		HoneycombCell cell = null;
		int counter = 0;

		for (int r = 0; r < cellArray.length; r++) {
			for (int q = 0; q < cellArray.length; q++) {
				cell = cellArray[r][q];

				if (cell != null) {
					counter++;
					if (counter == id) {
						cell = cellArray[r][q];
						return cell;
					}
				}
			}
		}
		return cell;
	}
	
	/**
	 * Return the array position (and implicitly, coordinate) of the cell
	 * 
	 * 
	 * @param id cell id 
	 * @param cellArray  the array of cells
	 * @return 2 member array conatining row and column of cell
	 */
	public static int[] getHoneycombIndex(int id,HoneycombCell[][] cellArray) {
		int returnValue[] = new int[2];
		int counter = 0;
		
		for (int r = 0; r < cellArray.length; r++) {
			for (int q = 0; q < cellArray.length; q++) {
				if (cellArray[r][q] != null) {
					counter++;
					if (counter == id) {
						returnValue[0] = r;
						returnValue[1] = q;
						return returnValue;
					}
				}
			}
		}
		return null;
	}


	/**
	 * Get a cell from a 2d array of cells
	 * 
	 * @param currentColumn 
	 * @param currentRow
	 * @param cellArray
	 * @return cell if it exists and is not wax, otherwise null
	 */
	static HoneycombCell getHoneycombCell(int currentColumn, int currentRow, HoneycombCell[][] cellArray) {
		HoneycombCell cell = null;

		if (currentColumn < cellArray.length && currentRow < cellArray.length) {
			cell = cellArray[currentRow][currentColumn];

			if (cell != null) {
				if (cell.isWax()) {
					cell = null;
				}
			}
		}
		return cell;
	}

	@Override
	public int getCost(GraphNode n1, GraphNode n2) {
		return 0;
	}

	@Override
	public int getDistance(GraphNode n1, GraphNode n2) {		
		return getHexDistance(getHoneycombCell(n1.getId(),mHexArray).getPos(), getHoneycombCell(n2.getId(),mHexArray).getPos());
	}

	@Override
	public ArrayList<GraphNode> getNeighbours(GraphNode node) {
		HoneycombCell cell = getHoneycombCell(node.getId(), mHexArray);
		
		return getNeighbours(cell, mHexArray);
	}

	@Override
	public GraphNode getNode(int nodeId) {
		return getHoneycombCell(nodeId, mHexArray) ;
	}

	@Override
	public int getNodeCount() {
		return (int) (Math.pow(mEdgeLength, 3) - Math.pow(mEdgeLength - 1, 3));
	}	
}
