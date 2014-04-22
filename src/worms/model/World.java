/**
 * 
 */
package worms.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * @author Toon
 *
 */
public class World {
	public World(double width, double height, boolean[][] passableMap, Random random) {
		this.setUpperboundHeight(height);
		this.setUpperboundWidth(width);
		this.setHeight(height);
		this.setWidth(width);
		this.passableMap = passableMap;
		this.perimeter = random;
	}
	
	//init
	/**
	 * Returns the perimeter, a random object to determine several random aspects of the world.
	 */
	public Random getPerimeter() {
		return this.perimeter;		
	}
	
	//Width and Hight
	/**
	 * Returns the width of the world.
	 */
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
	 */
	private boolean isValidWidth(double width){
		return ((0<= width) && (width<=Double.MAX_VALUE) && (width<=this.getUpperboundWidth()));
	}
	/**
	 * Returns the upper bound width of the world.
	 */
	public double getUpperboundWidth() {
		return this.upperboundWidth;
	}
	/**
	 * Sets the upper bound for the width of the world. 
	 * @param 	The new upperboundWidth for the world.
	 * @post 	The upperboundWidth of the world is set to the given upperboundWidth.
	 * 			|new.getUpperboundWidth() ==uperboundWidth
	 * @throws IllegalArgumentException
	 * 			if the upperboundWidth is not a valid width.
	 */
	public void setUpperboundWidth(double upperboundWidth) throws IllegalArgumentException {
		if ( upperboundWidth<this.getWidth())
			throw new IllegalArgumentException();
		this.upperboundWidth = upperboundWidth;
	}
	/**
	 * Returns the height of the world.
	 */
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
	 */
	private boolean isValidHeight(double height){
		return ((0<= height) && (height<=Double.MAX_VALUE) && (height<=this.getUpperboundHeight()));
	}
	/**
	 * Returns the upper bound height of the world.
	 */
	public double getUpperboundHeight() {
		return this.upperboundHeight;
	}
	/**
	 * Sets the upper bound for the height of the world.
	 * @param 	The new upperboundHeight for the world.
	 * @post 	The upperboundHeight of the world is set to the given upperboundHeight.
	 * 			|new.getUpperboundHeight() ==uperboundHeight
	 * @throws IllegalArgumentException
	 * 			if the upperboundHeight is not a valid height.
	 */
	public void setUpperboundHeight(double upperboundHeight) throws IllegalArgumentException {
		if (upperboundHeight<this.getHeight())
			throw new IllegalArgumentException();
		this.upperboundHeight = upperboundHeight;
	}
	
