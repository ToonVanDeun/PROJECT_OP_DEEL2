package worms.model;

import be.kuleuven.cs.som.annotate.Raw;

public class Position {
	//Initialization
	/**
	 * Initializes a position for a given object with given x- and y-positions.
	 * @param xpos	The given x position.
	 * @param ypos	The given y position.
	 * @post The newly initialized position is set the the given position.
	 * 			|new.getXpos() == xpos
	 * 			|new.getYpos() == ypos
	 */
	public Position(double xpos, double ypos) {
		this.setXpos(xpos);
		this.setYpos(ypos);
	}
	/**
	 * Initializes a random adjacent position for a given object.
	 * @param world		The world in which the position is being determined.
	 * @param object	The object the position in being determined for.
	 * @post 			The new position is an adjacent position.
	 * 					|world.isAdjacent(new.getXpos(), new.getYpos, object.getRadius)
	 */
	public Position(World world, Object object) {
		this.setAdjacantPosition(world, object);
	}
	
	//Getters and Setters
	/**
	 * Returns the x-position of the object.
	 */
	public double getXpos() {
		return this.xpos;
	}
	/**
	 * Sets the x-position of the object.
	 * @param xpos
	 * 			The (new) x-position of the object
	 * @post	the given x-position is the new x-position of the object.
	 * 			| new.getXpos() == xpos
	 * @throws	IllegalArgumentException
	 * 			If xpos isn't a valid x-position the exception is thrown.
	 * 			| ! isValidPos(xpos)
	 */
	public void setXpos(double xpos) throws IllegalArgumentException {
		if (! isValidPos(xpos))
			throw new IllegalArgumentException();
		this.xpos = xpos;
	}
	/**
	 * Returns the y-position of the object.
	 */
	public double getYpos() {
		return this.ypos;
	}
	/**
	 * Sets the y-position of the object.
	 * @param ypos
	 * 			The (new) y-position of the object
	 * @post	the given y-position is the new y-position of the object.
	 * 			| new.getYpos() == ypos
	 * @throws	IllegalArgumentException
	 * 			If ypos isn't a valid y position the exception is thrown.
	 * 			| ! isValidPos(ypos)
	 */
	public void setYpos(double ypos) throws IllegalArgumentException {
		if (! isValidPos(ypos))
			throw new IllegalArgumentException();
		this.ypos = ypos;
	}
	/**
	 * Finds a passable, adjacent position in the world and sets the position to the newly found position.
	 * The position in found by randomly selecting a position within the range of the map. 
	 * Checking if that the position is passable. If not, a new random location is chosen.
	 * Thereafter new positions get determined stepwise by moving in a random direction,
	 * until the position is and adjacent position.
	 * @param world	The world in which the position is being determined.
	 * @param object	The object the position in being determined for.
	 * @post The new position is an adjacent position.
	 * 			|world.isAdjacent(new.getXpos(), new.getYpos, object.getRadius)
	 */
	public void setAdjacantPosition(World world, Object object) {
		double radius = object.getRadius();
		double randXpos = radius+ (Math.random()*(world.getWidth()-radius));
		double randYpos = radius+ (Math.random()*(world.getHeight()-radius));
		
		if (world.isImpassable(randXpos, randYpos, radius) ||
				!isValidXPos(randXpos, world) || !isValidYPos(randYpos, world)) {
			setAdjacantPosition(world,object);
		}
		else {
			double randomDirection = Math.random()*(2*Math.PI);
			
			while (this.isValidXPos(randXpos, world, radius) 
					&& this.isValidYPos(randYpos, world, radius) ) {
				if (world.isAdjacent(randXpos, randYpos, radius)) {
					break;
				}
				else {
					randXpos += (Math.cos(randomDirection)*radius*0.1);
					randYpos += (Math.sin(randomDirection)*radius*0.1);
				}
			}
			this.setXpos(randXpos);
			this.setYpos(randYpos);
		}
	}
	public void setNearestAdjacent(World world, double xpos, double ypos, double radius){
		double tempx =xpos;
		double tempy =ypos;
		if (world.isPassable(xpos, ypos, radius)) {
			for (double a=0;a<(world.getWidth()/10); a=a+0.01) {
				if (world.isAdjacent(tempx+a, tempy, radius)){
					this.setXpos(tempx+a);
					this.setYpos(tempy);
					break;
				}
				if (world.isAdjacent(tempx-a, tempy, radius)){
					this.setXpos(tempx-a);
					this.setYpos(tempy);
					break;
				}
				if (world.isAdjacent(tempx, tempy+a, radius)){
					this.setXpos(tempx);
					this.setYpos(tempy+a);
					break;
				}
				if (world.isAdjacent(tempx, tempy-a, radius)){
					this.setXpos(tempx);
					this.setYpos(tempy-a);
					break;
				}
			}
		}
	}
	
