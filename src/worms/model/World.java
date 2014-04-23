/**
 * 
 */
package worms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of World involving width, height, passableMap, and lists of objects.
 * Complemented with methods to interact with the world and change certain values.
 * 
 * @invar	The width is a valid width.
 * 			|isValidWidth(this.getWidth()) ==true
 * @invar	The height is a valid height.
 * 			|isValidHeight(this.getHeight())==true
 * 	
 * @author 	Toon Stuyck
 * 			Toon Van Deun
 * 			Burgerlijk Ingenieur
 * 			https://github.com/ToonVanDeun/PROJECT_OP_DEEL2
 * @version 2.0
 */
public class World {
	/**
	 * Initialize a world with a width, height, passableMap and random object.
	 * @param width		The width of the new world.
	 * @param height	The height of the new world.
	 * @param passableMap	The passableMap for the new world.
	 * @param random	The random object for the world (perimeter).
	 * 
	 * @post	The UpperboundHeight is initialized to the given height.
	 * 			| new.getUpperboundHeight() == height
	 * @post	The upperboundWidth is initialized to the given height.
	 * 			| new.getUpperboundWidth() == width
	 * @post	The height is set to the given height.
	 * 			| new.getHeight() == height
	 * @post	The width is set to the given width.
	 * 			| new.getWidth() == width
	 * @post	The passableMap is set to the given passableMap.
	 * 			|new.getPassableMap() == passableMap
	 * @post	The perimeter is set to the given random object.
	 * 			|new.getPerimeter == random
	 */
	@Raw
	public World(double width, double height, boolean[][] passableMap, Random random) {
		World.setUpperboundHeight(height);
		World.setUpperboundWidth(width);
		this.setHeight(height);
		this.setWidth(width);
		this.passableMap = passableMap;
		this.perimeter = random;
	}
	
	//init
	/**
	 * Returns the perimeter, a random object to determine several random aspects of the world.
	 */
	@Basic @Raw
	public Random getPerimeter() {
		return this.perimeter;		
	}
	
	//Width and Height
	/**
	 * Returns the width of the world.
	 */
	@Basic @Raw
	public double getWidth() {
		return this.width;
	}
	/**
	 * Sets the width of the world.
	 * @param width
	 * 			The (new) width of the world
	 * @post	the given width is the new width of the world.
	 * 			| new.getWidth() == width
	 * @throws	IllegalArgumentException
	 * 			If width isn't a valid width the exception is thrown.
	 * 			| ! isValidWidth(width)
	 */
	@Raw
	public final void setWidth(double width) throws IllegalArgumentException {
		if (! isValidWidth(width))
			throw new IllegalArgumentException();
		this.width = width;
	}
	/**
	 * Checks whether the given width is a valid width.
	 * @param	width
	 * 			The width that needs to be checked.
	 * @return 	True if the given width is a valid width.
	 * 			If the given width isn't a valid width 
	 * 			(smaller then 0 or bigger then Double.MAX_VALUE or bigger than upperBoundWidth),
	 * 			the method returns false.
	 * 			| ((0<= width) && (width<=Double.MAX_VALUE) && (width<=this.getUpperboundWidth()))
	 */
	@Raw
	private boolean isValidWidth(double width){
		return ((0<= width) && (width<=Double.MAX_VALUE) && (width<=this.getUpperboundWidth()));
	}
	/**
	 * Returns the upper bound width of the world.
	 */
	@Basic @Raw
	public double getUpperboundWidth() {
		return World.upperboundWidth;
	}
	/**
	 * Sets the upper bound for the width of the world. 
	 * @param 	upperboundWidth
	 * 			The new upperboundWidth for the world.
	 * @post 	The upperboundWidth of the world is set to the given upperboundWidth.
	 * 			|new.getUpperboundWidth() ==uperboundWidth
	 * @throws 	IllegalArgumentException
	 * 			If the upperboundWidth is not a valid upperboundWidth.
	 * 			| upperboundWidth<0
	 */
	@Raw
	public static void setUpperboundWidth(double upperboundWidth) throws IllegalArgumentException {
		if ( upperboundWidth<0)
			throw new IllegalArgumentException();
		World.upperboundWidth = upperboundWidth;
	}
	/**
	 * Returns the height of the world.
	 */
	@Basic @Raw
	public double getHeight() {
		return this.height;
	}
	/**
	 * Sets the height of the world.
	 * @param height
	 * 			The (new) height of the world
	 * @post	the given height is the new height of the world.
	 * 			| new.getHeight() == height
	 * @throws	IllegalArgumentException
	 * 			If height isn't a valid height the exception is thrown.
	 * 			| ! isValidHeight(height)
	 */
	@Raw
	public final void setHeight(double height) throws IllegalArgumentException {
		if (! isValidHeight(height))
			throw new IllegalArgumentException();
		this.height = height;	
	}
	/**
	 * Checks whether the given height is a valid height.
	 * @param	height
	 * 			The height that needs to be checked.
	 * @return 	True if the given height is a valid height.
	 * 			If the given height isn't a valid height 
	 * 			(smaller then 0 or bigger then Double.MAX_VALUE or bigger that upperboundHeight),
	 * 			the method returns false.
	 * 			|(0<= height) && (height<=Double.MAX_VALUE) && (height<=this.getUpperboundHeight())
	 */
	@Raw
	private boolean isValidHeight(double height){
		return ((0<= height) && (height<=Double.MAX_VALUE) && (height<=this.getUpperboundHeight()));
	}
	/**
	 * Returns the upper bound height of the world.
	 */
	@Basic @Raw
	public double getUpperboundHeight() {
		return World.upperboundHeight;
	}
	/**
	 * Sets the upper bound for the height of the world.
	 * @param 	upperboundHeight
	 * 			The new upperboundHeight for the world.
	 * @post 	The upperboundHeight of the world is set to the given upperboundHeight.
	 * 			|new.getUpperboundHeight() ==uperboundHeight
	 * @throws IllegalArgumentException
	 * 			if the upperboundHeight is not a valid upperboundHeight.
	 */
	@Raw
	public static void setUpperboundHeight(double upperboundHeight) throws IllegalArgumentException {
		if (upperboundHeight<0)
			throw new IllegalArgumentException();
		World.upperboundHeight = upperboundHeight;
	}
	
