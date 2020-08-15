package graphics;

import java.awt.*;
import java.awt.event.*;

public class MapDrawer extends Frame implements FillNode {
	private static final long serialVersionUID = 1L;
	private static int SIDELENGTH = 40;
	private static int DIAGONAL = 35;
	int mInitialX = 400;
	int mInitialY = 200;
	int mEdgeLength;
	int waxCells[];
	int mStart;
	int mFinish;
	Point mXyArray[]; 
	int mCurrentId = 1;

	public MapDrawer(int edgeLength, int waxCells[], int start, int finish) {
		super("Java AWT Examples");
		prepareGUI();
		this.mEdgeLength = edgeLength;
		this.waxCells = waxCells;
		this.mStart = start;
		this.mFinish = finish;
		
		int total = 0;
		
		for (int i = 0; i < edgeLength-1;i++){
			total += edgeLength + i;
		}
		total += total + ((2 *edgeLength) -1);
		
		mXyArray = new Point[total];
	}
	
	private int convertIdToIndex(int id) throws Exception {
		if (mXyArray.length > 0) {
			if (id > 0 && id <= mXyArray.length) {
				return id-1;
			} else {
				throw new Exception("id out of bounds: " + id);
			}
		} else {
			throw new Exception("array was null");
		}
	}

	private void prepareGUI() {
		setSize(2000, 2000);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
	}

	private void drawRow(Graphics g, Color colour, int y) {
		for (int x = 0; x < mEdgeLength + y; x++) {
			drawHex(mInitialX + x * (DIAGONAL * 2), mInitialY, g, colour, false, Integer.toString(mCurrentId));
			
			try {
				mXyArray[convertIdToIndex(mCurrentId)] = new Point(mInitialX + x * (DIAGONAL * 2),mInitialY);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mCurrentId++;
		}
	}

	private void drawMap(Graphics g) {
		int y;

		for (y = 0; y < mEdgeLength; y++) {
			drawRow(g, Color.BLACK, y);
			mInitialY += SIDELENGTH * 1.5;// next row
			mInitialX -= DIAGONAL;// step one to left as rows get longer until mid point
		}

		// now draw the rows under the widest point
		y -= 2;
		mInitialX += DIAGONAL * 2;

		while (y >= 0) {
			drawRow(g, Color.BLACK, y);
			mInitialY += SIDELENGTH * 1.5;
			mInitialX += DIAGONAL;// step right as rows now getting shorter
			y--;
		}
		
		//finally, fill the start, end and wax cells.
		try {
			Point p = mXyArray[convertIdToIndex(mStart)];
			drawHex(p.x, p.y,g,Color.GREEN,true,Integer.toString(mStart));
			
			p = mXyArray[convertIdToIndex(mFinish)];
			drawHex(p.x, p.y,g,Color.YELLOW,true,Integer.toString(mFinish));
			
			for(int i = 0; i < waxCells.length;i++) {
				p = mXyArray[convertIdToIndex(waxCells[i])];
				drawHex(p.x, p.y,g,Color.BLUE,true, Integer.toString(waxCells[i]));
			}
			
		} catch (Exception e) {
			
		}
		
		mCurrentId = 1;
		mInitialX = 400;
		mInitialY = 200;
	}

	/**
	 * Draw a hexagon, pointy end up (center should be x, y -SIDELENGTH)
	 * g.drawString("c", x, y-SIDELENGTH);
	 * 
	 * @param x      the x position of the top point
	 * @param y      the y position of the top point
	 * @param g      some magical graphical bullshit
	 * @param colour
	 * @param fill
	 */
	private void drawHex(int x, int y, Graphics g, Color colour, boolean fill, String label) {
		Graphics2D g2 = (Graphics2D) g;
		Polygon p;
		Font font = new Font("Serif", Font.PLAIN, 12);

		g2.setColor(colour);

		int xArray[] = new int[] { x, x + DIAGONAL, x + DIAGONAL, x, x - DIAGONAL, x - DIAGONAL };
		int yArray[] = new int[] { y - SIDELENGTH, y - SIDELENGTH / 2, y + SIDELENGTH / 2, y + SIDELENGTH,
				y + SIDELENGTH / 2, y - SIDELENGTH / 2 };

		p = new Polygon(xArray, yArray, 6);
	
		if (fill) {
			g2.fillPolygon(p);
		}
		
		g2.setColor(colour);
	
		g2.setFont(font);
		g2.drawPolygon(p);
		g2.setColor(Color.BLACK);
		g.drawString(label, x, y);
		
	}

	@Override
	public void paint(Graphics g) {
		drawMap(g);
	}

	@Override
	public void fillNode(int cellId) {
		try {
			//Point p = mXyArray[convertIdToIndex(cellId)];
			
			//drawHex(p.x, p.y,getGraphics(),Color.RED,true,Integer.toString(mFinish));
			//mark node as dirty and trigger a redraw
			//update(getGraphics());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create array of hex positions by id? then hexMap can use INterface to send
	 * which UI position to update
	 */
}