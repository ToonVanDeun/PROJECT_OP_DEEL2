/**
 * 
 */
package worms.model;

import java.util.Random;

/**
 * @author Toon
 *
 */
	public class Food {
	
	public Food(double xpos, double ypos) {
		this.position = new Position(xpos, ypos);
		this.setRadius();
	}
	public Food(World world) {
		Random perimeter = world.getPerimeter();
		this.setXpos(perimeter.nextDouble()*world.getWidth());
		this.setYpos(perimeter.nextDouble()*world.getHeight());
		
	}
	
	public void setRadius() {
		this.radius = 0.20;
	}
	public double getRadius() {
		return this.radius;
	}
	public void setXpos(double xpos) {
		position.setXpos(xpos);
	}
	
	public double getXpos() {
		return position.getXpos();
	}
	public double getYpos() {
		return position.getYpos();
	}
	public void setYpos(double ypos) {
		position.setYpos(ypos);
	}
	private double radius;
	private Position position;
	
	

}
