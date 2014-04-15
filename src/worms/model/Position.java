package worms.model;

import be.kuleuven.cs.som.annotate.Raw;

public class Position {
	public Position(double xpos, double ypos) {
		this.setXpos(xpos);
		this.setYpos(ypos);
	}
	
	public Position(World world, Object object) {
		this.setAdjacantPosition(world, object);
	}
	
	public void setAdjacantPosition(World world, Object object) {
		double radius = object.getRadius();
		double randXpos = radius+ (Math.random()*(world.getWidth()-radius));
		double randYpos = radius+ (Math.random()*(world.getHeight()-radius));
		
		//double toOrFromCenter = (Math.random()*1);
		
		if (world.isImpassable(randXpos, randYpos, radius) ||
				!isValidXPos(randXpos, world) || !isValidYPos(randYpos, world)) {
			setAdjacantPosition(world,object);
		}
		else {
			//double randomDirection = getDirectionFromCenter(world);
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

	/**
	 * public double getDirectionFromCenter(World world) {
		//nog rekening houden met deling door 0
		double centerX = world.getWidth()/2;
		double centerY = world.getHeight()/2;
		double diffX = (this.getXpos()-centerX);
		double diffY = (this.getYpos()-centerY);
		double direction = Math.atan(diffY/diffX);
		if (diffX < 0)
			direction = direction + Math.PI;
		return direction;
		
	}
	*/
	
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
	 * Returns the x-position of the object.
	 */
	public double getXpos() {
		return this.xpos;
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
	 * Returns the y-position of the object.
	 */
	public double getYpos() {
		return this.ypos;
	}
	
	/**
	 * Checks whether the given position is a valid position.
	 * @param	pos
	 * 			The positions that needs to be checked.
	 * @return 	True if the given position (pos) is a valid position.
	 * 			If the given position isn't a valid position (not a number (NaN),
	 * 			the method returns false.
	 */
	@Raw
	public boolean isValidPos(double pos) {
		return ! (Double.isNaN(pos));
	}
	@Raw
	public boolean isValidXPos(double pos, World world) {
		return !(Double.isNaN(pos)) && pos>=0 && pos<=world.getWidth();
	}
	@Raw
	public boolean isValidYPos(double pos, World world) {
		return ! (Double.isNaN(pos)) && pos>=0 && pos<=world.getHeight();
	}
	@Raw
	public boolean isValidXPos(double pos, World world, double radius) {
		return !(Double.isNaN(pos)) && (pos)>(0+2*radius) && pos<(world.getWidth()-2*radius);
	}
	@Raw
	public boolean isValidYPos(double pos, World world, double radius) {
		return ! (Double.isNaN(pos)) && (pos)>(0+2*radius) && pos<(world.getHeight()-2*radius);
	}
		
	private double xpos;
	private double ypos;

}
