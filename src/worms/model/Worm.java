package worms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of worms involving x-position, y-position a radius, a direction a mass, 
 * actionpoints, hitpoints, team participation, and a name.
 * Complemented with methods that interact with the worm and change certain values and 
 * methods that allow the worm to interact with the world of the worm and objects in that world.
 * 
 * @invar	The radius must be a valid radius for the worm.
 * 		  	| isValidRadius(this.getRadius())
 * @invar	The actionpoints of a worm must always be equal to or larger than zero.
 * 			|this.getActionPoints() >= 0
 * @invar	The actionpoints of a worm must always be equal to or smaller than the maximum actionpoints.
 * 		  	| this.getActionpoints() =< this.getMaxActionPoints()
 * @invar	The hitpoints of a worm must always be larger than zero.
 * 			|this.getHitPoints() >= 0
 * @invar	The hitpoints of a worm must always be equal to or smaller than the maximum hitpoints.
 * 		  	| this.getHitPoints() =< this.getMaxHitPoints() 
 * 	
  * @author Toon Stuyck
 * 			Toon Van Deun
 * 			Burgerlijk Ingenieur
 * 			https://github.com/ToonVanDeun/PROJECT_OP_DEEL2
 * @version 2.0
 */
public class Worm extends Object {
	/**
	 * Initialize a worm in a given world, 
	 * with a x-and y-position (meters), direction (radians), radius (meters) and a name.
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
	 * @pre		The world must be valid.
	 * 			|world!=null
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
	 * @post	The maximum amount of hitpoints is set accordingly to the worm's mass.
	 * 			|new.getMaxHitPoints() == (int) Math.round(this.getMass())
	 * @post	The amount of hitpoints are set to the maximum amount of hitpoints.
	 * 			|new.getHitPoints() == new.getMaxHitPoints()
	 * @post	The name of the worm is set to the given name.
	 * 			|new.getName() == name.
	 * @post	The Worm is added to a team is there are any teams.
	 * 			|if (world.getTeams.size()!=0)
	 * 			| 	then this.hasTeam() == true
	 * @post	The lower bound of the radius is set to the given lower bound.
	 * 			Or when no lower bound is given it's set to 0.25 as it is in this case.
	 * 			|new.getRadiusLowerBound() ==  0.25
	 */
	@Raw
	public Worm(World world, double xpos, double ypos, double direction, double radius, String name){
		super(world);
		this.position = new Position(xpos, ypos);
		this.setDirection(direction);
		this.setRadius(radius);
		this.setName(name);
		this.setActionPoints(maxActionPoints);
		this.setHitPoints(maxHitPoints);
		this.setTeamRandom();
	}
	/**
	 * Initialize a worm in a given world, 
	 * and a x-and y-position (meters), direction (radians), radius (meters) and a name will be determined.
	 * @param	world
	 * 			The world in which a worm is being added.
	 * @pre		The world must be valid.
	 * 			|world!=null
	 * @post	The x- and y-position are set to valid x- and y-positions.
	 * 			|isValidXpos(world,new.getXpos()) == true
	 * 			|isValidYpos(world,new.getYpos()) == true
	 * @post	The x- and y-position are set to passable position adjacent to impassable terrain.
	 * 			|world.isAdjacent(new.getXpos(), new.getYpos()) == true
	 * @post	The direction is set to a valid direction.
	 * 			|isValidDirection(new.getDirection()) == true
	 * @post	The radius is set to a valid radius
	 * 			|isValidRadius(new.getRadius()) == true
	 * @post	The mass of a worm is set accordingly to its radius.
	 * 			|new.getMass() == DENSITY*((4.0/3.0)*Math.PI*Math.pow(radius, 3))
	 * @post	The maximum amount of actionpoints is set accordingly to the worm's mass.
	 * 			|new.getMaxActionPoints() == (int) Math.round(this.getMass())
	 * @post	The amount of actionpoints are set to the maximum amount of actionpoints.
	 * 			|new.getActionPoints() == new.getMaxActionPoints()
	 * @post	The maximum amount of hitpoints is set accordingly to the worm's mass.
	 * 			|new.getMaxHitPoints() == (int) Math.round(this.getMass())
	 * @post	The amount of hitpoints are set to the maximum amount of hitpoints.
	 * 			|new.getHitPoints() == new.getMaxHitPoints()
	 * @post	The name of the worm is set to the given name.
	 * 			|new.getName() == name.
	 * @post	The Worm is added to a team is there are any teams.
	 * 			|if (world.getTeams.size()!=0)
	 * 			| 	then this.hasTeam() == true
	 * @post	The lower bound of the radius is set to the given lower bound.
	 * 			Or when no lower bound is given it's set to 0.25 as it is in this case.
	 * 			|new.getRadiusLowerBound() ==  0.25
	 */
	@Raw
	public Worm(World world){
		super(world);
		Random perimeter = world.getPerimeter();
		this.setRadius(radiusLowerBound);
		this.position = new Position(world, this);
		this.setDirection(perimeter.nextDouble()*(2*Math.PI));
		this.setName("Worm");
		this.setActionPoints(this.getMaxActionPoints());
		this.setHitPoints(this.getMaxHitPoints());
		this.setTeamRandom();
		
	}
	
