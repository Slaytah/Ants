package graph;

import java.util.ArrayList;

/*
Represents a weighted directed graph (i.e. a collection of nodes that can be
traversed at some or no cost). This feller can explain it better than I ever
could: https://www.redblobgames.com/pathfinding/grids/graphs.html, but it's a
way of representing a map, for example. (or in this case a grid of honeycomb)

Main functionality is get neighbouring nodes, get distance between two nodes, 
and get cost of moving from one node to another node.
*/
public interface Graph {
	int getCost(GraphNode n1, GraphNode n2);
	int getDistance(GraphNode n1, GraphNode n2);
	ArrayList<GraphNode> getNeighbours(GraphNode node);
	GraphNode getNode(int nodeId);
	int getNodeCount();
}