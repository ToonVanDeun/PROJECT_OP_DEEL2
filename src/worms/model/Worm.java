package worms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.core.IsAnything;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of worms involving x-position, y-position a radius, a direction a mass , actionpoints and a name.
 * Complemented with methodes that interact with the worm and change curtain values.
 * 
 * @invar	The radius must be a valid radius for the worm.
 * 		  	| isValidRadius(this.getRadius())
 * @invar	The actionpoints of a worm must always be equal to or larger than zero.
 * 			|this.getActionPoints() >= 0
 * @invar	The actionpoints of a worm must always be equal to or smaller than the maximum actionpoints.
 * 		  	| this.getActionpoints() =< this.getMaxActionPoints()
 * 	
 * @author 	Toon Stuyck
 * 			Toon Van Deun
 * 			Burgerlijk Ingenieur
 * 			https://github.com/ToonVanDeun/PROJECT_OP
 * @version 1.0
 */
public class Worm extends Object {
	
	
	
	/**
	 * Initialize a worm with a x-and -position (meters), direction (radians), radius (meters) and a name.
	 * @param xpos
	 * 			The X position of the worm.
	 * @param ypos
	 * 			The Y position of the worm.
	 * @param direction
	 * 			The Direction the worm is facing.
	 * @param radius
	 * 			The Radius of the worm.
	 * @param name
	 * 			The name of the worm.
	 * @pre		The positions of the worm must be valid positions.
	 * 			|isValidPos(xpos)
	 * 			|isValidPos(ypos)
	 * @pre		The direction a worm is facing at must be a valid direction.
	 * 			|isValidDirection(direction)
	 * @pre		The radius of a worm must be higher than the lower bound of the radius.
	 * 			|isValidRadius(radius)
	 * @pre		The name of a worm must be a valid name.
	 * 			|isValidName(name)
	 * @post	The x- and y-position are set to the given x- and y-positions.
	 * 			|new.getXpos() == xpos
	 * 			|new.getYpos() == ypos
	 * @post	The direction is set to the given direction.
	 * 			|new.getDirection() == direction
	 * @post	The radius is set to the given radius
	 * 			|new.getRadius() == radius
	 * @post	The mass of a worm is set accordingly to its radius.
	 * 			|new.getMass() == DENSITY*((4.0/3.0)*Math.PI*Math.pow(radius, 3))
	 * @post	The maximum amount of actionpoints is set accordingly to the worm's mass.
	 * 			|new.getMaxActionPoints() == (int) Math.round(this.getMass())
	 * @post	The amount of actionpoints are set to the maximum amount of actionpoints.
	 * 			|new.getActionPoints() == new.getMaxActionPoints()
	 * @post	The name of the worm is set to the given name.
	 * 			|new.getName() == name.
	 * @post	The lower bound of the radius is set to the given lower bound.
	 * 			Or when no lower bound is given it's set to 0.25 as it is in this case.
	 * 			|new.getRadiusLowerBound() ==  0.25
	 */
	public Worm(World world, double xpos, double ypos, double direction, double radius, String name){
		super(world);
		this.position = new Position(xpos, ypos);
		this.setDirection(direction);
		this.setRadius(radius);
		this.setName(name);
		this.setActionPoints(maxActionPoints);
		this.setHitPoints(maxHitPoints);
		//this.setIsAlive();
	}

	
	public Worm(World world){
		super(world);
		//this.setWorldTo(world);
		Random perimeter = world.getPerimeter();
		this.setRadius(radiusLowerBound);
		this.position = new Position(world, this);
		this.setDirection(perimeter.nextDouble()*2*Math.PI);
		this.setName("Worm");
		this.setActionPoints(this.getMaxActionPoints());
		this.setHitPoints(this.getMaxHitPoints());
		this.setTeamRandom();
		//this.setIsAlive();
		
	}
	
	public void newRound(){
		if (this.alive == true)
			this.renewActionPoints();
			this.heal(health);
	}
	// team
	
	public String getTeamName(){
		return this.teamName;
	}
	public void setTeamName(Team team){
		teamName = team.getName();
	}
	
	/**
	 * Return the team of this worm.
	 *   A null reference is returned if this object is not in a team.
	 */
	@Basic
	@Raw
	public Team getTeam() {
		return this.team;
	}

	/**
	 * Check whether this worm can have the given team
	 * as its team.
	 *
	 * @param  team
	 *         The team to check.
	 * @return True, if the worm is not yet in a team.
	 * 			| ...
	 */
	@Raw
	public boolean canHaveAsTeam(Team team) {
		if (team.getAllWorms().contains(this))
			return false;
		return true;
		
	}
	/**
	 * Check whether this worm is in a team.
	 *
	 * @return True if and only if the team of this worm is effective.
	 *       | result == (getTeam() != null)
	 */
	@Raw
	public boolean hasTeam() {
		return getTeam() != null;
	}

