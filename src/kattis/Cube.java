package kattis;

/**
 * A point in three dimensional space. Doesn't have to be a cube really,
 * but this makes handling a hex grid sort of easier in terms of calculating distance
 * something about symmetry too, though I haven't figured out how that helps
 * https://www.redblobgames.com/grids/hexagons/
 * 
 * @author tom 
 *
 */
public class Cube {
	private int x, y, z;

	public Cube(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.z;
	}
	
	public static int getCubeDistance(Cube start, Cube destination) {
		return (Math.abs(start.x - destination.x) + Math.abs(start.y - destination.y)
				+ Math.abs(start.z - destination.z)) / 2;
	}
}
