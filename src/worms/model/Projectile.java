/**
 * 
 */
package worms.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * @author Toon
 *
 */
public class Projectile extends Object{

	public Projectile(World world, double xpos, double ypos) {
		super(world);
		this.position = new Position(xpos,ypos);
		this.setDirection();
		this.setMass();
		this.setActive(true);
	}
	public Projectile(World world){
		super(world);
		this.position = new Position(world, this);
		this.setDirection();
		this.setMass();
		this.setActive(true);
	}
	public void deleteProjectile(World world){
		world.deleteProjectile(this);
	}
	public void setActive(boolean state){
		this.active = state;
	}
	public boolean getActive(){
		return this.active;
	}
	/**
	 * Sets the x-position of the projectile.
	 * @param xpos
	 * 			The (new) x-position of the projectile
	 * @post	the given x-position is the new x-position of the projectile.
	 * 			| new.getXpos() == xpos
	 * @throws	IllegalArgumentException
	 * 			If xpos isn't a valid x-position the exception is thrown.
	 * 			| ! position.isValidPos(xpos)
	 */
	public void setXpos(double xpos){
		this.xpos = xpos+(worm.getRadius()*Math.cos(this.direction));
		position.setXpos(this.xpos);
	}
	/**
	 * Returns the x-position of the projectile.
	 */
	public double getXpos(){
		return position.getXpos();
	}
	/**
	 * Sets the y-position of the projectile.
	 * @param ypos
	 * 			The (new) y-position of the projectile
	 * @post	the given y-position is the new y-position of the projectile.
	 * 			| new.getYpos() == ypos
	 * @throws	IllegalArgumentException
	 * 			If ypos isn't a valid y position the exception is thrown.
	 * 			| ! isValidPos(ypos)
	 */
	public void setYpos(double ypos){
		this.ypos = ypos+(worm.getRadius()*Math.sin(this.direction));
		position.setYpos(this.ypos);
	}
	/**
	 * Returns the y-position of the projectile.
	 */
	public double getYpos(){
		return position.getYpos();
	}
	public void setDirection(){
		this.direction = worm.getDirection();
	}
	public double getDirection(){
		return this.direction;
	}
	public void setMass(){
		if (worm.getSelectedWeapon()=="Rifle"){
			this.mass = 10;
		}
		else {
			this.mass = 300;
		}
	}
	public int getMass(){
		return this.mass;
	}
	public void setRadius() {
		this.radius = Math.pow((3.0/4.0)*(this.getMass()/(density*Math.PI)) ,1.0/3.0);
	}
	public double getRadius() {
		return this.radius;
	}
	public void setForce(){
		if (worm.getSelectedWeapon()=="Rifle"){
			this.force = 1.5;
		}
		else {
			this.force =  2.5+(worm.getPropulsionYield()*0.07);
		}
	}
	public double getForce(){
		return this.force;
	}
	public boolean canJump() {
		World world = this.getWorld();
		return ( world.isPassable(this.getXpos(), this.getYpos(), this.getRadius()));
	}
	/**
	 * Returns the jump velocity of a projectile.
	 * 	this is needed in to calculate the distance over which to projectile can jump.
	 */
	@Basic 
	private double jumpVelocity() {
		double velocity = ((this.force/this.getMass())*0.5);
		System.out.println("naam "+ worm.getSelectedWeapon() +" force " +this.force);
		return velocity;
	}
	/**
	 * Returns the jump distance of a worm.
	 */
	@Basic
	private double jumpXDistance(double timeStep) {
		World world = this.getWorld();
		double xDistance;
		if (world.isAdjacent(this.getXpos(), this.getYpos(), this.getRadius())) {
			 xDistance = (Math.pow(this.jumpVelocity(), 2)*Math.sin(2*this.getDirection()))/G;
		}

		xDistance = this.jumpStep(timeStep)[0];
		System.out.println("xdistance " +xDistance);
		return xDistance;
		
	}
//	private double jumpYDistance(double timeStep) {
//		double yDistance = this.jumpStep(timeStep)[1];
//		System.out.println("yDistance " +yDistance);
//		return yDistance;
//	}
	/**
	 * Returns the worms position during a jump on a given time (after the jump started).
	 * @param timeAfterLaunch
	 * 			The time after the jump started
	 * @throws	IllegalStateException
	 * 			If the worm can't jump the exception is thrown.
	 * 			| ! canJump()
	 */
	@Basic
	public double[] jumpStep(double timeAfterLaunch) throws IllegalStateException {
		double[] step;
        step = new double[2];
        if (! this.canJump())
        	throw new IllegalStateException();
        
        	step[0] = ((this.jumpVelocity()*Math.cos(this.getDirection())*timeAfterLaunch)+this.getXpos());   
        	step[1] = (this.jumpVelocity()*Math.sin(this.getDirection())*timeAfterLaunch - 
        			0.5*G*Math.pow(timeAfterLaunch, 2))+this.getYpos();
        

		return step;
	}
public double jumpTime2(double timeStep) {
		
		World world = this.getWorld();	
		double origXpos = this.getXpos();
		double origYpos = this.getYpos();
		double tempXpos = this.getXpos();
		double tempYpos = this.getYpos();
		double t=0;
		while (world.isPassable(tempXpos, tempYpos, this.getRadius())){
			tempXpos = this.jumpStep(t)[0];
			tempYpos = this.jumpStep(t)[1];
			t += timeStep;
			
			if ((world.isAdjacent(tempXpos, tempYpos, this.getRadius())) &&  
					(Math.sqrt(Math.pow((origXpos-tempXpos), 2)+Math.pow((origYpos-tempYpos), 2))>=this.getRadius() )){
				return t;
			}
			if (isOutOfTheMap(tempXpos,tempYpos)) {
				return t;
			}
		}
		return t;	
	}

public boolean isOutOfTheMap(double xpos, double ypos) {
	World world= this.getWorld();
	return !((xpos<=(world.getWidth()-this.getRadius()))&&((xpos>=this.getRadius()))&&
			((ypos <= world.getHeight()-this.getRadius())) && ((ypos>=this.getRadius()))) ;
}
public void jump2(Double timeStep) {
	if (this.canJump()) {
		World world = this.getWorld();	
		double origXpos = this.getXpos();
		double origYpos = this.getYpos();
		double tempXpos = this.getXpos();
		double tempYpos = this.getYpos();
		double t=0;
		while (world.isPassable(tempXpos, tempYpos, this.getRadius())){
			tempXpos = this.jumpStep(t)[0];
			tempYpos = this.jumpStep(t)[1];
			t += timeStep;
			
			
			if (isOutOfTheMap(tempXpos,tempYpos)) {
				this.deleteProjectile(world);
				this.setActive(false);
				break;
				
				
			}
			if ((world.isAdjacent(tempXpos, tempYpos, this.getRadius())) &&  
					(Math.sqrt(Math.pow((origXpos-tempXpos), 2)+Math.pow((origYpos-tempYpos), 2))>=this.getRadius() )){
				this.setXpos(tempXpos);
				this.setYpos(tempYpos);	
				break;
			}
			
		}
		
		
	}	
}


	
	private double density = 7800;
	private double radius;
	private Worm worm;
	private Position position;
	private double xpos;
	private double ypos;
	private double direction;
	private int mass;
	private boolean active;
	private double force;
	private static final double G = 9.80665;

}