	//Passable map
	/**
	 * Returns the passable map of the world.
	 */
	public boolean[][] getPassableMap() {
		return this.passableMap;
	}
	/**
	 * Determines whether an object with given x position, given y position and given radius, 
	 * is placed in or overlaps with impassable terrain.
	 * @param x	The given x position
	 * @param y	The given y position
	 * @param radius	The given radius
	 * @return True if the object is positioned in a impassable location
	 * 			False if the object isn't positioned in impassable terrain.
	 */
	public boolean isImpassable2(double x, double y, double radius) {
		int mapWidth = this.getPassableMap()[1].length; //eigenlijk height
		int mapHeight = this.getPassableMap().length; //eigenlijk width
		//schaalfactoren waarmee coordinaten uit world vermenigvuldigd zullen worden om ze in passableMap te hebben.
		double xScale = (mapWidth/this.getWidth()); //schaalfactor voor een x coordinaat van world
		double yScale = (mapHeight/this.getHeight());//schaalfactor voor een y coordinaat van world
		
		return ((! this.getPassableMap() [(int) Math.round((this.getHeight()-y)*yScale)][(int) Math.round(x*xScale)] ));
	}
	public boolean isImpassable(double x, double y, double radius) {		
		return !this.isPassable(x, y, radius);
	}
	/**
	 * Checks whether an objects with given x position, given y position and given radius, 
	 * is placed in passable terrain and doesn't overlap with impassable terrain.
	 * @param x	The given x position
	 * @param y	The given y position
	 * @param radius	The given radius
	 * @return	True if the object isn't placed in impassable terrain.
	 * 			False if the object is placed in impassable terrain
	 * 			| !this.isImpassable(x,y,radius)
	 */
	public boolean isPassable2(double x, double y, double radius) {
		return !isImpassable(x,y,radius);
	}
	
	
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
	 * Checks whether an objects with given x position, given y position and given radius, 
	 * is placed in adjacent terrain.
	 * Adjacent means: Passable and in an x or y direction there is impassable terrain 
	 * less than 0.1*radius from the objects' radius.
	 * @param x	The given x position
	 * @param y	The given y position
	 * @param radius	The given radius
	 * @return True if the object is placed on adjacent terrain
	 * 			False if the object isn't placed on adjacent terrain
	 */
	public boolean isAdjacent2(double x, double y, double radius) {
		int mapWidth = this.getPassableMap()[1].length; //eigenlijk height
		int mapHeight = this.getPassableMap().length; //eigenlijk width
		//schaalfactoren waarmee coordinaten uit world vermenigvuldigd zullen worden om ze in passableMap te hebben.
		double xScale = (mapWidth/this.getWidth()); //schaalfactor voor een x coordinaat van world
		double yScale = (mapHeight/this.getHeight());//schaalfactor voor een y coordinaat van world
		
		if (((x-1.1*radius)<0) || ((x+1.1*radius)>this.getWidth()) || ((y-1.1*radius)<0) || ((y+1.1*radius)>this.getHeight())) {
			return false;
		}
		return ((this.getPassableMap() [(int) Math.round((this.getHeight()-y)*yScale)][(int) Math.round(x*xScale)] ) &&
					((!this.getPassableMap() [(int) Math.floor((this.getHeight()-y+(1.1*radius))*yScale)][(int) Math.round(x*xScale)] ) &&
						(this.getPassableMap() [(int) Math.floor((this.getHeight()-y+radius)*yScale)][(int) Math.round(x*xScale)] )) ||
					((!this.getPassableMap() [(int) Math.round((this.getHeight()-y-(1.1*radius))*yScale)][(int) Math.round(x*xScale)] ) &&
						(this.getPassableMap() [(int) Math.floor((this.getHeight()-y-radius)*yScale)][(int) Math.round(x*xScale)] )) ||
					((!this.getPassableMap() [(int) Math.round((this.getHeight()-y)*yScale)][(int) Math.floor((x+(1.1*radius))*xScale)] ) &&
						(this.getPassableMap() [(int) Math.round((this.getHeight()-y)*yScale)][(int) Math.floor((x+radius)*xScale)] ))||
					((!this.getPassableMap() [(int) Math.round((this.getHeight()-y)*yScale)][(int) Math.round((x-(1.1*radius))*xScale)] ) &&
						(this.getPassableMap() [(int) Math.round((this.getHeight()-y)*yScale)][(int) Math.round((x-radius)*xScale)] )));
	}
	
	public boolean isAdjacent3(double x, double y ,double radius){
		if (this.isPassable(x, y, radius)){
			
			double maxDistance = radius*1.1;
			int mapWidth = this.getPassableMap()[1].length; //eigenlijk height
			int mapHeight = this.getPassableMap().length; //eigenlijk width
			double xScale = (mapWidth/this.getWidth()); //schaalfactor voor een x coordinaat van world
			double yScale = (mapHeight/this.getHeight());//schaalfactor voor een y coordinaat van world
			if (((x-maxDistance)<0) 	|| (((x+maxDistance)>this.getWidth()) || ((y-maxDistance)<0) || ((y+maxDistance)>this.getHeight()))) {
				return false;
			}
			for (double dir=0;dir<2*Math.PI;dir+=0.3) {
				if (this.getPassableMap()[(int) Math.floor((this.getHeight()-(y+radius*Math.sin(dir)))*yScale)][(int) Math.floor((x+radius*Math.cos(dir))*xScale)] &&
						!this.getPassableMap()[(int) Math.floor((this.getHeight()-(y+maxDistance*Math.sin(dir)))*yScale)][(int) Math.floor((x+maxDistance*Math.cos(dir))*xScale)]) {
					return true;
				}
			}
				

		}
		return false;
	}
	public boolean isAdjacent(double x, double y ,double radius){
		if ((this.isPassable(x, y, radius)) && (this.isImpassable(x, y, radius*1.1))){
			return true;
		}
		return false;
	}
	
