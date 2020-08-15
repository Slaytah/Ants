package searchalgorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

import graphics.FillNode;
import kattis.Graph;
import kattis.GraphNode;

public class SearchAlgorithm {
	FillNode mNodeFiller;
	
	public void addNodeFiller(FillNode nodeFiller) {
		if (mNodeFiller == null) {
			mNodeFiller = nodeFiller;
		}
	}

	public int astar(int startId, int goalId, Graph graph) {
		int nodeCount = graph.getNodeCount();
		int[] cameFrom = new int[nodeCount + 1];// ids are 1 based
		int[] costSoFar = new int[nodeCount + 1];// cost from start to node [n]
		int newCost;// cost to get here from start

		PriorityQueue<GraphNode> frontier = new PriorityQueue<GraphNode>(GraphNode.getComparator());
		ArrayList<GraphNode> neighbours;
		GraphNode current, currentNeighbour;
		GraphNode start = graph.getNode(startId);
		GraphNode goal = graph.getNode(goalId);

		frontier.add(start);
		cameFrom[start.getId()] = 0;
		costSoFar[start.getId()] = 0;// cost to get to node "id" from node start

		while (!frontier.isEmpty()) {
			System.out.println("queue: " + frontier);
			current = frontier.remove();			
			System.out.println("current = " + current.getId());

			// display current cell y filling it in
			mNodeFiller.fillNode(current.getId());

			if (current.getId() != goal.getId()) {
				neighbours = graph.getNeighbours(current);

				for (int i = 0; i < neighbours.size(); i++) {
					currentNeighbour = neighbours.get(i);

					// display neighbour in another colour
					mNodeFiller.fillNode(currentNeighbour.getId());

					// cost is how far (or easy, but not in hex case, although we could use isWax)
					// to neighbouring node. Get distance only ever returns 1 in a grid
					// so... record how far this node is from our start point. update new cost with
					// distance from source ( and distance to goal if A*)
					newCost = costSoFar[current.getId()] + 1;// getDistance(current.getPos(),
																// currentNeighbour.getPos());Always returns 1
					//System.out.println("\n neighbour1 id = " + currentNeighbour.getId() + " cost so far "
						//	+ costSoFar[current.getId()] + " new cost " + newCost);

					// only add to list if it hasn't already been examined (and thus had costSoFar
					// != 0)
					// or in case we there is a better route from a different neighbour. Again,
					// doesn't happen in
					// hex grid as all neighbouring nodes are distance 1 apart
					// better option

					if (costSoFar[currentNeighbour.getId()] == 0
							/* unexamined */ || newCost < costSoFar[currentNeighbour.getId()]/* found better route */) {

						costSoFar[currentNeighbour.getId()] = newCost;
						currentNeighbour.setPriority(newCost
								+ /* heuristic */graph.getDistance(goal, currentNeighbour));
						frontier.add(currentNeighbour);

						cameFrom[currentNeighbour.getId()] = current.getId();
					}
				}

			} else {
				// we're done
				ArrayList<Integer> path = new ArrayList<Integer>();

				while (current.getId() != start.getId()) {
					path.add(current.getId());
					System.out.print(current.getId() + " ");
					current.setId(cameFrom[current.getId()]);
				}
				return path.size();
			}
		}
		return 0;
	}

	/**
	 * Dijkstra is just A* without the heuristic, I thbink
	 * 
	 * @param mStartId
	 * @param mGoalId
	 * @param hexNodeArray
	 * @return
	 */
	public static int dijkstra(int startId, int goalId, Graph graph) {
		int nodeCount = graph.getNodeCount();
		int[] cameFrom = new int[nodeCount + 1];// ids are 1 based
		int[] costSoFar = new int[nodeCount + 1];
		int newCost;
		PriorityQueue<GraphNode> frontier = new PriorityQueue<GraphNode>(GraphNode.getComparator());
		ArrayList<GraphNode> neighbours;
		GraphNode current, currentNeighbour;
		GraphNode start = graph.getNode(startId);
		GraphNode goal = graph.getNode(goalId);

		frontier.add(start);
		cameFrom[start.getId()] = 0;
		costSoFar[start.getId()] = 1;// cost to get to node "id" from node start

		while (!frontier.isEmpty()) {
			current = frontier.remove();
			System.out.println("current = " + current.getId());

			if (current.getId() != goal.getId()) {
				neighbours = graph.getNeighbours(current);

				for (int i = 0; i < neighbours.size(); i++) {
					currentNeighbour = neighbours.get(i);
					// cost is how far (or easy, but not in hex case, although we could use isWax)
					// to neighbouring node. Get distance only ever returns 1 in a grid
					// so... record how far this node is from our start point. update new cost with
					// distance from source and distance to goal
					newCost = costSoFar[current.getId()] + 1;// getDistance(current.getPos(),
																// currentNeighbour.getPos());
					System.out.println("\n neighbour id = " + currentNeighbour.getId() + " cost so far "
							+ costSoFar[current.getId()] + " new cost " + newCost);

					// only add to list if it hasn't already been examined (and thus had costSoFar
					// != 0)
					// or in case we there is a better route from a different neighbour. Again,
					// doesn't happen in hex grid as all neighbouring nodes are distance 1 apart
					// better option

					if (costSoFar[currentNeighbour.getId()] == 0
							/* unvisited */ || newCost < costSoFar[currentNeighbour.getId()]) {

						costSoFar[currentNeighbour.getId()] = newCost;

															  // this is where we add the heuristic. IF we skip this, is it dijksra?
						currentNeighbour.setPriority(newCost); //getDistance(goal.getPos(), currentNeighbour.getPos());
						frontier.add(currentNeighbour);

						cameFrom[currentNeighbour.getId()] = current.getId();
					}
				}

			} else {
				// we're done
				// System.out.println("FOUND IT!");
				ArrayList<Integer> path = new ArrayList<Integer>();

				while (current.getId() != start.getId()) {
					path.add(current.getId());
					System.out.print(current.getId() + " ");
					current.setId(cameFrom[current.getId()]);
				}
				return path.size();
			}
		}
		return 0;
	}

	/*
	 * Add start node to queue Add start node to visited set
	 * 
	 * while (queue != empty) { current = queue.get();
	 * 
	 * current.getNeighbours() {
	 * 
	 * }
	 * 
	 */
	static int breadthFirst(int startId, int goalId, Graph graph) {
		int nodeCount = graph.getNodeCount();
		int[] visited = new int[nodeCount];
		PriorityQueue<GraphNode> frontier = new PriorityQueue<GraphNode>(GraphNode.getComparator());
		ArrayList<GraphNode> neighbours;
		GraphNode current, next;
		GraphNode start = graph.getNode(startId);
		GraphNode goal = graph.getNode(goalId);

		frontier.add(start);
		visited[start.getId()] = 0;

		while (!frontier.isEmpty()) {
			current = frontier.remove();

			if (current.getId() != goal.getId()) {
				neighbours = graph.getNeighbours(current);

				for (int i = 0; i < neighbours.size(); i++) {
					next = neighbours.get(i);
					if (visited[next.getId()] == 0) {
						frontier.add(next);
						visited[next.getId()] = 1;
					}
				}
			}
		}
		return 0;
	}
}