	//Passable map
	/**
	 * Returns the passable map of the world.
	 */
	@Basic
	public boolean[][] getPassableMap() {
		return this.passableMap;
	}
	/**
	 * Determines whether an object with given x position, given y position and given radius, 
	 * is placed in passable terrain and doesn't overlap with impassable Terrain.
	 * @param 	x	
	 * 			The given x position
	 * @param 	y	
	 * 			The given y position
	 * @param 	radius	
	 * 			The given radius
	 * @return 	True if the object is positioned in a passable location and 
	 * 			doesn't overlap with impassable terrain.
	 * 			False if the object isn't positioned in impassable terrain or 
	 * 			overlaps with impassable terrain.
	 * 			| (isLocatedOnPassableLocation(position) && doesNotOverlapWithImpassableTerrain(position,radius)
	 */
	@Raw
	public boolean isPassable(double x, double y, double radius) {
		int mapWidth = this.getPassableMap()[1].length; //eigenlijk height
		int mapHeight = this.getPassableMap().length; //eigenlijk width
		double xScale = (mapWidth/this.getWidth()); //schaalfactor voor een x coordinaat van world
		double yScale = (mapHeight/this.getHeight());//schaalfactor voor een y coordinaat van world
		if (((x>=0)&&(x<=this.getWidth())&&(y>=0)&&y<=this.getHeight()) && 
				(((x-radius)<0) 	|| (((x+radius)>this.getWidth()) || ((y-radius)<0) || ((y+radius)>this.getHeight())))) {
			return (this.getPassableMap()[(int) Math.floor((this.getHeight()-(y))*yScale)][(int) Math.floor((x)*xScale)]);
		}
		for (double dir=0;dir<2*Math.PI;dir+=0.3) {
			if (!(this.getPassableMap()[(int) Math.floor((this.getHeight()-(y))*yScale)][(int) Math.floor((x)*xScale)]) ||
				!(this.getPassableMap()[(int) Math.floor((this.getHeight()-(y+radius*Math.sin(dir)))*yScale)][(int) Math.floor((x+radius*Math.cos(dir))*xScale)]))  {
				return false;
			}
		}
		return true;
	}
	/**
	 * Determines whether an object with given x position, given y position and given radius, 
	 * is placed in impassable terrain or overlaps with impassable Terrain.
	 * @param 	x	
	 * 			The given x position
	 * @param 	y	
	 * 			The given y position
	 * @param 	radius	
	 * 			The given radius
	 * @return	True is the given position overlaps with, or is positioned in impassable terrain.
	 * 			!this.isPassable(x, y, radius);
	 */
	@Raw
	public boolean isImpassable(double x, double y, double radius) {		
		return !this.isPassable(x, y, radius);
	}
	/**
	 * Checks whether an objects with given x position, given y position and given radius, 
	 * is placed in adjacent terrain.
	 * Adjacent means: Passable for radius and impassable for radius*1.1.
	 * @param 	x	
	 * 			The given x position
	 * @param 	y	
	 * 			The given y position
	 * @param 	radius	
	 * 			The given radius
	 * @return True if the object is placed on adjacent terrain
	 * 			False if the object isn't placed on adjacent terrain
	 * 			|(this.isPassable(x, y, radius)) && (this.isImpassable(x, y, radius*1.1))
	 */
	@Raw
	public boolean isAdjacent(double x, double y ,double radius){
		if ((this.isPassable(x, y, radius)) && (this.isImpassable(x, y, radius*1.1))){
			return true;
		}
		return false;
	}
	
