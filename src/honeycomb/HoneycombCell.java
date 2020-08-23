package honeycomb;

import java.util.Comparator;

import graph.GraphNode;
import shapes.Hex;

/**
 * A honeycomb cell. Can be filled with wax which cannot be eaten through.
 * 
 * Has an id because problem uses ids to identify wax cells, start cell, end
 * cell difficult to claculate distances from a list of ids..
 * 
 * @author tom
 *
 */

class HoneycombCell implements GraphNode{
	private boolean isWax = false;
	private int mId = -1; // 1 based id of hex. Hexlist pos + 1;
	private Hex pos;// position in the hex grid. REdundant as it can be derived from [x],[y];
	private int mPriority = 0;//essential to this implementation?
	
	private static Comparator<GraphNode> mComparator = null;

	public HoneycombCell(Hex pos, int id) {
		super();
		this.pos = pos;
		this.mId = id;
		
		if(mComparator == null) {
			mComparator = new Comparator<GraphNode>() {
				@Override
				public int compare(GraphNode o1, GraphNode o2) {
					if (o1.getPriority() > o2.getPriority()) {
						return 1;
					} else if (o1.getPriority() == o2.getPriority()) {
						return 0;
					} else {
						return -1;
					}
				}
			};
		}
	}
	
	@Override
	public String toString() {
		return Integer.toString(mId) + " (p" + Integer.toString(mPriority) + ")";
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
