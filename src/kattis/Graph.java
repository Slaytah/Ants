package kattis;

import java.util.ArrayList;

public interface Graph {
	int getCost(GraphNode n1, GraphNode n2);
	int getDistance(GraphNode n1, GraphNode n2);
	ArrayList<GraphNode> getNeighbours(GraphNode node);
	GraphNode getNode(int nodeId);
	int getNodeCount();
}
