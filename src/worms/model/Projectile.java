package worms.model;

import java.util.Collection;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of projectiles involving x-position, y-position, a radius, a orientation, a mass, a propulsion yield and a force.
 * Complemented with methodes that interact with the projectile and change certain values.
 * 
 * 
 * @invar	
 * 	
 * @author 	Toon Stuyck
 * 			Toon Van Deun
 * 			Burgerlijk Ingenieur
 * 			https://github.com/ToonVanDeun/PROJECT_OP_DEEL2
 * @version 1.0
 */
public class Projectile extends Object{
	/**
	 * Initialize a projectile with a world, an x-position, an y-position and a worm.
	 * @param world		The world where the projectile is placed in.
	 * @param xpos		The x-position of the new projectile.
	 * @param ypos		The y-position of the new projectile.
	 * @param worm		The worm which fires the projectile.
	 * 
	 * @effect	The projectile is initialized in the given world.
	 * 			| super(world)
	 * @post	The position of the projectile is set to a new position.
	 * 			| new.getXpos() == position.getXpos()
	 * 			| new.getYpos() == position.getYpos()
	 * @post	The direction is set to the same direction as the worm.
	 * 			| new.getDirection() == worm.getDirection()
	 * @post	The mass is set to the given mass of the weapon that is equipped.
	 * 			| if (worm.getSelectedWeapon == "Rifle")
	 * 			| 	then (new.getMass() == 0.01)
	 * 			|	else (new.getMass() == 0.3)
	 * @post	The projectile is set to active.
	 * 			| 	new.getActive() == true
	 * @post	The force is set to 1.5 if the weapon selected is the Rifle else
	 * 			| it is in range of 2.5 and 9.5 depending on the propulsion yield.
	 * 			|	if (worm.getSelectedWeapon == "Rifle")
	 * 			|		then (new.getForce() == 1.5)
	 * 			|		else (new.getForce() == 2.5+(this.getYield()*0.07))
	 * @post	The damage of a projectile is set to 20 if the weapon selected is the Rifle else
	 * 			|	it is set to 80.
	 * 			| if (worm.getSelectedWeapon == "Rifle")
	 * 			|	then (new.getDamage() == 20)
	 * 			|	else (new.getDamage() == 80)
	 */
	public Projectile(World world, double xpos, double ypos, Worm worm) {
		super(world);
		this.position = new Position(xpos,ypos);
		this.setDirection(worm);
		this.setMass(worm);
		this.setActive(true);
		this.setForce(worm);
		this.setDamage(worm);
	}
	public Projectile(World world, Worm worm){
		super(world);
		this.position = new Position(world, this);
		this.setDirection(worm);
		this.setMass(worm);
		this.setActive(true);
		this.setForce(worm);
		this.setDamage(worm);
	}
	/**
	 * Deletes the projectile from the given world.
	 * @param 	world
	 *			The world where the projectile gets removed from.		
	 */
	public void deleteProjectile(World world){
		world.deleteProjectile(this);
	}
	/**
	 * Sets the projectile to a given state.
	 * @param 	state
	 * 			The state where the projectile gets set to (True or False).
	 */
	private void setActive(boolean state){
		this.active = state;
	}
	/**
	 * Returns the state of the projectile.
	 */
	public boolean getActive(){
		return this.active;
	}
	/**
	 * Sets the radius of the worm who shot a projectile.
	 * @param 	worm
	 * 			The worm that shot the projectile.
	 */
	public void setRadiusWorm(Worm worm){
		this.wormRadius =  worm.getRadius();
	}
	/**
	 * Returns the radius of the worm that shot the projectile.
	 */
	public double getRadiusWorm(){
		return this.wormRadius;
	}
	/**
	 * Sets the x-position of the projectile.
	 * @param xpos
	 * 			The (new) x-position of the projectile
	 * @post	the given x-position is the new x-position of the projectile.
	 * 			| new.getXpos() == xpos
	 */
	public void setXpos(double xpos){
		this.xpos = xpos+((this.getRadiusWorm()+this.getRadius())*1.1*Math.cos(this.getDirection()));
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
	 */
	public void setYpos(double ypos){
		this.ypos = ypos+((this.getRadiusWorm()+this.getRadius())*1.1*Math.sin(this.getDirection()));
		position.setYpos(this.ypos);
	}
	/**
	 * Returns the y-position of the projectile.
	 */
	public double getYpos(){
		return position.getYpos();
	}
	/**
	 * Sets the direction of the projectile, which is the same as the direction of the worm shooting the projectile.
	 * @param direction
	 * 			The new direction of the projectile.
	 * @pre		The given direction of projectile must be a valid direction.
	 * 			|isValidDirection(worm.getDirection())
	 * @post	The new direction of the projectile is the same as the direction of the worm.
	 * 			| new.getDirection() == worm.getDirection()
	 */
	private void setDirection(Worm worm){
		assert (isValidDirection(worm.getDirection()));
		this.direction = worm.getDirection();
	}
	/**
	 * Returns the direction of the projectile.
	 * The direction of the projectile is an angle given in radians, 
	 * which indicates where the projectile is facing at. (ex. facing up = PI/2, facing right = 0)
	 */
	public double getDirection(){
		return this.direction;
	}
	/**
	 * Checks whether the given direction is a valid direction.
	 * @param 	direction
	 * 			The direction that needs to be checked.
	 * @return 	True if the given direction (direction) is a valid direction.
	 * 			If the given direction isn't a valid direction (not a number (NaN)),
	 * 			the method returns false.
	 */
	@Raw
	public boolean isValidDirection(double direction){
		return ! (Double.isNaN(direction));
	}
	/**
	 * The method sets the mass of the projectile.
	 * The mass of the rifle's projectile is 0.01 kg.
	 * The mass of the bazooka's projectile is 0.3 kg.
	 * @param worm
	 * 			The worm who has a weapon equipped.
	 * @post	The new mass of the worm is set dependent of the weapon selected by the worm.
	 * 			| if (worm.getSelectedWeapon == "Rifle")
	 * 			| 	then (new.getMass() == 0.01)
	 * 			|	else (new.getMass() == 0.3)
	 * @throws 	IllegalStateException
	 * 			When the selected weapon is nor the Rifle nor the Bazooka the exception will be thrown.
	 * 			| !((worm.getSelectedWeapon() == "Rifle") || (worm.getSelectedWeapon() == "Bazooka"))
	 */
	private void setMass(Worm worm) throws IllegalStateException{
		if (worm.getSelectedWeapon()=="Rifle"){
			this.mass = 0.01;
		}
		else if (worm.getSelectedWeapon()=="Bazooka"){
			this.mass = 0.3;
		} else {
			throw new IllegalStateException();
		}
	}
	/**
	 * Returns the mass of the projectile.
	 */
	public double getMass(){
		return this.mass;
	}
	/**
	 * Checks whether the given mass is a valid mass.
	 * @param 	mass
	 * 			The mass that needs to be checked.
	 * @return 	True if the given mass is a valid mass.
	 * 			If the given mass isn't a valid mass (not a number (NaN)),
	 * 			the method returns false.
	 */
	@Raw
	public boolean isValidMass(double mass){
		return ! (Double.isNaN(mass));
	}
	/**
	 * The method sets the damage a projectile will inflict to a worm.
	 * @param worm
	 * 			The worm who has the weapon equipped.
	 * @post	The damage the projectile will inflict is set dependent of the equipped weapon.
	 * 			| if (worm.getSelectedWeapon == "Rifle")
	 * 			|	then (new.getDamage() == 20)
	 * 			|	else (new.getDamage() == 80)
	 */
	private void setDamage(Worm worm){
		if (worm.getSelectedWeapon() == "Rifle"){
			this.damage = 20;
		} else {
			this.damage = 80;
		}
	}
	/**
	 * Returns the damage a projectile will inflict 
	 */
	public int getDamage(){
		return this.damage;
	}
	/**
	 * The method sets the radius of the projectile if it has a valid mass.
	 * @post 	The new radius of the projectile is set.
	 * 			|new.getRadius() == Math.pow((3.0/4.0)*(this.getMass()/(density*Math.PI)) ,1.0/3.0)
	 * @throws 	IllegalArgumentException
	 * 			When the mass appropriate to the projectile is not a valid mass the exception will be thrown.
	 * 			| ! isValidMass(this.getMass()))
	 */
	public void setRadius() throws IllegalArgumentException{
		if ( ! isValidMass(this.getMass()))
			throw new IllegalArgumentException();
		this.radius = Math.pow((3.0/4.0)*(this.getMass()/(density*Math.PI)) ,1.0/3.0);
	}
	/**
	 * Returns the radius of the projectile.
	 */
	public double getRadius() {
		return this.radius;
	}
	/**
	 * The method sets the propulsion yield of the projectile.
	 * @param worm
	 * 			The worm who has the weapon equipped.
	 * @post	The new yield of the projectile is set.
	 * 			| new.getYield()==worm.getPropulsionYield()
	 */
	public void setYield(Worm worm){
		this.yield = worm.getPropulsionYield();
	}
	/**
	 * Returns the propulsion yield of the projectile.
	 */
	public double getYield(){
		return this.yield;
	}
	/**
	 * The method sets the force that is exerted on the projectile.
	 * @param worm
	 * 			The worm who has the weapon equipped.
	 * @post	The force is set to 1.5 if the weapon selected is the Rifle else
	 * 			 it is in range of 2.5 and 9.5 depending on the propulsion yield.
	 * 			|	if (worm.getSelectedWeapon == "Rifle")
	 * 			|		then (new.getForce() == 1.5)
	 * 			|		else (new.getForce() == 2.5+(this.getYield()*0.07))
	 */
	private void setForce(Worm worm){
		this.setYield(worm);
		if (worm.getSelectedWeapon()=="Rifle"){
			this.force = 1.5;
		}
		else {
			this.force =  2.5+(this.getYield()*0.07);
		}
	}
	/**
	 * Returns the force that is exerted on the projectile.
	 */
	public double getForce(){
		return this.force;
	}
	/**
	 * Checks whether the begin position of the projectile is in passable terrain.
	 */
	public boolean canJump() {
		World world = this.getWorld();
		return ( world.isPassable(this.getXpos(), this.getYpos(), this.getRadius()));
	}
	/**
	 * Returns the jump velocity of a projectile.
	 * 	this is needed to calculate the distance over which to projectile can jump.
	 */
	@Basic 
	private double jumpVelocity() {
		double velocity = ((this.force/this.getMass())*0.5);
		return velocity;
	}
	/**
	 * Returns the projectiles position during a jump on a given time (after the jump started).
	 * @param timeAfterLaunch
	 * 			The time after the jump started
	 * @throws	IllegalStateException
	 * 			If the projectile can't jump the exception is thrown.
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
	/**
	 * Returns the time it takes for the projectile to jump (to his new position).
	 * @throws	IllegalStateException
	 * 			If the projectile can't jump the exception is thrown.
	 * 			| ! canJump()
	 */
	public double jumpTime(double timeStep) throws IllegalStateException{
		
		World world = this.getWorld();	
		double origXpos = this.getXpos();
		double origYpos = this.getYpos();
		double tempXpos = this.getXpos();
		double tempYpos = this.getYpos();
		double t=0;
		if (!this.canJump())
			throw new IllegalStateException();
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
	/**
	 * Checks whether the given x-position and y-position are still in the boundaries of the map.
	 * @param xpos
	 * 			the given x-position that needs to be checked.
	 * @param ypos
	 * 			the given y-position that needs to be checked.
	 */
	public boolean isOutOfTheMap(double xpos, double ypos) {
		World world= this.getWorld();
		return !((xpos<=(world.getWidth()-this.getRadius()))&&((xpos>=this.getRadius()))&&
				((ypos <= world.getHeight()-this.getRadius())) && ((ypos>=this.getRadius()))) ;
	}
	/**
	 * Changes the positions of the projectile as a result of a jump from the current position.
	 * @post 	The worms jumped to the correct position when the direction is in the range (0 - PI)
	 * 			| new.getXpos() == old.getXpos() + this.jumpDistance()
	 * @post 	The worms hasn't jumped when the direction is in the range (PI- 2*PI)
	 * 			| new.getXpos() == old.getXpos()
	 * @post	The worm's actionpoints are reduced to zero.
	 * 			|new.getActionPoints() == 0;
	 * @throws 	IllegalStateException
	 * 			If the projectile can't jump the exception is thrown.
	 * 			|! canJump()
	 */
	public void jump(Double timeStep) throws IllegalStateException{
		if (this.canJump()) {
			World world = this.getWorld();
			double tempXpos = this.getXpos();
			double tempYpos = this.getYpos();
			double t=0;
			while ((world.isPassable(tempXpos, tempYpos, this.getRadius()))){
				
				tempXpos = this.jumpStep(t)[0];
				tempYpos = this.jumpStep(t)[1];
				
	
				
				Collection<Worm> collection = (world.getWorms());
	
			    for (Worm w : collection) {
			    	Worm overlappingWorm = null;
			    	double maxDistance = this.getRadius() + w.getRadius();
			    	
			    	if (!(w==world.getCurrentWorm()) && (Math.sqrt(Math.pow(w.getXpos()-tempXpos, 2)+
			    			Math.pow(w.getYpos()-tempYpos, 2))< maxDistance)) {
			    		System.out.println("ze overlappen");
	
			    		
			    		//als ze overlappen
			    		
			    		overlappingWorm = w;
			    		System.out.println(w.getName());
			    		
			    		while ((this.getActive()==true)){
			    			overlappingWorm.setHitPoints(overlappingWorm.getHitPoints()-this.getDamage());
			    			System.out.println("dammage");
			    			System.out.println("ze overlappen");
							this.deleteProjectile(world);
							this.setActive(false);
							System.out.println("false?" +this.getActive());
			    		}
						
			    	} else {
			    		overlappingWorm = null;
			    		if ((isOutOfTheMap(tempXpos,tempYpos))&& (this.getActive()==true)) {
							System.out.println("if1");
							this.deleteProjectile(world);
							this.setActive(false);
							
							
							
						}
					
						if (((world.isImpassable(tempXpos, tempYpos, this.getRadius()))) && (this.getActive()==true)){
							System.out.println("if2");
							this.deleteProjectile(world);
							this.setActive(false);
							
						}
			    	}
			    	
			    	
			    	
			    }
			    t += timeStep;
			}
		}
		else {
			throw new IllegalStateException();
		}
	}


	private double wormRadius;
	private double radius;
	private int yield;
	private int damage;
	private Position position;
	private double xpos;
	private double ypos;
	private double direction;
	private double mass;
	private boolean active;
	private double force;
	
	
	private static final double G = 9.80665;
	private static final double density = 7800;

}
