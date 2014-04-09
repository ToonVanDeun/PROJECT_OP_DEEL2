/**
 * 
 */
package worms.model;

import java.math.BigInteger;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Raw;


/**
 * @author Toon
 *
 */
	public class Food extends Object{
	
		/**
		 * Initialize this food in a given world
		 *
		 * @param  world
		 *         The world for this new food.
		 * @effect This new food is initialized as a new object with
		 *         in a given world.
		 *       | super(world)
		 * @effect The food is placed in a position, adjacent to impassable terrain
		 * 			|.....
		 */
		@Raw
		public Food(World world) throws IllegalArgumentException {
			super(world);
			this.setRadius();
			this.setWorldTo(world);
			this.position = new Position(world, this);
			
			
		}
		
		public Food(World world, double xpos, double ypos) {
			super(world);
			this.setRadius();
			this.position = new Position(xpos, ypos);
			
			
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