	/**
	 * Set the team of this worm to the given team.
	 *
	 * @param  team
	 *         The new team for this worm.
	 * @post   The team of this worm is the same as the given team.
	 *       | new.getTeam() == team
	 * @post   The number of worms of the given team
	 *         is incremented by 1.
	 *       | (new team).getNbWorms() == team.getNbWorms() + 1
	 * @post   The given team has this worm as its new last
	 *         worm.
	 *       | (new team).getWormAt(getNbWorms()+1) == this
	 * @throws IllegalArgumentException
	 *         The given team is not effective or it cannot have this worm
	 *         as its new last worm.
	 *       | (team == null) ||
	 *       |   (! team.canHaveAsWormAt(this,team.getNbWorms()+1))
	 * @throws IllegalStateException
	 *         This worm already belongs to a team.
	 *       | hasTeam()
	 */	
	public void setTeamTo(Team team)
			throws IllegalArgumentException, IllegalStateException {
		if ((team == null))
				//|| (!team.canHaveAsWormAt(this, team.getNbWorms()+1))) 
			throw new IllegalArgumentException();
		if (this.hasTeam())
			throw new IllegalStateException("Already belongs to a world!");
		setTeam(team);
		team.addAsWorm(this);
	}

	/**
	 * Set the team of this worm to the given team.
	 *
	 * @param  team
	 *         The new team for this worm.
	 * @pre    This worm can have the given team as its team.
	 *       | canHaveAsTeam(team)
	 * @post   The team of this worm is the same as the given team.
	 *       | new.getTeam() == team
	 */
	@Raw
	private void setTeam(@Raw Team team) {
		assert canHaveAsTeam(team);
		this.team = team;
	}
	public void setTeamRandom(){
		World world = this.getWorld();
		ArrayList<Team> teams = (ArrayList<Team>) world.getTeams();
		if (!(teams.size()==0)) {
			int i = teams.size();
			int randomIndex = (int) (Math.random()*i);
			this.setTeamTo(teams.get(randomIndex));
		} 
	
	}
	/**
	 * Unset the team, if any, from this worm.
	 *
	 * @post   This worm no longer belongs to a team.
	 *       | ! new.hasTeam()
	 * @post   The former team of this worm, if any, no longer
	 *         has this worm as one of its worms.
	 *       |    (getTeam() == null)
	 *       | || (! (new getTeam()).hasAsWorm(worm))
	 * @post   All worms registered beyond the position at which
	 *         this worm was registered shift one position to the left.
	 *       | (getTeam() == null) ||
	 *       | (for each index in
	 *       |        getTeam().getIndexOfWorm(worm)+1..getTeam().getNbWorms():
	 *       |    (new getTeam()).getWormAt(index-1) == getTeam().getWormAt(index) ) 
	 */
	public void unsetTeam() {
		if (hasTeam()) {
			Team formerTeam = this.getTeam();
			setTeam(null);
			formerTeam.removeAsWorm(this);
		}
	}
	
	
	//position (defensive)
	/**
	 * Returns the x-position of the worm.
	 */
	@Basic @Raw
	public double getXpos(){
		return position.getXpos();
	}
	/**
	 * Sets the x-position of the worm.
	 * @param xpos
	 * 			The (new) x-position of the worm
	 * @post	the given x-position is the new x-position of the worm.
	 * 			| new.getXpos() == xpos
	 * @throws	IllegalArgumentException
	 * 			If xpos isn't a valid x-position the exception is thrown.
	 * 			| ! isValidPos(xpos)
	 */
	@Raw
	private void setXpos(double xpos) {
		position.setXpos(xpos);
	}
	/**
	 * Returns the y-position of the worm.
	 */
	@Basic @Raw
	public double getYpos(){
		return position.getYpos();
	}
	/**
	 * Sets the y-position of the worm.
	 * @param ypos
	 * 			The (new) y-position of the worm
	 * @post	the given y-position is the new y-position of the worm.
	 * 			| new.getYpos() == ypos
	 * @throws	IllegalArgumentException
	 * 			If ypos isn't a valid y position the exception is thrown.
	 * 			| ! isValidPos(ypos)
	 */
	@Raw
	private void setYpos(double ypos) {
		position.setYpos(ypos);
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
	
	public boolean isOutOfTheMap(double xpos, double ypos) {
		World world= this.getWorld();
		return !((xpos<=(world.getWidth()-this.getRadius()))&&((xpos>=this.getRadius()))&&
				((ypos <= world.getHeight()-this.getRadius())) && ((ypos>=this.getRadius()))) ;
	}
	
	public boolean canFall() {
		World world = this.getWorld();
		return( world.isPassable(this.getXpos(), this.getYpos(), this.getRadius()) &&
				!world.isAdjacent(this.getXpos(), this.getYpos(), this.getRadius()));
	}
	
	public void fall() throws IllegalArgumentException{
		World world = this.getWorld();
		double distance = 0;
		boolean trigger = false;
		if ( canFall()) {
			while (world.isPassable(this.getXpos(), this.getYpos(), this.getRadius())){
				this.setYpos(this.getYpos()-this.getRadius());
				distance += this.getRadius();
				if (this.getYpos()<0) {
					this.setYpos(0+this.getRadius()*1.1);
					trigger = true;
					//this.alive = false;
					//this.deleteWorm(world);
					break;
				}
			}
			
			if ((this.getYpos()>=0) && !trigger){
				while (!world.isAdjacent(this.getXpos(), this.getYpos(), this.getRadius())) {
					this.setYpos(this.getYpos()+0.1*this.getRadius());
					distance -= 0.1*this.getRadius();
				}
				
			}
			this.setHitPoints(this.getHitPoints()-(3*(int) Math.floor(distance)));
			this.setIsAlive();
			this.deleteWorm(world);
		}	
	}
	
	//direction (nominal)
	/**
	 * Returns the current direction of the worm.
	 * 	The direction of the worm is an angle given in radians, 
	 * 	which indicates where the worm is facing at. (ex. facing up = PI/2, facing right = 0)
	 */
	@Basic @Raw
	public double getDirection(){
		return this.direction;
	}
	/**
	 * Sets the direction of the worm between 0 and 2*Pi.
	 * @param direction
	 * 			The new direction of the worm.
	 * @pre		The given direction of worm must be a valid direction.
	 * 			|isValidDirection(direction)
	 * @post	The new direction of the worm is the given direction modulo (2*Pi).
	 * 			And is always greater than zero and smaller than 2*Pi
	 * 			| if direction%(2*Pi) < 0  
	 * 			| 	new.getDirection() == direction % (2*Pi) + 2*Pi
	 * 			| else 
	 * 			|	new.getDirection() == direction % (2*Pi)
	 */
	@Raw
	private void setDirection(double direction){
		assert (isValidDirection(direction));
		double newDirection = (direction % (2*Math.PI));
		if (newDirection < 0) 
			this.direction = (newDirection+(2*Math.PI));
		else
			this.direction = newDirection;
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
	
	//radius (defensive)
	/**
	 * Returns the current radius (meters) of the worm.
	 */
	@Basic
	public double getRadius(){
		return this.radius;
	}	
	/**
	 * The method sets the radius of the worm to the given radius if it's a valid value.
	 * @param radius
	 * 			the new radius of the worm, in meters.
	 * @post 	The new radius of the worm is set to the given radius.
	 * 			|new.getRadius() == radius
	 * @post	The mass of the worm changes accordingly
	 * 			|new.getMass() == DENSITY*((4.0/3.0)*Math.PI*Math.pow(radius, 3))
	 * @throws	IllegalArgumentException
	 * 			When the given radius is not a valid radius the exception will be thrown.
	 * 			| ! isValidRadius(radius)) 
	 */
	public void setRadius(double radius) throws IllegalArgumentException{
		if ( ! isValidRadius(radius))
			throw new IllegalArgumentException();
		
		this.radius = radius;
		this.setMass(this.radius);
		
	}
	/**
	 * Checks whether a given radius is a valid radius.
	 * @param radius
	 * 			The radius that's being checked.
	 * @return	True if and only if the given radius is larger than or equal to the minimal allowed radius.
	 * 			| result == (radius >= this.getRadiusLowerBound())
	 */
	public boolean isValidRadius(double radius){
		return radius >= this.getRadiusLowerBound();
	}
	/**
	 * Returns the lower bound of the radius (meters)
	 * 	the lower bound of the radius is the minimal allowed radius of the worm.
	 */
	@Basic 
	public double getRadiusLowerBound() {
		return Worm.radiusLowerBound;
	}
	/**
	 * Sets the minimal allowed radius to lower bound;
	 * @param lowerbound
	 * 			The minimal allowed radius of the worm.
	 * 			Originally initialized at 0.25m.
	 */
	public static void setRadiusLowerBound(double lowerbound) {
		Worm.radiusLowerBound = lowerbound;
	}
	
	//mass (defensive)
	/**
	 * Returns the mass of the worm.
	 */
	@Basic @Raw
	public double getMass(){
		return this.mass;
	}
	/**
	 * The method sets the mass of the worm.
	 * A worms mass is equal to DENSITY*(4/3)*PI*radius^3, with DENSITY = 1062.
	 * @param radius
	 * 			The radius to which the mass must be set accordingly.
	 * @post 	The new mass of the worm is set correctly.
	 * 			|new.getMass() == DENSITY*(4/3)*PI*radius^3
	 * @post 	A new maximum for actionpoints is set.
	 * 			| new.getMaxActionPoints() == new.getMass();
	 * @post	The number of actionpoints change accordingly. (AP can't be higher than MaxAP)
	 * 			| new.getMaxActionPoints() >= new.getActionPoints()
	 * @throws	IllegalArgumentException
	 * 			When the given radius is not a valid radius the exception will be thrown.
	 * 			| ! isValidRadius(radius))
	 */
	private void setMass(double radius) throws IllegalArgumentException{
		if (! isValidRadius(radius))
			throw new IllegalArgumentException();
		this.mass = DENSITY*((4.0/3.0)*Math.PI*Math.pow(radius, 3));
		this.setMaxActionPoints();
		this.setMaxHitPoints();
		this.setActionPoints(this.getActionPoints());
	}

	//name (defensive)
	/**
	 * Returns the name of the worm.
	 */
	@Basic @Raw
	public String getName(){
		return this.name;
	}
	/**
	 * Sets the name of the worm to a given name if the given name is a valid name.
	 * @param name
	 * 			The new name of the worm.
	 * @post	The name of the worm is set to the new name.
	 * 			|new.getName() == name
	 * @throws 	IllegalArgumentException
	 * 			When the name is not a valid name the exception is thrown.
	 * 			| ! isValidName(name)
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException{
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}
	/**
	 * Checks whether a given name is a valid name.
	 * @param name
	 * @post	Returns true if the given name is a valid name
	 * 			(if it starts with a capital and exists of at least 2 letters and no numbers
	 * 			also single and double quotes are allowed.)
	 * 			If the give name is not a valid name the method returns false.
	 * 			| result == match "[A-Z]{1}[a-zA-Z0-9 " ']{1,}"
	 */
	@Raw
	public static boolean isValidName(String name){
	    String regex = "^[A-Z]{1}[a-zA-Z0-9 \"\']{1,}$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(name);
	    return matcher.find();
	}
	
	//actionpoints (total)
	/**
	 * Return the maximal amount of action points for this worm.
	 */
	@Basic
	public int getMaxActionPoints(){
		return this.maxActionPoints;
	}
	/**
	 * Set the maximal amount of action points of this worm.
	 * @param mass
	 * 			The action points change along with the mass.
	 * 
	 * @post	The amount of action points must be equal to the mass of the worm rounded
	 * 			to the nearest integer.
	 * 			| this.maxActionPoints = (int) Math.round(mass)
	 * 
	 * @post	If the mass of a worm changes, the maximal must be adjusted accordingly.
	 * 			| new.getMass() = mass
	 * 			| this.maxActionPoints == (int) Math.round(new.getMass()) == int Math.round(mass)
	 * @effect	The maximal amount of action points has been set.
	 */
	private void setMaxActionPoints(){
		
		if (this.getMass() < Integer.MAX_VALUE)
			this.maxActionPoints = (int) Math.round(this.getMass());
		else 
			this.maxActionPoints =  Integer.MAX_VALUE;
	}
	/**
	 * Return the current amount of action points for this worm.
	 */
	@Basic 
	public int getActionPoints(){
		return this.actionPoints;
	}
	/**
	 * Set a new amount of action points for this worm.
	 * @param actionPoints
	 * 			The new amount of action points. 
	 * @post	The value of a worm's action points must always be 
	 * 			less then or equal to the maximum value. 
	 * 			|new.getActionPoint() <= new.getMaxActionPoints()
	 * @post	The value of a worm's action points must never be less then zero.
	 * 			|new.getActionPoint() >= 0
	 */
	private void setActionPoints(int actionPoints){
		if (actionPoints >= (this.getMaxActionPoints()))
			this.actionPoints = this.getMaxActionPoints();
		else if (actionPoints <0)
			this.actionPoints = 0;
		else 
			this.actionPoints = actionPoints;
	}
	public void renewActionPoints(){
		this.setActionPoints(this.getMaxActionPoints());
	}
	//hitpoints (total)
	/**
	 * Return the maximal amount of hit points for this worm.
	 */
	@Basic
	public int getMaxHitPoints(){
		return this.maxHitPoints;
	}
	/**
	 * Set the maximal amount of hit points of this worm.
	 * @param mass
	 * 			The hit points change along with the mass.
	 * 
	 * @post	The amount of hit points must be equal to the mass of the worm rounded
	 * 			to the nearest integer.
	 * 			| this.maxHitPoints = (int) Math.round(mass)
	 * 
	 * @post	If the mass of a worm changes, the maximal must be adjusted accordingly.
	 * 			| new.getMass() = mass
	 * 			| this.maxHitPoints == (int) Math.round(new.getMass()) == int Math.round(mass)
	 * @effect	The maximal amount of hit points has been set.
	 */
	private void setMaxHitPoints(){
		
		if (this.getMass() < Integer.MAX_VALUE)
			this.maxHitPoints = (int) Math.round(this.getMass());
		else 
			this.maxHitPoints =  Integer.MAX_VALUE;
	}
	/**
	 * Return the current amount of hit points for this worm.
	 */
	@Basic 
	public int getHitPoints(){
		return this.hitPoints;
	}
	/**
	 * Set a new amount of hit points for this worm.
	 * @param 	hitPoints
	 * 			The new amount of hit points. 
	 * @post	The value of a worm's hit points must always be 
	 * 			less then or equal to the maximum value. 
	 * 			|new.getHitPoint() <= new.getMaxHitPoints()
	 * @post	The value of a worm's hit points must never be less then zero.
	 * 			|new.getHitPoint() >= 0
	 */
	private void setHitPoints(int hitPoints){
		if (hitPoints >= (this.getMaxHitPoints())) {
			this.hitPoints = this.getMaxHitPoints();
		} else if (hitPoints <0) {
				this.hitPoints = 0;
		} else if (hitPoints < this.getMaxHitPoints()) {
				this.hitPoints = hitPoints;
		} 
		this.setIsAlive();
	}
	public void setIsAlive(){
		if(this.getHitPoints()==0) {
			this.alive = false;
		}
		else if(this.getHitPoints() > 0){
			this.alive = true;
		}
	}

	public boolean getIsAlive(){
		return this.alive;
	}
	public void killWorm() {
		this.setHitPoints(0);
		this.setIsAlive();
		this.deleteWorm(this.getWorld());
	}
	public void deleteWorm(World world){
		if (this.getIsAlive() == false)
			world.deleteWorm();
	}
	
	public void heal(int amount){
		if (this.alive)
			this.setHitPoints(this.getHitPoints()+amount);
	}
	//move (defensive)
	/**
	 * The method makes the worm move a given number of steps in the direction the worm is currently facing.
	 * @param steps
	 * 			The number of steps the worm is moving
	 * @post	The worm has moved the according number of steps in the current direction.
	 * 			|new.getXpos() == old.getXpos() + steps*cos(direction)*radius
	 * 			|new.getYpos() == old.getYpos() + steps*sin(direction)*radius
	 * @post	The worms actionpoints are correctly reduced.
	 * 			|new.getActionPoints == old.getActionPoints() - old.computeCostStep(steps)
	 * @throws	IllegalArgumentException
	 * 			If the worm can't move the amount of steps because he has insufficient actionpoints
	 * 			the exception is thrown.
	 * 			| ! isValidStep(steps)
	 */
	public void move() throws IllegalArgumentException {
		if ( ! isValidStep())
			throw new IllegalArgumentException();
		this.setXpos(this.getXpos() + (Math.cos(this.getDirection())*this.getRadius()));
		this.setYpos(this.getYpos() + (Math.sin(this.getDirection())*this.getRadius()));
		this.setActionPoints(this.getActionPoints()-this.computeCostStep(1));
	}
	
	public void move2() throws IllegalArgumentException {
		if ( ! isValidStep())
			throw new IllegalArgumentException();
		if (canMove2()) { 
			World world = this.getWorld(); //wereld waarin de worm zich bevind.
			double x = this.getXpos();
			double y = this.getYpos();
			double prevx = x;
			double prevy = y;
			double x2 = x;
			double y2 = y;
			double x2Max = x2;
			double y2Max= y2;
			double c=-0.7875;
			double direction = this.getDirection() + c;
			
			double maxD = 0;
			double minS = this.getDirection();
		
			// geval 1: na gaan of in direction+-45° er een gischike volgende positie is
			//			en zo ja, ernaar verplaatsen.
			for (double a = 0.1;a<=this.getRadius();a=a+(0.01*a)) {
				x2 = x+Math.cos(direction)*a;
				y2 = y+Math.sin(direction)*a;
				if (world.isAdjacent(x2, y2, this.getRadius()) &&
						world.isPassable(x2, y2, this.getRadius())) {
					double d = Math.sqrt(Math.pow((x-x2),2)+Math.pow((y-y2),2));
					double s = Math.atan((x-x2)/(y-y2));
					if ((d>=maxD) && (s<minS)) {
						minS=s;
						maxD=d;
						x2Max = x2;
						y2Max= y2;
					}
				}
				direction = direction +0.0175;
			}
			this.setXpos(x2Max);
			this.setYpos(y2Max);
			this.setActionPoints(this.getActionPoints()-this.computeCost2(prevx, prevy));
			
			// geval2: Er werd in direction+-45° geen geschikte plaats gevonden
			//			nagaan of er in direction naar een passable locatie kan verplaatst worden,
			//			daarnaar verplaatsen en dan vallen (fall).
			if ((x2Max == x) && (y2Max == y)) {
				double pasXpos = x;
				double pasYpos = y;
				
				pasXpos = (x + (Math.cos(this.getDirection())*this.getRadius()));
				pasYpos = (y + (Math.sin(this.getDirection())*this.getRadius()));
				if (!world.isAdjacent(pasXpos, pasYpos, this.getRadius()) && world.isPassable(pasXpos, pasYpos, this.getRadius())) {
					this.setXpos(pasXpos);
					this.setYpos(pasYpos);
					this.setActionPoints(this.getActionPoints()-this.computeCost2(prevx, prevy));
					this.fall();
				}
				
			} 
			this.consumeFood();
			
		}
	}
	
	/**
	 * 
	 * @return Returns true if the worm is positioned in passable terrain 
	 * 			and adjacent to impassable terrain.
	 */
	public boolean canMove2() {
		World world = this.getWorld();
		return world.isAdjacent(this.getXpos(), this.getYpos(), this.getRadius());
	}
	
	// move ~ actionpoints (total)
	/**
	 * Returns the cost in actionpoints for a given number of steps in the current direction.
	 * @param steps
	 * 			the number of steps the worm is going to move.
	 * @return	The cost of steps (integer) in the current direction, rounded up to the next integer.
	 * 			|(steps*(int) Math.round((Math.abs(Math.cos(this.getDirection()))
				|+Math.abs((4*Math.sin(this.getDirection()))))))		
	 */
	@Basic @Raw
	private int computeCostStep(int steps){
		return Math.abs((int) Math.round((steps)*(Math.abs(Math.cos(this.getDirection()))
				+Math.abs((4.0*Math.sin(this.getDirection()))))));
	}
	
	private int computeCost2(double prevXpos, double prevYpos){
		return (int) (Math.round(Math.abs(this.getXpos()-prevXpos))
				+Math.round(4*Math.abs(this.getYpos()-prevYpos)));
	}
	/**
	 * Checks whether a given step in the current direction is a valid step.
	 * This means that there are still enough actionpoints to complete the step.
	 * @param steps
	 * 			number of steps in the current direction.	
	 * @return 	True if there are still enough actionpoints for the wished step.
	 * 			and return False if there aren't enough actionpoints for the wished step.
	 * 			| this.getActionPoints() >= this.computeCostStep(steps)
	 */
	public boolean isValidStep(){
		return this.getActionPoints() >= this.computeCostStep(1);
	}
	
	//turn (nominal)
	/**
	 * Returns the cost in actionpoints for a turn of a given angle.
	 * @param angle
	 * 			the angle over which the worm would like to turn in radians. 
	 * @return 	the cost in actionpoints for a turn of a given angle rounded up to the nearest integer.
	 * 			|(int) Math.abs(Math.round(((60*angle)/(2*Math.PI))))
	 */
	@Basic @Raw
	private int computeCostTurn(double angle){
		return (int) Math.abs(Math.round(((60*angle)/(2*Math.PI))));
	}
	/**
	 * Checks whether turning over a given angle is valid.
	 * This means that there must be enough actionpoints to complete the turn. 
	 * @param angle
	 * 			the angle over which the worm would like to turn in radians.
	 * @return 	True if there are still enough actionpoints for the wished turn.
	 * 			and return False if there aren't enough actionpoints for the wished turn.
	 * 			| this.getActionPoints() >= this.computeCostTurn(angle) 
	 */
	public boolean isValidTurn(double angle){
		return this.getActionPoints() >= this.computeCostTurn(angle);
	}
	/**
	 * Makes the worms turn over a given angle.
	 * @param angle
	 * @pre		There must be enough action point to complete the turn.
	 * 			| this.isvalidTurn(angle)
	 * @post	The worms must have turned over the given angle.
	 * 			|new.getDirection() == old.getDirection() + angle
	 * @post	The worm's actionpoints must be decreased accordingly.
	 * 			|new.getActionpoints() == old.getActionpoints() - old.computeCostTurn(angle)
	 */
	public void turn(double angle){
		assert this.isValidTurn(angle);
		this.setDirection(this.getDirection()+ angle);
		this.setActionPoints(this.getActionPoints()-this.computeCostTurn(angle));
	}
	
	//jump (defensive)
	/**
	 * Changes the positions of the worm as a result of a jump from the current position.
	 * @post 	The worms jumped to the correct position when the direction is in the range (0 - PI)
	 * 			| new.getXpos() == old.getXpos() + this.jumpDistance()
	 * @post 	The worms hasn't jumped when the direction is in the range (PI- 2*PI)
	 * 			| new.getXpos() == old.getXpos()
	 * @post	The worm's actionpoints are reduced to zero.
	 * 			|new.getActionPoints() == 0;
	 * @throws 	IllegalStateException
	 * 			When the worm has no action point left to jump the exception is thrown.
	 * 			|! canJump()
	 */
	public void jump(double timeStep) throws IllegalStateException {
		World world = this.getWorld();
		System.out.println("jump xpos voor " +this.getXpos());
		System.out.println("jump ypos voor " +this.getYpos());
		if (! canJump()) {
			throw new IllegalStateException();
		}
		else {
			System.out.println("jump if");
			while(world.isPassable(this.jumpStep(timeStep)[0], this.jumpStep(timeStep)[1], this.getRadius())) {
				//System.out.println("jump if2");
				this.setXpos(this.jumpStep(timeStep)[0]);
				this.setYpos(this.jumpStep(timeStep)[1]);
				this.setActionPoints(0);
				//System.out.println("jump xpos na " +this.getXpos());
				//System.out.println("jump ypos na " +this.getYpos());
			}
		}
		System.out.println("jump xpos2 " +this.getXpos());
		System.out.println("jump ypos2 " +this.getYpos());
		//this.setXpos(this.jumpStep(this.jumpTime())[0]);
		//this.setYpos(this.jumpStep(this.jumpTime())[1]);
		//this.setActionPoints(0);
		//this.fall();
	}
	
	public void jump2(Double timeStep) {
		if (this.canJump2()) {
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
					this.killWorm();
					break;
					
					
				}
				if ((world.isAdjacent(tempXpos, tempYpos, this.getRadius())) &&  
						(Math.sqrt(Math.pow((origXpos-tempXpos), 2)+Math.pow((origYpos-tempYpos), 2))>=this.getRadius() )){
					this.setXpos(tempXpos);
					this.setYpos(tempYpos);
					this.consumeFood();
					this.setActionPoints(0);
					break;
				}
				
			}
			
			
		}	
	}
	
