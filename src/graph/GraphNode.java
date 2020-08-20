package graph;

import java.util.Comparator;

/*
Interface for a node in a graph that can be traversed using 
a priority-based pathfinding algorithm such as Dijkstra
*/
public interface GraphNode {
	public int getId();
	public void setId(int id);
	public int getPriority();
	public void setPriority(int priority);
	static public Comparator<GraphNode> getComparator() {
		return new Comparator<GraphNode>() {
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