	//Worm Index and Current Worm
	/**
	 * 
	 * @return
	 */
	private int getCurrentWormIndex() {
		return currentWormIndex;
	}
	/**
	 * Set the currentWormIndex to currentWormIndex.
	 * @param currentWormIndex	The new currentWormIndex.
	 * @pre	The current worm index must be a valid index
	 * 		| currentWormIndex>=0
	 * @post The currentWormIndext is set to the given currentWormIndex
	 * 		|new.getCurrentWormIndex() == currentWormIndex
	 */
	private void setCurrentWormIndex(int currentWormIndex) {
		assert currentWormIndex >=0;
		this.currentWormIndex = currentWormIndex;
	}
	/**
	 * Returns the the worm on position "getCurrentWormIndex" in the list of worms.
	 */
	public Worm getCurrentWorm(){
		return ((ArrayList<Worm>) getWorms()).get(this.getCurrentWormIndex());
	}

	//Start and turns
	/**
	 * Starts the game.
	 * (By setting the currentWormINdext to zero.)
	 */
	public void	startGame(){
		setCurrentWormIndex(0);
	}
	/**
	 * Cycles through all worms turn by turn.
	 * When all worms have had a turn a new round is started.
	 */
	public void startNextTurn(){
		if (getCurrentWormIndex() >= (getWorms().size()-1))
			startNextRound();
		else
			setCurrentWormIndex(getCurrentWormIndex()+1);
	}
	/**
	 * Starts a new round.
	 */
	private void startNextRound(){
		for (Worm worm: getWorms()){
			worm.newRound();
		}
		setCurrentWormIndex(0);
	}
	
	//All objects
	/**
	 * Return the object of this world at the given index.
	 * @param  index
	 *         The index of the owning to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of objects of this person.
	 *       | (index < 1) || (index > getNbObjects())
	 */
	@Basic
	@Raw
	private Object getObjectAt(int index) throws IndexOutOfBoundsException {
		return objects.get(index - 1);
	}
	/**
	 * Return the number of objects in this world.
	 */
	@Basic
	@Raw
	public int getNbObjects() {
		return objects.size();
	}
	/**
	 * Check whether this world can have the given object
	 * as one of its objects.
	 * 
	 * @param  object
	 *         The object to check.
	 * @return True if and only if the given object is effective, and
	 *         if that object can have this world as its world.
	 *       | result ==
	 *       |   (object != null) &&
	 *       |   object.canHaveAsWorld(this)
	 */
	@Raw
	public boolean canHaveAsObject(Object object) {
		return (object != null) && object.canHaveAsWorld(this);
	}
	/**
	 * Check whether this world can have the given object
	 * as one of its objects at the given index.
	 * @param  object
	 *         The object to check.
	 * @param  index
	 *         The index to check.
	 * @return False if the given index is not positive or exceeds
	 *         the number of objects of this world + 1.
	 *       | if ( (index < 1) || (index > getNbObjects()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this world cannot have the
	 *         given object as one of its objects.
	 *       | else if (! canHaveAsObject(object))
	 *       |   then result == false
	 *         Otherwise, true if and only if the given object is
	 *         not already registered at another index.
	 *       | else result ==
	 *       |   for each I in 1..getNbObjects():
	 *       |     ( (I == index) || (getObjectAt(I) != object) )
	 */
	@Raw
	private boolean canHaveAsObjectAt(Object object, int index) {
		if ((index < 1) || (index > getNbObjects() + 1))
			return false;
		if (!canHaveAsObject(object))
			return false;
		for (int pos = 1; pos <= getNbObjects(); pos++)
			if ((pos != index) && (getObjectAt(pos) == object))
				return false;
		return true;
	}
	
	
	