	//~jump ~actionpoints (total)
	/**
	 * Checks whether the worms still has actionpoints and is facing the right direction so he can jump.
	 */
	public boolean canJump() {
		World world = this.getWorld();
		return ((this.getActionPoints() > 0) && ((this.getDirection()<=Math.PI)) &&
				world.isAdjacent(this.getXpos(), this.getYpos(), this.getRadius()));
	}
	/**
	 * Checks whether the worms still has actionpoints is placed in passable terrain.
	 */
	public boolean canJump2() {
		World world = this.getWorld();
		return ((this.getActionPoints() > 0) && world.isPassable(this.getXpos(), this.getYpos(), this.getRadius()));
	}
	//~jump (extra methods used for calculations needed by the method jump.)
	/**
	 * Returns the jump velocity of a worm.
	 * 	this is needed in to calculate the distance over which to worm can jump.
	 */
	@Basic 
	private double jumpVelocity() {
		double force = (5*this.getActionPoints())+(this.getMass()*G);
		double velocity = ((force/this.getMass())*0.5);
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
//		double distance = (Math.pow(this.jumpVelocity(), 2)*Math.sin(2*this.getDirection()))/G;
//		return distance;
		xDistance = this.jumpStep(timeStep)[0];
		System.out.println("xdistance " +xDistance);
		return xDistance;
		
	}
	private double jumpYDistance(double timeStep) {
		double yDistance = this.jumpStep(timeStep)[1];
		System.out.println("yDistance " +yDistance);
		return yDistance;
	}
	/**
	 * Returns the time it takes to worm to jump (to his new position).
	 * @throws	IllegalStateException
	 * 			If the worm can't jump the exception is thrown.
	 * 			| ! canJump()
	 */
	@Basic
	public double jumpTime(double timeStep) throws IllegalStateException{
		double time = 0;
		if (!this.canJump2())
			throw new IllegalStateException();
		if (this.getDirection() == (Math.PI/2))
			time = 0;
		else
			time = Math.sqrt(Math.pow(this.jumpXDistance(timeStep),2)+Math.pow(this.jumpYDistance(timeStep),2))
			/(this.jumpVelocity()*Math.abs(Math.cos(this.getDirection())));
		return time;
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
        if (! this.canJump2())
        	throw new IllegalStateException();
        
        	step[0] = ((this.jumpVelocity()*Math.cos(this.getDirection())*timeAfterLaunch)+this.getXpos());   
        	step[1] = (this.jumpVelocity()*Math.sin(this.getDirection())*timeAfterLaunch - 
        			0.5*G*Math.pow(timeAfterLaunch, 2))+this.getYpos();
        

		return step;
	}
	
