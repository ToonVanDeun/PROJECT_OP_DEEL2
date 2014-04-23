
package worms.model;

import be.kuleuven.cs.som.annotate.*;



/**
 * A class of food that can be in a world of class World. 
 * The class inherits from Object and contains methods to change the foods position and delete the food.
 *       
 * @invar	The radius of food is 0.20
 * 			|this.getRadius()==0.20
 * @invar	The position of food is always adjacent to impassable terrain.
 * 			|this.getWorld().isadjacent(this.getXpos(),this.getYpos,this.getRadius)==true
 * 
 * @author 	Toon Stuyck
 * 			Toon Van Deun
 * 			Burgerlijk Ingenieur
 * 			https://github.com/ToonVanDeun/PROJECT_OP_DEEL2
 * @version 2.0
 */
	public class Food extends Object{
		/**
		 * Initializes the food in a given world at a random adjacent location.
		 * @param  	world
		 *         	The world for this new food.
		 * @effect 	This new food is initialized as a new object in a given world.
		 *       	| super(world)
		 * @post 	The food is placed in a position, adjacent to impassable terrain.
		 * 			|new.position.isAdjacent()==true
		 */
		@Raw
		public Food(World world) throws IllegalArgumentException {
			super(world);
			this.setRadius();
			this.position = new Position(world, this);
		}
		/**
		 * Initializes the food in a given world at a given position.
		 * @param 	world
		 * 			The world for this new food.
		 * @param 	xpos
		 * 			The X position of the food.
		 * @param 	ypos
		 * 			The Y position of the food.
		 * @post	The X position is set to the given xpos.
		 * 			|this.position.getXpos()==xpos
		 * @post	The Y position is set to the given ypos.
		 * 			|this.position.getYpos()==ypos	
		 */
		@Raw
		public Food(World world, double xpos, double ypos) {
			super(world);
			this.setRadius();
			this.position = new Position(xpos, ypos);
		}
		/**
		 * The radius of the food is set to 0.20.
		 */
		@Raw @Immutable
		public final void setRadius() {
			this.radius = 0.20;
		}
		/**
		 * Returns the radius of the food.
		 */
		@Basic @Raw @Immutable
		public double getRadius() {
			return this.radius;
		}
		
		//position
		/**
		 * Returns the x-position of the food.
		 */
		@Basic @Raw
		public double getXpos() {
			return position.getXpos();
		}
		/**
		 * Returns the y-position of the food.
		 */
		@Basic @Raw
		public double getYpos() {
			return position.getYpos();
		}
		/**
		 * Sets the x-position of the food.
		 * @param xpos
		 * 			The (new) x-position of the food
		 * @post	the given x-position is the new x-position of the food.
		 * 			| new.getXpos() == xpos
		 */
		@Raw
		public void setXpos(double xpos) {
			position.setXpos(xpos);
		}
		/**
		 * Sets the y-position of the food.
		 * @param ypos
		 * 			The (new) y-position of the food
		 * @post	the given y-position is the new y-position of the food.
		 * 			| new.getYpos() == ypos
		 */
		@Raw
		public void setYpos(double ypos) {
			position.setYpos(ypos);
		}
		/**
		 * Checks whether food is active 
		 * @return	True if food has a world different from null.
		 * 			| !this.getWorld()==null
		 */
		@Raw
		public boolean isActive(){
			if (this.getWorld()==null){
				return false;
			}
			return true;
		}
		/**
		 * Deletes the food
		 * @param 	world
		 * 			The world in which the food needs to be deleted.
		 * @post	The food in no longer an object of the world it originally belonged to.
		 * 			|(new) world.getAllObjects().contains(this) == false
		 */
		@Raw
		public void deleteFood(World world){
			world.deleteFood(this);
		}
		
		//variables
		private double radius;
		/**
		 * Variable referencing the position of this object.
		 */
		private Position position;
		
	

}
