package ElectricHockey;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall {
	private int x, y;
	private int width, height;
	
	public Wall(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.GRAY);
		g2d.fillRect(x, y, width, height);
	}

	public int getX() { return x; }
	public int getY() { return y; }

	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