	//Eating food
	
	public void consumeFood() {
		double xpos = this.getXpos(); // om de een of andere vreemde reden, fallt de worm als je zijn radius aanpast...
		double ypos = this.getYpos();
		World world = this.getWorld();
		Food food = this.overlappingFood();
//		System.out.println("width " +world.getWidth());
//		System.out.println("height " +world.getHeight());
//		System.out.println("xpos1 " +this.getXpos());
//		System.out.println("ypos1 " +this.getYpos());
//		System.out.println("straal0 " +this.getRadius());
//		System.out.println(world.isAdjacent(this.getXpos(), this.getYpos(), this.getRadius()));
		if (!(food==null)) {
			this.setRadius(this.getRadius()*1.1);
//			System.out.println("xpos " +this.getXpos());
//			System.out.println("ypos " +this.getYpos());
//			System.out.println("straal " +this.getRadius());
//			System.out.println(world.isAdjacent(this.getXpos(), this.getYpos(), this.getRadius()));
			this.setXpos(xpos);
			this.setYpos(ypos);
			//this.setName("Kwetzalkowatel");
			food.unsetWorld();	
		}
	}
	
	public Food overlappingFood() {
		World world = this.getWorld();
		double maxDistance = this.getRadius() + 0.2;
		Food overlappingFood = null;
//		System.out.println("maxDisctance" + maxDistance);
//		System.out.println("Wx " + this.getXpos());
//		System.out.println("Wy " + this.getYpos());
		
		Collection<Food> collection = (world.getFood());

	    for (Food f : collection) {
//	    	System.out.println(f.getXpos());
//	    	System.out.println(f.getYpos());
	    	if (Math.sqrt(Math.pow(f.getXpos()-this.getXpos(), 2)+
	    			Math.pow(f.getYpos()-this.getYpos(), 2))< maxDistance) {
	    		//System.out.println("ze overlappen");
	    		
	    		//als ze overlappen
	    		
	    		overlappingFood = f;
	    		break;
	    	} else {
	    		//System.out.println("nope");
	    		overlappingFood = null;
	    	}
	    }
	    return overlappingFood;
	}
	