	//Worm Index and Current Worm
	/**
	 * The index of the worm that is currently being controlled.
	 */
	@Basic @Raw
	public int getCurrentWormIndex() {
		return currentWormIndex;
	}
	/**
	 * Sets the currentWormIndex to the given currentWormIndex.
	 * @param 	currentWormIndex	
	 * 			The new currentWormIndex.
	 * @post	The current worm index must be a valid index
	 * 			| currentWormIndex>=0
	 * @post 	The currentWormIndext is set to the given currentWormIndex
	 * 			|new.getCurrentWormIndex() == currentWormIndex
	 */
	@Raw
	public void setCurrentWormIndex(int currentWormIndex) {
		if (currentWormIndex<0){
			currentWormIndex = 0;
		}
		this.currentWormIndex = currentWormIndex;
	}
	/**
	 * Returns the the worm that is currently being controlled.
	 * (The worm on position "getCurrentWormIndex" in the list of worms.)
	 */
	@Basic @Raw
	public Worm getCurrentWorm(){
		return ((ArrayList<Worm>) getWorms()).get(this.getCurrentWormIndex());
	}

	//Start, turns, Finished, Winners
	/**
	 * Starts the game.
	 * (By setting the currentWormINdext to zero.)
	 */
	@Raw
	public void	startGame(){
		setCurrentWormIndex(0);
	}
	/**
	 * Cycles through all worms turn by turn.
	 * When all worms have had a turn a new round is started.
	 */
	@Raw
	public void startNextTurn(){
		if (getCurrentWormIndex() >= (getWorms().size()-1))
			startNextRound();
		else
			setCurrentWormIndex(getCurrentWormIndex()+1);
	}
	/**
	 * Starts a new round.
	 */
	@Raw
	private void startNextRound(){
		for (Worm worm: getWorms()){
			worm.newRound();
		}
		setCurrentWormIndex(0);
	}
	/**
	 * Returns the winner, if the game is finished.
	 * If there are no teams the last worm the is alive will be the winner.
	 * If only one team remains the winner is the remaining team.
	 * If from the only remaining team only one worm remains, that worm will be the winner.
	 */
	@Raw
	public String getWinner(){
		Team team = ((Worm) this.getWorms().toArray()[0]).getTeam();
		if (team ==null) {
			return ((Worm) this.getWorms().toArray()[0]).getName();
		} else{
			if (team.getAllAliveWorms().size()==1) {
				return ((Worm) this.getWorms().toArray()[0]).getName();
			}else {
				return "TEAM " + ((Worm) this.getWorms().toArray()[0]).getTeamName();
			}
		}
			
	}
	/**
	 * Checks whether the game is finished or not.
	 */
	@Raw
	public boolean isGameFinished(){
		if (this.getWorms().size()>0){
			Team team = ((Worm) this.getWorms().toArray()[0]).getTeam();
			for (Worm worm : this.getWorms()){
				if (!(worm.getTeam()==team)){
					return false;
				}
			}if (team==null)
				if (this.getWorms().size()==1) {
					return true;
				} else {
					return false;
				}
			return true;
		}
		return false;
	}

	
	//All objects
	/**
	 * Returns the number of objects in this world.
	 */
	@Basic @Raw
	public int getNbObjects() {
		return objects.size();
	}
	/**
	 * Check whether this world can have the given object
	 * as one of its objects.
	 * 
	 * @param  	object
	 *         	The object to check.
	 * @return 	True if and only if the given object is effective, and
	 *         	if that object can have this world as its world.
	 *     		| result ==
	 *       	|   (object != null) &&
	 *       	|   object.canHaveAsWorld(this)
	 */
	@Raw
	public boolean canHaveAsObject(Object object) {
		return (object != null) && object.canHaveAsWorld(this);
	}
	/**
	 * Check whether this world has the given object as one of
	 * its objects.
	 * @param  	object
	 *         	The object to check.
	 * @return 	True if and only if this world has the given object
	 *         	as one of its objects at some index.
	 *       	| result ==
	 *       	|   for some index in 1..getNbObjects():
	 *       	|     getObjectAt(index).equals(object)
	 */
	@Raw
	public boolean hasAsObject(Object object) {
		return objects.contains(object);
	}
	/**
	 * Return a list of all the objects of this world.
	 * @return 	The size of the resulting list is equal to the number of
	 *         	objects in this world.
	 *       	| result.size() == getNbObjects()
	 * @return 	Each element in the resulting list is the same as the
	 *         	objects of this world at the corresponding index.
	 *       	| for each index in 0..result-size()-1 :
	 *       	|   result.get(index) == getObjectAt(index+1)
	 */
	@Basic @Raw
	public List<Object> getAllObjects() {
		return new ArrayList<Object>(objects);
	}
	/**
	 * Add the given object at the end of the list of
	 * objects of this world.
	 * @param  object
	 *         The object to be added.
	 * @pre    The given object is effective and already references
	 *         this world as its world.
	 *         | (world != null) && (object.getWorld() == this)
	 * @pre    This world does not yet have the given object
	 *         as one of its objects.
	 *         | ! hasAsObject(object)
	 * @post   The number of objects of this world is incremented
	 *         by 1.
	 *         | new.getNbObjects() == getNbObjects() + 1
	 * @post   This world has the given object as its new last
	 *         object.
	 *         | new.getObjectAt(getNbObjects()+1) == object
	 */
	@Raw
	public void addAsObject(@Raw Object object) {
		assert (object != null) && (object.getWorld() == this);
		assert !hasAsObject(object);
		objects.add(object);
	}
	/**
	 * Remove the given object from the objects of this world.
	 * @param 	object
	 *       	The object to be removed.
	 * @pre  	The given object is effective and does not have any
	 *        	world.
	 *       	| (object != null) && (object.getWorld() == null)
	 * @pre  	This world has the given object as one of
	 *       	its objects.
	 *     		| hasAsObject(object)
	 * @post  	The number of objects of this world is decremented
	 *        	by 1.
	 *       	| new.getNbObjects() == getNbObjects() - 1
	 * @post  	This world no longer has the given object as
	 *       	one of its objects.
	 *     		| (! new.hasAsObject(object))
	 * @post  	All objects registered beyond the removed object
	 *         	shift one position to the left.
	 *     		| for each index in getIndexOfObject(object)+1..getNbObjects():
	 *      	|   new.getObjectAt(index-1) == getObjectAt(index) 
	 */
	@Raw
	public void removeAsObject(Object object) {
		assert (object != null) && (object.getWorld() == null);
		assert (hasAsObject(object));
		objects.remove(object);
	}
	
	
	//List of Worms
	/**
	 * Returns a collection of all the worms in the world.
	 * @post 	the size of the collection must be equal to or less than het size of all the objects in the world.
	 * 			| result.size()<=this.objects.size()
	 * @post 	the collection only contains objects of the class Worm
	 * 			| for each index in 0..result.size()-1:
	 *      	|   (result.get(i) instanceof Worm) == true
	 */
	@Basic @Raw
	public Collection<Worm> getWorms() {
		ArrayList<Object> lijst = new ArrayList<Object>(objects);
		Collection<Worm> worms = new ArrayList<Worm>(); 
		
		for (int i = 0; i < lijst.size(); i++) {
			if (lijst.get(i) instanceof Worm)
				worms.add((Worm) lijst.get(i));
		}
		return worms;
	}
	/**
	 * Deletes a worm from the list of all objects in this world.
	 * @post 	objects no longer contains worm
	 * 			| new.objects.contains(worm) == false;
	 */
	@Raw 
	public void deleteWorm(Worm worm){
		((List<Object>) objects).remove(worm);
	}
	
