package kattis;
import java.util.Scanner;

import graphics.*;
import honeycomb.HoneycombMap;
import searchalgorithms.SearchAlgorithm;

public class Ants {
	//input parameters
	int mEdgeLength = 0;//The length of each side of the hexagon. Max 20, 8000 - 6859 = 1141 max cells
	int mMaxChew = 0;//the maximum number of cells the ant can chew through
	int mStartId = 0;
	int mGoalId = 0;
	int mNWax = 0;//number of wax-filled cells
	int[] mWaxCells = new int[mNWax];//array of ids with waxed-up cells.
	
	public Ants() {
		Scanner sc = new Scanner(System.in);
		
		mEdgeLength = sc.nextInt();
		mMaxChew = sc.nextInt();
		mStartId = sc.nextInt();
		mGoalId = sc.nextInt();
		mNWax = sc.nextInt();
		mMaxChew = sc.nextInt();
		mStartId = sc.nextInt();
		mGoalId = sc.nextInt();
		mNWax = sc.nextInt();

		for (int i = 0; i < mWaxCells.length; i++) {
			mWaxCells[i] = sc.nextInt();
		}
		sc.close();
	}
	
	public Ants(int edgeLength, int maxChew, int startId, int goalId, int nWax,int[] waxCells) {
		super();

		this.mEdgeLength = edgeLength;
		this.mMaxChew = maxChew;
		this.mStartId = startId;
		this.mGoalId = goalId;
		this.mNWax = nWax;
		this.mWaxCells = waxCells;
	}

	public void solution() {
		SearchAlgorithm SA = new SearchAlgorithm();
		HoneycombMap map = new HoneycombMap(mEdgeLength, mWaxCells);

		if (map.getDistance(map.getNode(mStartId), map.getNode(mGoalId)) > mMaxChew) {
			
		} else {
			MapDrawer  awtGraphicsDemo = new MapDrawer(this.mEdgeLength, this.mWaxCells, this.mStartId, this.mGoalId);  
			awtGraphicsDemo.setVisible(true);
			SA.addNodeFiller(awtGraphicsDemo);
	
			//System.out.println(SA.astar(mStartId, mGoalId, map));
			System.out.println(SA.breadthFirst(mStartId, mGoalId, map));
			
			//SearchAlgorithm.dijkstra(mStartId, mGoalId, map);
		}
	}

	public static void main(String args[]) {
		System.out.println("ants");
		int params[] = new int[]{6,7,8,9};
		Ants ants = new Ants(4,8,3,27,4,params);
		ants.solution();
	}
}
