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
		double randXpos = 1*radius+ (Math.random()*(world.getWidth()-2*radius));
		double randYpos = 1*radius+ (Math.random()*(world.getHeight()-2*radius));
		
		double toOrFromCenter = (Math.random()*1);
		
		if (world.isImpassable(randXpos, randYpos, radius) ||
				randXpos>=world.getWidth() || randYpos>=world.getHeight()) {
			setAdjacantPosition(world,object);
		}
		if (!world.isImpassable(randXpos, randYpos, radius) && 
				(this.isValidXPos(randYpos, world)
				&& this.isValidYPos(randYpos, world) )) {
			double randomDirection = getDirectionToCenter(world)+Math.PI*toOrFromCenter;
			
			while ((world.isAdjacent(randXpos, randYpos, radius)
					&& randXpos<world.getWidth() 
					&& randYpos<world.getHeight()) ) {
				randXpos = (Math.random()*world.getWidth()) + 
						(Math.cos(randomDirection)*radius*0.1);
				randYpos = (Math.random()*world.getHeight())
						+ (Math.sin(randomDirection)*radius*0.1);
			}
			this.setXpos(randXpos);
			this.setYpos(randYpos);
		}
	}
//		double radius = object.getRadius();
//		double randXpos = 1*radius+ (Math.random()*(world.getWidth()-2*radius));
//		double randYpos = 1*radius+ (Math.random()*(world.getHeight()-2*radius));
//		
//		double toOrFromCenter = (Math.random()*1);
//		
//		if (!world.isImpassable(randXpos, randYpos, radius)) {
//			double randomDirection = getDirectionToCenter(world)+Math.PI*toOrFromCenter;
//					
//			while (	!world.isAdjacent(randXpos, randYpos, radius) 
//					&& (this.isValidXPos(randXpos, world, radius) 
//					&& (this.isValidYPos(randYpos, world, radius)))) {
//				randXpos = (Math.random()*world.getWidth()) 
//						+ (Math.cos(randomDirection)*radius*0.1);
//				randYpos = (Math.random()*world.getHeight())
//						+ (Math.sin(randomDirection)*radius*0.1);
//
//				if (!this.isValidXPos(randXpos, world, radius) ||
//						!this.isValidYPos(randYpos, world, radius)) {	
//					this.setAdjacantPosition(world, object);
//			}
//			this.setXpos(randXpos);
//			this.setYpos(randYpos);
//			}
//		}
//		else {
//			this.setAdjacantPosition(world, object);
//		}
//			
//	}

	
	
		
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