	/**
	 * Checks whether a given name is a valid name.
	 * @param name
	 * @post	Returns true if the given name is a valid name
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
	/**
	 * Check whether this world has the given object as one of
	 * its objects.
	 * @param  object
	 *         The object to check.
	 * @return True if and only if this world has the given object
	 *         as one of its objects at some index.
	 *       | result ==
	 *       |   for some index in 1..getNbObjects():
	 *       |     getObjectAt(index).equals(object)
	 */
	@Raw
	public boolean hasAsObject(Object object) {
		return objects.contains(object);
	}
	/**
	 * Return the index at which the given object is registered
	 * in the list of objects for this world.
	 * @param  object
	 *         The object to search for.
	 * @return If this world has the given object as one of its
	 *         objects, that object is registered at the resulting
	 *         index. Otherwise, the resulting value is -1.
	 *       | if (hasAsObject(object))
	 *       |    then getObjectAt(result) == object
	 *       |    else result == -1
	 */
	@Raw
	private int getIndexOfObject(Object object) {
		return objects.indexOf(object);
	}
	/**
	 * Return a list of all the objects of this world.
	 * @return The size of the resulting list is equal to the number of
	 *         objects in this world.
	 *       | result.size() == getNbObjects()
	 * @return Each element in the resulting list is the same as the
	 *         objects of this world at the corresponding index.
	 *       | for each index in 0..result-size()-1 :
	 *       |   result.get(index) == getObjectAt(index+1)
	 */
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
	 *       | (world != null) && (object.getWorld() == this)
	 * @pre    This world does not yet have the given object
	 *         as one of its objects.
	 *       | ! hasAsObject(object)
	 * @post   The number of objects of this world is incremented
	 *         by 1.
	 *       | new.getNbObjects() == getNbObjects() + 1
	 * @post   This world has the given object as its new last
	 *         object.
	 *       | new.getObjectAt(getNbObjects()+1) == object
	 */
	public void addAsObject(@Raw Object object) {
		assert (object != null) && (object.getWorld() == this);
		assert !hasAsObject(object);
		objects.add(object);
	}
	/**
	 * Remove the given object from the objects of this world.
	 * 
	 * @param  object
	 *         The object to be removed.
	 * @pre    The given object is effective and does not have any
	 *         world.
	 *       | (object != null) && (object.getWorld() == null)
	 * @pre    This world has the given object as one of
	 *         its objects.
	 *       | hasAsObject(object)
	 * @post   The number of objects of this world is decremented
	 *         by 1.
	 *       | new.getNbObjects() == getNbObjects() - 1
	 * @post   This world no longer has the given object as
	 *         one of its objects.
	 *       | (! new.hasAsObject(object))
	 * @post   All objects registered beyond the removed object
	 *         shift one position to the left.
	 *       | for each index in getIndexOfObject(object)+1..getNbObjects():
	 *       |   new.getObjectAt(index-1) == getObjectAt(index) 
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
	 * @post the size of the collection must be equal to or less than het size of all the objects in the world.
	 * 			| result.size()<=this.objects.size()
	 * @post the collection only contains objects of the class Worm
	 * 			| for each index in 0..result.size()-1:
	 *      	|   (result.get(i) instanceof Worm) == true
	 */
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
	 */
	public void deleteWorm(){
		((List<Object>) objects).remove(this.getCurrentWorm());
	}
	
	//List of Food
	/**
	 * Return a list of all the objects that is food of this world.
	 * @post The size of the resulting list is smaller than or equal to the number of
	 *         objects in this world.
	 *       | result.size() <= getNbObjects()
	 * @return Each object in the resulting list is food.
	 *       | for each index in 0..result.size()-1 :
	 *       |   result.get(index) instanceof Food = true
	 *       
	 */
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
	 */
	public void deleteFood(Food food){
		((List<Object>) objects).remove(food);
	}
	
	//List of Teams
	/**
	 * Return a list of all the objects that is a team in this world.
	 * @post The size of the resulting list is smaller than or equal to the number of
	 *         objects in this world.
	 *       | result.size() <= getNbObjects()
	 * @return Each object in the resulting list is a team.
	 *       | for each index in 0..result.size()-1 :
	 *       |   result.get(index) instanceof Team == true      
	 */	
	public Collection<Team> getTeams() {
		ArrayList<Object> lijst = (ArrayList<Object>) objects;
		Collection<Team> teams = new ArrayList<Team>();
		
		for (int i = 0; i < lijst.size(); i++) {
			if (lijst.get(i) instanceof Team)
				teams.add((Team) lijst.get(i));
		}
		return teams;
	}
	
	// List of Projectiles
	
	
	public Collection<Projectile> getProjectile() {
		ArrayList<Object> lijst = (ArrayList<Object>) objects;
		Collection<Projectile> projectile = new ArrayList<Projectile>();
		
		for (int i = 0; i < lijst.size(); i++) {
			if (lijst.get(i) instanceof Projectile)
				projectile.add((Projectile) lijst.get(i));
		}
		return projectile;
	}
	public void deleteProjectile(Projectile projectile){
		((List<Object>) objects).remove(projectile);
	}
	private int getCurrentProjectileIndex() {
		return currentProjectileIndex;
	}
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
	private double upperboundWidth;
	private double upperboundHeight;
	private int currentWormIndex;
	private int currentProjectileIndex;
	
}
