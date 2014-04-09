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
		this.setRandomPosInWorld(world);
		double randomDirection = Math.random()*(2*Math.PI);
		double radius = object.getRadius();
		double oldX = this.getXpos();
		double oldY = this.getYpos();
		while ( (this.isValidXPos(this.getXpos(),world)) && (this.isValidYPos(this.getYpos(),world)) &&
				world.getPassableMap() [(int) this.getXpos()][(int) this.getYpos()])
			
			oldX = this.getXpos();
			oldY = this.getYpos();
						
			this.setXpos(this.getXpos() + (Math.cos(randomDirection)*radius*0.1));
			this.setYpos(this.getYpos() + (Math.sin(randomDirection)*radius*0.1));
			
				
		if ( ! world.getPassableMap() [(int) this.getXpos()][(int) this.getYpos()]) {	
			this.setXpos(oldX);
			this.setYpos(oldY);
		}
		else {
			this.setAdjacantPosition(world, object);	
		}
		
		
		
		
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
	public boolean isValidXPos(double pos, World world) {
		return !(Double.isNaN(pos)) && pos>0 && pos<world.getWidth();
	}
	public boolean isValidYPos(double pos, World world) {
		return ! (Double.isNaN(pos)) && pos>0 && pos<world.getHeight();
	}
	private double xpos;
	private double ypos;

}