	//Checkers
	/**
	 * Checks whether the given position is a valid position.
	 * @param	pos
	 * 			The positions that needs to be checked.
	 * @return 	True if the given position (pos) is a valid position.
	 * 			If the given position isn't a valid position (not a number (NaN),
	 * 			the method returns false.
	 * 			| !double.isNan(pos)
	 */
	@Raw
	public boolean isValidPos(double pos) {
		return (! (Double.isNaN(pos))) ;
	}
	/**
	 * Checks whether the given x-position is a valid x-position.
	 * @param	pos
	 * 			The x-position that needs to be checked.
	 * @return 	True if the given x-position (pos) is a valid x-position.
	 * 			If the given position isn't a valid position (not a number (NaN), 
	 * 			or the x position lies not in the range from the map (0..width),
	 * 			the method returns false.
	 * 			| !(Double.isNaN(pos)) && pos>=0 && pos<=world.getWidth()
	 */
	@Raw
	public boolean isValidXPos(double pos, World world) {
		return !(Double.isNaN(pos)) && pos>=0 && pos<=world.getWidth();
	}
	/**
	 * Checks whether the given y-position is a valid y-position.
	 * @param	pos
	 * 			The y-position that needs to be checked.
	 * @return 	True if the given y-position (pos) is a valid y-position.
	 * 			If the given position isn't a valid position (not a number (NaN), 
	 * 			or the y position lies not in the range from the map (0..height),
	 * 			the method returns false.
	 * 			| !(Double.isNaN(pos)) && pos>=0 && pos<=world.getHeight()
	 */
	@Raw
	public boolean isValidYPos(double pos, World world) {
		return ! (Double.isNaN(pos)) && pos>=0 && pos<=world.getHeight();
	}
	/**
	 * Checks whether the given x-position is a valid x-position.
	 * @param	pos
	 * 			The x-position that needs to be checked.
	 * @return 	True if the given x-position (pos) is a valid y-position.
	 * 			If the given position isn't a valid position (not a number (NaN), 
	 * 			or the x position lies not in the range from the map (0..width) the range taken into account,
	 * 			the method returns false.
	 * 			| !(Double.isNaN(pos)) && (pos)>(0+2*radius) && pos<(world.getWidth()-2*radius)
	 */
	@Raw
	public boolean isValidXPos(double pos, World world, double radius) {
		return !(Double.isNaN(pos)) && (pos)>(0+2*radius) && pos<(world.getWidth()-2*radius);
	}
	/**
	 * Checks whether the given y-position is a valid y-position.
	 * @param	pos
	 * 			The y-position that needs to be checked.
	 * @return 	True if the given y-position (pos) is a valid y-position.
	 * 			If the given position isn't a valid position (not a number (NaN), 
	 * 			or the y position lies not in the range from the map (0..height) the range taken into account,
	 * 			the method returns false.
	 * 			| !(Double.isNaN(pos)) && (pos)>(0+2*radius) && pos<(world.getHeight()-2*radius)
	 */
	@Raw
	public boolean isValidYPos(double pos, World world, double radius) {
		return ! (Double.isNaN(pos)) && (pos)>(0+2*radius) && pos<(world.getHeight()-2*radius);
	}
	
	//Variables
	private double xpos;
	private double ypos;

}