	/**
	 * Prepare the worm for a new round.
	 * @post	If the worm is alive, The amount of action points is set to maximumActionPoints
	 * 			| if(this.alive == true)
	 * 			|	then new.getActionPoints() == this.getMaxActionPoints()
	 * @post	If the worm is alive, The amount of hitpoints is increased with health.
	 * 			| if(this.alive == true)
	 * 			|	then new.getHitPoints() == old.getHitpoints() + health
	 */
	@Raw
	public void newRound(){
		if (this.alive == true)
			this.renewActionPoints();
			this.heal(health);
	}
	
	// team
	/**
	 * Returns the name of the team if the worm has a team.
	 */
	@Basic @Raw
	public String getTeamName(){
		if (this.hasTeam())
			return this.getTeam().getName();
		return null;
	}
	/**
	 * Return the team of this worm.
	 *   A null reference is returned if this object is not in a team.
	 */
	@Basic @Raw
	public Team getTeam() {
		return this.team;
	}
	/**
	 * Check whether this worm can have the given team as its team.
	 * @param  team
	 *         The team to check.
	 * @return True, if the worm is not yet in the team.
	 * 			| !team.getAllWorms().contains(this)
	 */
	@Raw
	public boolean canHaveAsTeam(Team team) {
		if (team.getAllWorms().contains(this))
			return false;
		return true;
		
	}
	/**
	 * Check whether this worm is in a team.
	 * @return 	True if and only if the team of this worm is effective.
	 *       	| result == (getTeam() != null)
	 */
	@Raw
	private boolean hasTeam() {
		return getTeam() != null;
	}
	/**
	 * Set the team of this worm to the given team.
	 * @param  	team
	 *         	The new team for this worm.
	 * @post   	The team of this worm is the same as the given team.
	 *       	| new.getTeam() == team
	 * @post   	The number of worms of the given team
	 *         	is incremented by 1.
	 *       	| (new team).getNbWorms() == team.getNbWorms() + 1
	 * @throws 	IllegalArgumentException
	 *         	The given team is not effective.
	 *       	| (team == null)
	 * @throws 	IllegalStateException
	 *         	This worm already belongs to a team.
	 *       	| hasTeam()
	 */	
	@Raw
	public void setTeamTo(Team team)
			throws IllegalArgumentException, IllegalStateException {
		if ((team == null))
			throw new IllegalArgumentException();
		if (this.hasTeam())
			throw new IllegalStateException("Already belongs to a world!");
		setTeam(team);
		team.addAsWorm(this);
	}
	/**
	 * Set the team of this worm to the given team.
	 * @param  	team
	 *         	The new team for this worm.
	 * @pre    	This worm can have the given team as its team.
	 *       	| canHaveAsTeam(team)
	 * @post   	The team of this worm is the same as the given team.
	 *       	| new.getTeam() == team
	 */
	@Raw
	private void setTeam(@Raw Team team) {
		assert canHaveAsTeam(team);
		this.team = team;
	}
	/**
	 * Sets the team to a random team, if there are teams.
	 * @post	If there where teams the worm is added to a team.
	 * 			If there where no teams the worm isn't added to a team.
	 * 			| if ((this.getWorld().getTeams().size()!=0)) {
	 * 			|	then this.hasTeam() == true;
	 */
	@Raw
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
	 * Unsets the team, if any, from this worm.
	 * @post   	This worm no longer belongs to a team.
	 *       	| ! new.hasTeam()
	 * @post   	The former team of this worm, if any, no longer
	 *         	has this worm as one of its worms.
	 *       	|    (getTeam() == null)
	 *       	| || (! (new getTeam()).hasAsWorm(worm))
	 */
	@Raw
	public void unsetTeam() {
		if (hasTeam()) {
			Team formerTeam = this.getTeam();
			this.team=null;
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
	 * @param 	xpos
	 * 			The (new) x-position of the worm
	 * @post	the given x-position is the new x-position of the worm 
	 * 			if it is accepted by Position.
	 * 			| new.getXpos() == xpos
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
	 * @post	the given y-position is the new y-position of the worm 
	 * 			if it is accepted by Position
	 * 			| new.getYpos() == ypos
	 */
	@Raw
	private void setYpos(double ypos) {
		position.setYpos(ypos);
	}
	/**
	 * Sets the position to the nearest adjacent position to the given position, if any.
	 * @param 	xpos
	 * 			The position to which we try to find and set a new x-position that is adjacent
	 * @param 	ypos
	 * 			The position to which we try to find and set a new x-position that is adjacent
	 */
	@Raw
	private void setNearestAdjacent(double xpos, double ypos){
		position.setNearestAdjacent(this.getWorld(), xpos, ypos, this.getRadius());
	}
	/**
	 * Checks whether the given position is a valid position.
	 * @param	pos
	 * 			The positions that needs to be checked.
	 * @return 	True if the given position (pos) is a valid position.
	 * 			If the given position isn't a valid position (not a number (NaN),
	 * 			the method returns false.
	 * 			|(!Double.isNaN(pos))
	 */
	@Raw
	public boolean isValidPos(double pos) {
		return ! (Double.isNaN(pos));
	}
	/**
	 * Checks if the given position is out of the boundaries of the map.
	 * @param 	xpos
	 * 			The x-position to be checked.
	 * @param 	ypos
	 * 			The y-position to be checked.
	 * @return	True if the given position isn't in the boundaries of the map.
	 * 			False if e given position is in the boundaries of the map.
	 * 			| !((xpos<=(world.getWidth()-this.getRadius()))&&((xpos>=this.getRadius()))&&
	 *			|	((ypos <= world.getHeight()-this.getRadius())) && ((ypos>=this.getRadius())))
	 */
	@Raw
	private boolean isOutOfTheMap(double xpos, double ypos) {
		World world= this.getWorld();
		return !((xpos<=(world.getWidth()-this.getRadius()))&&((xpos>=this.getRadius()))&&
				((ypos <= world.getHeight()-this.getRadius())) && ((ypos>=this.getRadius()))) ;
	}
	/**
	 * Checks if the worm can fall.
	 * That is if the worm's position is passable and is not adjacent.
	 * @return 	True if the worm can fall.
	 * 			False if the worm can't fall.
	 * 			| world.isPassable(this.getXpos(), this.getYpos(), this.getRadius()) &&
				|!world.isAdjacent(this.getXpos(), this.getYpos(), this.getRadius()));
	 */
	@Raw
	public boolean canFall() {
		World world = this.getWorld();
		return( world.isPassable(this.getXpos(), this.getYpos(), this.getRadius()) &&
				!world.isAdjacent(this.getXpos(), this.getYpos(), this.getRadius()));
	}
	/**
	 * Makes the worm fall, if it can fall, until the worm falls on an obstacle or falls out of the map.
	 *
	 * @post	The worm has fallen to a new position that is adjacent. The x-position stays the same but 
	 * 			the y-position changes.
	 * 			|this.getWorld().isAdjacent(new.getXpos(),new.getYpos(), new.getDirecetion) == true
	 * 			|new.getXpos()==old.getXpos()
	 * 			|while (world.isPassable(this.getXpos(), this.getYpos(), this.getRadius()))
	 * 			|	newYpos = this.getYpos()-this.getRadius()
	 * 			|	if (this.getYpos()<0) 
	 * 			|		then new.getYpos == (this.getRadius()*1.1)
	 * 			|		break;
	 * 			|	else if ((this.getYpos()>=0)){	
	 * 			|		while (!world.isAdjacent(this.getXpos(), this.getYpos(), this.getRadius())) 
	 * 			|			newYpos = (this.getYpos()+0.1*this.getRadius())
	 * 			|		new.getYpos() == newYpos
	 * @post	The worms hitpoints are correctly reduced.
	 * 			|new.getHitPoints() == old.getHitPoints() - 3*distance
	 * @post	After the fall the worm could be on food this gets checked.
	 * 			| this.consumeFood()
	 * @throws	IllegalStateException
	 * 			If the worm can't fall because he is on adjacent terrain the exception is thrown.
	 * 			| ! canFall()		
	 */
	@Raw
	public void fall() throws IllegalStateException{
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
			this.consumeFood();
		}
		else {
			throw new IllegalStateException();
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
	@Basic @Raw
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
	@Raw
	public void setRadius(double radius) throws IllegalArgumentException{
		if ( ! isValidRadius(radius))
			throw new IllegalArgumentException();
		this.radius = radius;
		this.setMass(radius);
		
	}
	/**
	 * Checks whether a given radius is a valid radius.
	 * @param radius
	 * 			The radius that's being checked.
	 * @return	True if and only if the given radius is larger than or equal to the minimal allowed radius.
	 * 			| result == (radius >= this.getRadiusLowerBound())
	 */
	@Raw
	public boolean isValidRadius(double radius){
		return radius >= this.getRadiusLowerBound();
	}
	/**
	 * Returns the lower bound of the radius (meters)
	 * 	the lower bound of the radius is the minimal allowed radius of the worm.
	 */
	@Basic @Raw
	public double getRadiusLowerBound() {
		return Worm.radiusLowerBound;
	}
	/**
	 * Sets the minimal allowed radius to lower bound;
	 * @param lowerbound
	 * 			The minimal allowed radius of the worm.
	 * 			Originally initialized at 0.25m.
	 */
	@Raw
	private static void setRadiusLowerBound(double lowerbound) {
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
	@Raw
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
	private static boolean isValidName(String name){
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
	@Raw
	private void setMaxActionPoints(){
		
		if (this.getMass() < Integer.MAX_VALUE)
			this.maxActionPoints = (int) Math.round(this.getMass());
		else 
			this.maxActionPoints =  Integer.MAX_VALUE;
	}
	/**
	 * Return the current amount of action points for this worm.
	 */
	@Basic  @Raw
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
	@Raw
	private void setActionPoints(int actionPoints){
		if (actionPoints >= (this.getMaxActionPoints()))
			this.actionPoints = this.getMaxActionPoints();
		else if (actionPoints <0)
			this.actionPoints = 0;
		else 
			this.actionPoints = actionPoints;
	}
	/**
	 * This methods refills the actionpoints to its maximum.
	 * 
	 * @post	The actionspoints are refilled to its maximum
	 * 			| new.getActionPoints() == this.getMaxActionPoints()
	 */
	@Raw
	public void renewActionPoints(){
		this.setActionPoints(this.getMaxActionPoints());
	}
	//hitpoints (total)
	/**
	 * Return the maximal amount of hit points for this worm.
	 */
	@Basic @Raw
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
	@Raw
	private void setMaxHitPoints(){
		
		if (this.getMass() < Integer.MAX_VALUE)
			this.maxHitPoints = (int) Math.round(this.getMass());
		else 
			this.maxHitPoints =  Integer.MAX_VALUE;
	}
	/**
	 * Return the current amount of hit points for this worm.
	 */
	@Basic @Raw
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
	@Raw
	public void setHitPoints(int hitPoints){
		if (hitPoints >= (this.getMaxHitPoints())) {
			this.hitPoints = this.getMaxHitPoints();
		} else if (hitPoints <0) {
				this.hitPoints = 0;
				this.setIsAlive();
				this.deleteWorm(this.getWorld());
		} else if (hitPoints < this.getMaxHitPoints()) {
				this.hitPoints = hitPoints;
		} 
		this.setIsAlive();
	}
	/**
	 * This method checks whether the worm still has hitpoints over else it is set as a dead worm.
	 * 
	 * @post	If the hitpoints of the worm >0 then it is alive but if the hitpoints ==0 then the worm is set dead.
	 * 			| if (this.getHitpoints() == 0)
	 * 			|	then new.getAlive() == false
	 * 			| else then new.getAlive() == true
	 */
	@Raw
	public void setIsAlive(){
		if(this.getHitPoints()==0) {
			this.alive = false;
		}
		else if(this.getHitPoints() > 0){
			this.alive = true;
		}
	}
	/**
	 * This method return whether or not the worm is still alive.
	 */
	@Raw
	public boolean getIsAlive(){
		return this.alive;
	}
	/**
	 * This method kills a worm.
	 * 
	 * @post	The hitpoints of a worm are set to 0.
	 * 			| new.getHitPoints() == 0
	 * @post	The state of the worm will be set to dead because he has no hitpoints left.
	 * 			| new.getIsAlive() == false
	 * @post	The worm will be deleted from the current world.
	 * 			| new.objects.contains(this) == false;
	 */
	@Raw
	public void killWorm() {
		this.setHitPoints(0);
		this.setIsAlive();
		this.deleteWorm(this.getWorld());
	}
	/**
	 * This method deletes a worm from the list of objects.
	 * @param world
	 * 			The world from where the worm needs to be deleted.
	 * 
	 * @post	The worm will be deleted from the current world.
	 * 			| new.objects.contains(this) == false;
	 */
	@Raw
	public void deleteWorm(World world){
		int index = world.getCurrentWormIndex()+1;
		int size = world.getWorms().size();
		if (index==size)
			this.getWorld().setCurrentWormIndex(0);
		if (this.getIsAlive() == false)
			world.deleteWorm(this);
	}
	/**
	 * This method heals the worm with an amount of health.
	 * @param 	amount
	 * 			The amount of health which the worm will get.
	 * 
	 * @post	If the worm is still alive it will get an amount of new hitpoints else it will be deleted.
	 * 			| if (this.getAlive() == true)
	 * 			| 	then new.getHitPoints() == old.getHitPoints() + amount
	 * 			| else then new.objects.contains(this) == false;
	 */
	@Raw
	public void heal(int amount){
		if (this.alive)
			this.setHitPoints(this.getHitPoints()+amount);
		else 
			this.deleteWorm(getWorld());
	}
	//move (defensive)
	/**
	 * The method makes the worm move to a next position that is adjacent to impassable terrain following the slope
	 * of that terrain in the direction. The worm shall aim to maximize the distance while minimizing the divergence.
	 * If no such location exists because all locations in the direction +- 0,7875 are impassable the worm shall remain at its current position.
	 * If locations in the direction are passable but not adjacent the worm shall move there and then drop passively.
	 * @post	If the worm can maximize the distance while minimizing the divergence, 
	 * 			the worm has moved to the optimal location.
	 * 			|for (double a = 0.1;a<=this.getRadius();a=a+(0.01*a)) {
	 *			|	x2 = x+Math.cos(direction)*a;
	 *			|	y2 = y+Math.sin(direction)*a;
	 *			|	if (world.isAdjacent(x2, y2, this.getRadius()) &&
	 *			|			world.isPassable(x2, y2, this.getRadius())) {
	 *			|		double d = Math.sqrt(Math.pow((x-x2),2)+Math.pow((y-y2),2));
	 *			|		double s = Math.atan((x-x2)/(y-y2));
	 *			|		if ((d>=maxD) && (s<minS)) {
	 *			|			minS=s;
	 *			|			maxD=d;
	 *			|			x2Max = x2;
	 *			|			y2Max= y2;
	 *			|	direction = direction +0.0175;
	 *			|new.getXpos()==x2Max
	 *			|new.getYpos() == y2Max
	 * @post	If the worm can't maximize the distance while minimizing the divergence and there is 
	 * 			only impassable terrain in the checked directions, the worm will not have moved.
	 * 			|new.getXpos() == old.getXpos()
	 * 			|new.getYpos() == old.getYpos()
	 * @post	If the worm can't maximize the distance while minimizing the divergence and there is 
	 * 			only passable terrain in the checked directions that is not adjacent, the worm will move there.
	 * 			|new.getXpos() == old.getXpos() + cos(direction)*radius
	 * 			|new.getYpos() == old.getYpos() + sin(direction)*radius
	 * @post	The worms actionpoints are correctly reduced.
	 * 			|new.getActionPoints == old.getActionPoints() - old.computeCost2(old.getXpos(),old.getYpos())
	 * @throws	IllegalArgumentException
	 * 			If the worm can't move because he has insufficient actionpoints
	 * 			the exception is thrown.
	 * 			| ! isValidStep()
	 * @throws	IllegalStateException
	 * 			If the worm can't move because the worm isn't positioned in passable terrain 
	 * 			and adjacent to impassable terrain the exception is thrown.
	 * 			| ! canMove()
	 */
	@Raw
	public void move() throws IllegalArgumentException,IllegalStateException {
		if ( ! isValidStep())
			throw new IllegalArgumentException();
		if (canMove()) { 
			World world = this.getWorld();
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
			if (this.isOutOfTheMap(x2Max, y2Max)){
				this.killWorm();
			} else {
			this.setXpos(x2Max);
			this.setYpos(y2Max);
			this.setActionPoints(this.getActionPoints()-this.computeCost2(prevx, prevy));
			}
			
			// geval2: Er werd in direction+-45° geen geschikte plaats gevonden
			//			nagaan of er in direction naar een passable locatie kan verplaatst worden,
			//			daarnaar verplaatsen en dan vallen (fall).
			if ((x2Max == x) && (y2Max == y)) {
				double pasXpos = x;
				double pasYpos = y;
				
				pasXpos = (x + (Math.cos(this.getDirection())*this.getRadius()));
				pasYpos = (y + (Math.sin(this.getDirection())*this.getRadius()));
				if (!world.isAdjacent(pasXpos, pasYpos, this.getRadius()) && world.isPassable(pasXpos, pasYpos, this.getRadius())) {
					if (this.isOutOfTheMap(pasXpos, pasYpos)){
						this.killWorm();
					} else {
					this.setXpos(pasXpos);
					this.setYpos(pasYpos);
					this.setActionPoints(this.getActionPoints()-this.computeCost2(prevx, prevy));
					}
				}
			} 
			this.consumeFood();
		}
		else throw new IllegalStateException();
	}
	
	/**
	 * Returns true if the worm is positioned in passable terrain 
	 * 	and adjacent to impassable terrain.
	 */
	@Raw
	private boolean canMove() {
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
	/**
	 * Returns the cost in actionpoints to move in the current direction.
	 * @param prevXpos
	 * 			The x-position of the worm before he moved.
	 * @param prevYpos
	 * 			The y-position of the worm before he moved.
	 */
	@Raw
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
	@Raw
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
	@Raw
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
	@Raw
	public void turn(double angle){
		assert this.isValidTurn(angle);
		this.setDirection(this.getDirection()+ angle);
		this.setActionPoints(this.getActionPoints()-this.computeCostTurn(angle));
	}
	
	//jump (defensive)
	/**
	 * Changes the positions of the worm as a result of a jump from the current position.
	 * @post	If the worm jumped out of the map it will have been removed.
	 * 			|this.getWorld().getWorms().contains(this) == false
	 * @post 	The worm jumped to the correct position
	 * 			| while (world.isPassable(tempXpos, tempYpos, this.getRadius()))
	 *			|	tempXpos = this.jumpStep(t)[0]
	 *			|	tempYpos = this.jumpStep(t)[1]
	 *			|	t += timeStep
	 *			|	if ((world.isAdjacent(tempXpos, tempYpos, this.getRadius())) &&  
	 *			|			(Math.sqrt(Math.pow((origXpos-tempXpos), 2)+Math.pow((origYpos-tempYpos), 2))>=this.getRadius() ))
	 *			|				new.getXpos() == tempXpos
	 *			|				new.getYpos() == tempYpos
	 * @post	The worm's actionpoints are reduced to zero.
	 * 			|new.getActionPoints() == 0;
	 * @throws 	IllegalStateException
	 * 			When the worm has no action point left to jump the exception is thrown.
	 * 			|! canJump()
	 */
	@Raw
	public void jump(Double timeStep) throws IllegalStateException{
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
		} else throw new IllegalStateException();
	}
	
	//~jump ~actionpoints (total)
	/**
	 * Checks whether the worms still has actionpoints is placed in passable terrain.
	 */
	@Raw
	private boolean canJump() {
		World world = this.getWorld();
		return ((this.getActionPoints() > 0) && world.isPassable(this.getXpos(), this.getYpos(), this.getRadius()));
	}
	//~jump (extra methods used for calculations needed by the method jump.)
	/**
	 * Returns the jump velocity of a worm.
	 * 	this is needed in to calculate the distance over which to worm can jump.
	 */
	@Basic @Raw
	private double jumpVelocity() {
		double force = (5*this.getActionPoints())+(this.getMass()*G);
		double velocity = ((force/this.getMass())*0.5);
		return velocity;
	}
	/**
	 * Returns the time it takes to worm to jump (to his new position).
	 * @throws	IllegalStateException
	 * 			If the worm can't jump the exception is thrown.
	 * 			| ! canJump()
	 */
	@Raw
	public double jumpTime(double timeStep) throws IllegalStateException{
		World world = this.getWorld();	
		double origXpos = this.getXpos();
		double origYpos = this.getYpos();
		double tempXpos = this.getXpos();
		double tempYpos = this.getYpos();
		double t=0;
		if (this.canJump()){
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
		else throw new IllegalStateException();
	}
	
	/**
	 * Returns the worms position during a jump on a given time (after the jump started).
	 * @param timeAfterLaunch
	 * 			The time after the jump started
	 * @throws	IllegalStateException
	 * 			If the worm can't jump the exception is thrown.
	 * 			| ! canJump()
	 */
	@Basic @Raw
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
	
	//Eating food
	/**
	 * This method makes the worm eat food.
	 * @post	When the food gets eaten the radius of the worm will increas with 10%.
	 * 			| new.getRadius() == old.getRadius() * 1.1
	 * @post	When the food gets eaten the worm will be placed to the nearest adjecent location.
	 * 			| world.isAdjacent(xpos, ypos , radius) == true
	 * @post	When the food gets eaten this food object will de removed from the world.
	 * 			| (! (this.getWorld()).hasAsObject(food))
	 */
	@Raw
	public void consumeFood() {
		double xpos = this.getXpos();
		double ypos = this.getYpos();
		Food food = this.overlappingFood();
		if (!(food==null)) {
			this.setRadius(this.getRadius()*1.1);
			this.setNearestAdjacent(xpos, ypos);
			food.unsetWorld();	
		}
	}
	/**
	 * This method return the food objects that overlap with the worms position.
	 */
	@Raw
	public Food overlappingFood() {
		World world = this.getWorld();
		double maxDistance = this.getRadius() + 0.2;
		Food overlappingFood = null;
		Collection<Food> collection = (world.getFood());
	    for (Food f : collection) {
	    	if (Math.sqrt(Math.pow(f.getXpos()-this.getXpos(), 2)+
	    			Math.pow(f.getYpos()-this.getYpos(), 2))< maxDistance) {
	    		overlappingFood = f;
	    		break;
	    	} else {
	    		overlappingFood = null;
	    	}
	    }
	    return overlappingFood;
	}
	
	// shoot
	Weapon weapon = new Weapon();
	/**
	 * This method checks whether the propulsion is between 0 and 100.
	 * @param propulsion
	 * 			The propulsion that needs to be checked.
	 * @return	True if the propulsion is between 0 and 100.
	 */
	@Raw
	private boolean isValidPropulsion(int propulsion) {
		return (propulsion >= 0 && propulsion <= 100);
	}
	/**
	 * This method sets the given propulsion.
	 * @param propulsion
	 * 			The propulsion that needs to be set.
	 * @throws IllegalArgumentException
	 * 			If the propulsion isn't a valid propulsion the error is thrown.
	 * 			| if !(isValidPropulsion(propulsion))
	 */
	@Raw
	private void setPropulsionYield(int propulsion) throws IllegalArgumentException {
		if (isValidPropulsion(propulsion)) {
			this.propulsion = propulsion;
		} else 
			throw new IllegalArgumentException();
	}
	/**
	 * This method returns the propulsion yield.
	 */
	@Raw
	public int getPropulsionYield(){
		return this.propulsion;
	}
	/**
	 * This method returns the mass of the projectile of the selected weapon.
	 */
	@Raw
	public int getMassProjectile(){
		return weapon.getMass();
	}
	/**
	 * This method returns the name of the selected weapon.
	 */
	@Raw
	public String getSelectedWeapon(){
		return weapon.getName();
	}
	/**
	 * This method makes the worm select the next weapon.
	 */
	@Raw
	public void selectNextWeapon(){
		weapon.changeWeapon();
	}
	/**
	 * This method checks whether a worm can shoot and sets the cost.
	 * @return	True if the cost to use the weapon is smaller then 
	 * 			the remaining action points of the worm and if the worm is positioned on passable
	 * 			terrain and false if the cost to use the weapon is bigger then the 
	 * 			remaining action points and if the worm is not placed on passable terrain.
	 * 			| this.getActionPoints() >= weapon.getCost()
	 * 			| !this.getWorld().isImpassable(this.getXpos(),this.getYpos(),this.getRadius()) 
	 */
	@Raw
	private boolean canShoot(){
		if (this.getSelectedWeapon()=="Rifle"){
			this.cost = 10;
		}
		else {
			this.cost = 50;
		}
		if (((this.getActionPoints()- this.cost) >=0) && (!this.getWorld().isImpassable(this.getXpos(), this.getYpos(), this.getRadius())))

			return true;
		else 
			return false;
	}
	@Raw
	private void setCost(){
		if (this.getSelectedWeapon() == "Rifle")
			this.cost = 20;
		else 
			this.cost = 50;
	}
	@Raw
	public int getCost(){
		return this.cost;
	}
	/**
	 * This method makes the worm shoot a projectile.
	 * @param yield
	 * 			The yield that is used to shoot the projectile.
	 * @post	If the worm can shoot, a new projectile is made in the current world
	 * 			with a x-position and y-position outside the worm who is shooting.
	 * 			| new Projectile(world, this.getXpos(), this.getYpos(), this)
	 * @post	If the worm can shoot its actionpoints will also be changed.
	 * 			| new.getActionPoints() == old.getActionPoints() - weapon.getCost()
	 * @throws IllegalArgumentException
	 * 			If the given yield is not a valid yield the exepction is thrown.
	 * 			| !isValidPropulsion(yield)
	 * @throws IllegalStateException
	 * 			If the worm cannot shoot because it has not enough action points or it is located
	 * 			on impassable terrain the exception will be thrown.
	 * 			| !canShoot()
	 */
	@Raw
	public void shoot(int yield) throws IllegalArgumentException, IllegalStateException{
		if( !isValidPropulsion(yield)){
			throw new IllegalArgumentException();
		} else {
			if( !canShoot()){
				throw new IllegalStateException();
			} else {
				World world = this.getWorld();
				this.setPropulsionYield(yield);
				new Projectile(world,this.getXpos(),this.getYpos(),this);
				this.setActionPoints(this.getActionPoints()-this.getCost());
			}
		}
	}
	// variables
	
	private Team team;
	private Position position;
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
	private int cost;
	//constants
	private static final int DENSITY = 1062;
	private static final double G = 9.80665;
	

}