	//List of Food
	/**
	 * Return a list of all the objects that are food of this world.
	 * @post 	The size of the resulting list is smaller than or equal to the number of
	 *         	objects in this world.
	 *       	| result.size() <= getNbObjects()
	 * @return 	Each object in the resulting list is food.
	 *       	| for each index in 0..result.size()-1 :
	 *       	|   result.get(index) instanceof Food = true
	 *       
	 */
	@Raw
	public Collection<Food> getFood() {
		ArrayList<Object> lijst = (ArrayList<Object>) objects;
		Collection<Food> food = new ArrayList<Food>();
		
		for (int i = 0; i < lijst.size(); i++) {
			if (lijst.get(i) instanceof Food)
				food.add((Food) lijst.get(i));
		}
		return food;
	}
	/**
	 * Removes the given food.
	 * @post 	objects no longer contains the food
	 * 			| new.objects.contains(food) == false;
	 */
	@Raw
	public void deleteFood(Food food){
		((List<Object>) objects).remove(food);
	}
	
	//List of Teams
	/**
	 * Return a list of all the objects that are teams in this world.
	 * @post The size of the resulting list is smaller than or equal to the number of
	 *         objects in this world.
	 *       | result.size() <= getNbObjects()
	 * @return Each object in the resulting list is a team.
	 *       | for each index in 0..result.size()-1 :
	 *       |   result.get(index) instanceof Team == true      
	 */	
	@Raw
	public Collection<Team> getTeams() {
		ArrayList<Object> lijst = (ArrayList<Object>) objects;
		Collection<Team> teams = new ArrayList<Team>();
		
		for (int i = 0; i < lijst.size(); i++) {
			if (lijst.get(i) instanceof Team)
				teams.add((Team) lijst.get(i));
		}
		return teams;
	}
	/**
	 * Checks whether a new team can be added.
	 * @param 	teamName
	 * 			The name of the team that is being checked.
	 * @throws 	IllegalArgumentException
	 * 			If the name isn't a valid name
	 * 			|!isValidTeamname(teamName)
	 * @throws 	IllegalStateException
	 * 			If The world already contains 10 teams 
	 * 			which is the maximum allowed number of teams per world.
	 * 			|!(this.getTeams().size()<10)
	 */
	@Raw
	public boolean canAddAsTeam(String teamName) throws IllegalArgumentException, IllegalStateException {
		if (! isValidTeamName(teamName))
			throw new IllegalArgumentException();
		if (! (this.getTeams().size()<10))
			throw new IllegalStateException();
		if ((this.getTeams().size()<10) && this.isValidTeamName(teamName)){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Checks whether a given name is a valid name for a team.
	 * @param 	name
	 * @post	Returns true if the given name is a valid name for a team
	 * 			(if it starts with a capital and exists only of and at least 2 letters .)
	 * 			If the give name is not a valid name the method returns false.
	 * 			| result == match "[A-Z]{1}[a-zA-Z " ']{1,}"
	 */
	@Raw
	public boolean isValidTeamName(String name){
	    String regex = "^[A-Z]{1}[a-zA-Z]{1,}$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(name);
	    return matcher.find();
	}
	
	// List of Projectiles
	/**
	 * Return a list of all the objects that are projectiles in this world.
	 * @post The size of the resulting list is smaller than or equal to the number of
	 *         objects in this world.
	 *       | result.size() <= getNbObjects()
	 * @return Each object in the resulting list is a projectile.
	 *       | for each index in 0..result.size()-1 :
	 *       |   result.get(index) instanceof Projectile == true      
	 */	
	@Raw
	public Collection<Projectile> getProjectile() {
		ArrayList<Object> lijst = (ArrayList<Object>) objects;
		Collection<Projectile> projectile = new ArrayList<Projectile>();
		
		for (int i = 0; i < lijst.size(); i++) {
			if (lijst.get(i) instanceof Projectile)
				projectile.add((Projectile) lijst.get(i));
		}
		return projectile;
	}
	/**
	 * Removes the given projectile.
	 * @param 	projectile
	 * 			The projectile that is to be removed.
	 * @post 	objects no longer contains the projectile
	 * 			| new.objects.contains(projectile) == false;
	 */
	@Raw
	public void deleteProjectile(Projectile projectile){
		((List<Object>) objects).remove(projectile);
	}
	/**
	 * The index of the projectile that is currently being fired.
	 */
	@Basic
	private int getCurrentProjectileIndex() {
		return currentProjectileIndex;
	}
	/**
	 * Returns the the projectile that is currently being fired.
	 * (The projectile on position "getCurrentProjectileIndex" in the list of projectiles.)
	 */
	@Basic
	public Projectile getCurrentProjectile(){
		return ((ArrayList<Projectile>) getProjectile()).get(this.getCurrentProjectileIndex());
	}
	
	//Variables
	/**
	 * Variable referencing a list collecting all the objects of
	 * this world.
	 * @invar  Each object registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each object in objects:
	 *       |   ( (object != null) && (!object.isTerminated()) )
	 */
	private  List<Object> objects = new ArrayList<Object>();
	public final Random perimeter;
	private boolean[][] passableMap;
	private double width;
	private double height;
	private static double upperboundWidth;
	private static double upperboundHeight;
	private int currentWormIndex;
	private int currentProjectileIndex;
	
}
