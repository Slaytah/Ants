package honeycomb;

import graph.GraphNode;
import shapes.Hex;

/**
 * A honeycomb cell. Can be filled with wax which cannot be eaten through.
 * 
 * Has an id because problem uses IDs to identify wax cells, start cell, end
 * cell difficult to calculate distances from a list of IDs..
 * 
 * @author tom
 *
 */

class HoneycombCell implements GraphNode{
	private boolean isWax = false;
	private int mId = -1; // 1 based id of hex. Hex list pos + 1;
	private Hex pos;// position in the hex grid. REdundant as it can be derived from [x],[y]; <--WHAT x and y, you prick?
	private int mPriority = 0;//essential to this implementation?

	public HoneycombCell(Hex pos, int id) {
		super();
		this.pos = pos;
		this.mId = id;
	}
	
	@Override
	public String toString() {
		return Integer.toString(mId) + " position=" + pos.getR() + ", " + pos.getQ() + " (p" + Integer.toString(mPriority) + ")";
	}

	public int getRow() {
		return pos.getR();
	}
	
	public int getColumn() {
		return pos.getQ();
	}
	
	public Hex getPos() {
		return pos;
	}
	
	//think we only nseed these. Everythig else is same  as array[row],[colum])
	public void setId(int id) {
		this.mId = id;
	}

	public int getId() {
		return this.mId;
	}

	public void setWax(boolean isWax) {
		this.isWax = isWax;
	}

	public boolean isWax() {
		return isWax;
	}
	
	public void setPriority(int priority) {
		mPriority = priority;
	}
	
	public int getPriority() {
		return mPriority;
	}
}
