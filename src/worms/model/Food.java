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
		 * @effect This new food is initialized as a new object in a given world.
		 *       | super(world)
		 * @effect The food is placed in a position, adjacent to impassable terrain
		 * 			|.....
		 */
		@Raw
		public Food(World world) throws IllegalArgumentException {
			super(world);
			this.setRadius();
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
		
		//position (defensive)
		
		
		/**
		 * Returns the x-position of the food.
		 */
		public double getXpos() {
			return position.getXpos();
		}
		/**
		 * Returns the y-position of the food.
		 */
		public double getYpos() {
			return position.getYpos();
		}
		
		/**
		 * Sets the x-position of the food.
		 * @param xpos
		 * 			The (new) x-position of the food
		 * @post	the given x-position is the new x-position of the food.
		 * 			| new.getXpos() == xpos
		 * @throws	IllegalArgumentException
		 * 			If xpos isn't a valid x-position the exception is thrown.
		 * 			| ! position.isValidXpos(xpos)
		 */
		public void setXpos(double xpos) {
			position.setXpos(xpos);
		}
		
		
		/**
		 * Sets the y-position of the food.
		 * @param ypos
		 * 			The (new) y-position of the food
		 * @post	the given y-position is the new y-position of the food.
		 * 			| new.getYpos() == ypos
		 * @throws	IllegalArgumentException
		 * 			If ypos isn't a valid y-position the exception is thrown.
		 * 			| ! position.isValidYpos(ypos)
		 */
		public void setYpos(double ypos) {
			position.setYpos(ypos);
		}
		public boolean isActive(){
			if (this.getWorld()==null){
				return false;
			}
			return true;
		}
		public void deleteFood(World world){
			world.deleteFood(this);
		}
		
		private double radius;
		private Position position;
		
	

}
