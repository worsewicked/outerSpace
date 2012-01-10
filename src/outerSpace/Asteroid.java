package outerSpace;

import java.awt.Color;
import acm.graphics.*;

public class Asteroid extends GCompound{

	public Asteroid(double r) {
	      GOval asteroid = new GOval(2 * r, 2 * r);
	      asteroid.setFillColor(Color.WHITE);
	      asteroid.setFilled(true);
	      add(asteroid, -r, -r);
	      markAsComplete();
	}
	public Asteroid(double ar, double ax, double ay) {
	      this(ar);
	      setLocation(ax, ay);
	}
	
}