	// shoot
	
	public boolean isValidPropulsion(int propulsion) {
		return (propulsion >= 0 && propulsion <= 100);
	}
	
	public void setPropulsionYield(int propulsion) throws IllegalArgumentException {
		if (isValidPropulsion(propulsion)) {
			this.propulsion = propulsion;
		} else 
			throw new IllegalArgumentException();
	}
	public int getPropulsionYield(){
		return this.propulsion;
	}
	
	
	public void Rifle() {
		this.weapon1 = new Rifle();
		System.out.println("state rifle "+weapon1.getState());
	}
	public void Bazooka(){
		this.weapon = new Bazooka();
		System.out.println("state bazooka "+weapon.getState());
	}
	
	public String getSelectedWeapon() {
		this.weapon1 = new Rifle();
		this.weapon = new Bazooka();
		if (this.weapon1.getState()==true){
			System.out.println("state rifle "+weapon1.getState());
			return Rifle.getName();
		} if (this.weapon.getState()==true){
			System.out.println("state bazooka "+weapon.getState());
			return Bazooka.getName();
		}
		return "No weapon Selected";
	}
	public void selectNextWeapon() {
		this.weapon1 = new Rifle();
		this.weapon = new Bazooka();
		if (this.weapon1.getState()==true){
			this.weapon.setState(true);
			this.weapon1.setState(false);
		} if (this.weapon.getState()==true){
			this.weapon.setState(false);
			this.weapon1.setState(true);
		}getSelectedWeapon();
	}
//	public void selectNextWeapon() {
//		if (this.getSelectedWeapon() == "Rifle"){
//			this.weapon = new Bazooka();
//		}
//	}
//	private int getCurrentWeaponIndex() {
//		return currentWeaponIndex;
//	}
//
//	private void setCurrentWeaponIndex(int currentWeaponIndex) {
//		this.currentWeaponIndex = currentWeaponIndex;
//	}
//	public Collection<Projectile> getWeapon() {
//		ArrayList<Object> lijst = (ArrayList<Object>) objects;
//		Collection<Projectile> weapon = new ArrayList<Projectile>();
//		
//		
//		for (int i = 0; i < lijst.size(); i++) {
//			if (lijst.get(i) instanceof Projectile)
//				weapon.add((Projectile) lijst.get(i));
//		}
//		return weapon;
//	}
//
//	public Projectile getCurrentWeapon(){
//		return ((ArrayList<Projectile>) getWeapon()).get(this.getCurrentWeaponIndex());
//	}
//	
//	public String selectWeapon(){
//		return (String)getCurrentWeapon().getName();
//	}
	
	// variables
	private String teamName;
	private Team team;
	private Position position;
//	private double xpos;
//	private double ypos;
	private double direction;
	private double radius;
	private static double radiusLowerBound = 0.25;
	private double mass;
	private int maxActionPoints;
	private int actionPoints;
	private int maxHitPoints;
	private int hitPoints;
	private String name;
	private int  health = 10;
	private boolean alive;
	private int propulsion;
	private Bazooka weapon;
	private Rifle weapon1;
	//private int currentWeaponIndex;
	//private  List<Object> objects = new ArrayList<Object>();
	//constants
	private static final int DENSITY = 1062;
	private static final double G = 9.80665;
	

}
