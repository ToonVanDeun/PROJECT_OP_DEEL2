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
		double randXpos = (Math.random()*world.getWidth());
		double randYpos = (Math.random()*world.getHeight());
		
		
		if (world.getPassableMap() [(int) randXpos][(int) randYpos])
			//this.setAdjacantPosition(world, object);
			;
		else {		
			double randomDirection = getDirectionToCenter(world);
			double radius = object.getRadius();
					
			this.setXpos(randXpos);
			this.setYpos(randYpos);
			
			/**while ( (! world.getPassableMap() [(int) this.getXpos()][(int) this.getYpos()]) &&
					(this.isValidXPos(this.getXpos(), world) && this.isValidYPos(this.getYpos(), world)) )
							
				this.setXpos(this.getXpos() + (Math.cos(randomDirection)*radius*0.1));
				this.setYpos(this.getYpos() + (Math.sin(randomDirection)*radius*0.1));
			*/	
					
			if (! this.isValidXPos(this.getXpos(), world) || !this.isValidYPos(this.getYpos(), world)) {	
				//this.setAdjacantPosition(world, object);
			}
		
		}
	}
		
	public double getDirectionToCenter(World world) {
		double centerX = world.getWidth()/2;
		double centerY = world.getHeight()/2;
		double diffX = (centerX - this.getXpos());
		double diffY = (centerY - this.getYpos());
		double direction = Math.atan(diffY/diffX);
		if (diffX < 0)
			direction = direction + Math.PI;
		return direction;
		
	}
		

	private void setRandomPosInWorld(World world) {
		setXpos(Math.random()*world.getWidth());
		setYpos(Math.random()*world.getHeight());
	}

	public void setXpos(double xpos) throws IllegalArgumentException {
		if (! isValidPos(xpos))
			throw new IllegalArgumentException();
		this.xpos = xpos;
	}
	
	public double getXpos() {
		return this.xpos;
	}
	public void setYpos(double ypos) throws IllegalArgumentException {
		if (! isValidPos(ypos))
			throw new IllegalArgumentException();
		this.ypos = ypos;
	}
	public double getYpos() {
		return this.ypos;
	}
	@Raw
	public boolean isValidPos(double pos) {
		return ! (Double.isNaN(pos));
	}
	@Raw
	public boolean isValidXPos(double pos, World world) {
		return !(Double.isNaN(pos)) && pos>0 && pos<world.getWidth();
	}
	@Raw
	public boolean isValidYPos(double pos, World world) {
		return ! (Double.isNaN(pos)) && pos>0 && pos<world.getHeight();
	}
		
	public boolean isAdjacent(World world, double x, double y, double radius) {
		return( world.getPassableMap() [(int) x][(int) y] ) &&
				((!world.getPassableMap() [(int) (x+0.1*radius)][(int) y]) ||
				(!world.getPassableMap() [(int) (x-0.1*radius)][(int) y]) ||
				(!world.getPassableMap() [(int) x][(int) (y+0.1*radius)]) ||
				(!world.getPassableMap() [(int) x][(int) (y-0.1*radius)]));
	}
	
	private double xpos;
	private double ypos;

}
