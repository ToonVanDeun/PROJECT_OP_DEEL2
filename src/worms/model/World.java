/**
 * 
 */
package worms.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * @author Toon
 *
 */
public class World {
	public World(double width, double height, boolean[][] passableMap, Random random) {
		System.out.println(width);
		System.out.println(height);
		this.setHeight(height);
		this.setWidth(width);
		this.setPassableMap(passableMap);
		this.perimeter = random;
		
	}
	
	//World Setup
	
	
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
	 * 			If the given width isn't a valid width (smaller then 0 or bigger then Double.MAX_VALUE),
	 * 			the method returns false.
	 */
	public boolean isValidWidth(double width){
		return ((0<= width) && (width<=Double.MAX_VALUE));
	}
	/**
	 * Sets the upper bound for the width of the world. 
	 * It must be smaller than the current width.
	 */
	public void setUpperboundWidth(double upperboundWidth) throws IllegalArgumentException {
		if ((! isValidWidth(width)) || (upperboundWidth>this.getWidth()))
			throw new IllegalArgumentException();
		this.upperboundWidth = upperboundWidth;
	}
	/**
	 * Returns the upper bound width of the world.
	 */
	public double getUpperboundWidth() {
		return this.upperboundWidth;
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
	 * 			If the given height isn't a valid height (smaller then 0 or bigger then Double.MAX_VALUE),
	 * 			the method returns false.
	 */
	public boolean isValidHeight(double height){
		return ((0<= height) && (height<=Double.MAX_VALUE));
	}	
	public void setUpperboundHeight(double upperboundHeight) throws IllegalArgumentException {
		if (! isValidHeight(height))
			throw new IllegalArgumentException();
		this.upperboundHeight = upperboundHeight;
	}
	public double getUpperboundHeight() {
		return this.upperboundHeight;
	}
	public boolean[][] getPassableMap() {
		return passableMap;
	}
	public void setPassableMap(boolean[][] passableMap) {
		int mapWidth = passableMap.length;
		int mapHeight = passableMap[1].length;
		System.out.println("mapheight "+mapHeight);
		System.out.println("mapwidth "+mapWidth);
		double worldWidth =  (this.getWidth());
		double worldHeight = (this.getHeight());
		System.out.println("worldwidth "+worldWidth);
		System.out.println("worldheight "+worldHeight);
		boolean[][] scaledPassableMap = new boolean[(int) worldWidth+1][(int) worldHeight+1];
		int x=0;
		int y=0;
		double xScale = (worldWidth / mapWidth);
		double yScale = (worldHeight / mapHeight);
		System.out.println("xscale "+xScale);
		System.out.println("yscale "+yScale);
		int i=0;
		int j=0;
		for (y=0 ; y< mapHeight ; y++){
			for (x=0 ; x< mapWidth ; x++){
				if (passableMap[x][y]) {
					for (i=0; i<(1+xScale); i++){
						for (j=0; j<(j+1+yScale);j++){
							if ((((int) (x*xScale+i))<worldWidth+1) && ((int) (y*yScale+j))<worldHeight)
								scaledPassableMap[(int) (x*xScale+i)][(int) (y*yScale+j)] = passableMap[x][y];
						}
					}
				}
			}
		}	
		this.passableMap = scaledPassableMap;	
	}
	
	
	public boolean isImpassable(double x, double y, double radius) {
		return ((! this.getPassableMap() [(int) x][(int) y] ) &&
				((!this.getPassableMap() [(int) (x+radius)][(int) y]) ||
				(!this.getPassableMap() [(int) (x-radius)][(int) y]) ||
				(!this.getPassableMap() [(int) x][(int) (y+radius)]) ||
				(!this.getPassableMap() [(int) x][(int) (y-radius)])));
	}
	
	public boolean isAjacent(double x, double y, double radius) {
		return( (this.getPassableMap() [(int) x][(int) y] ) &&
				((!this.getPassableMap() [(int) (x+0.1*radius)][(int) y]) ||
				(!this.getPassableMap() [(int) (x-0.1*radius)][(int) y]) ||
				(!this.getPassableMap() [(int) x][(int) (y+0.1*radius)]) ||
				(!this.getPassableMap() [(int) x][(int) (y-0.1*radius)])));
	}
	
	public Random getPerimeter() {
		return this.perimeter;		
	}
	
	//Worm Index and Current Worm
	
	
	private int getCurrentWormIndex() {
		return currentWormIndex;
	}

	private void setCurrentWormIndex(int currentWormIndex) {
		this.currentWormIndex = currentWormIndex;
	}

	public Worm getCurrentWorm(){
		return ((ArrayList<Worm>) getWorms()).get(this.getCurrentWormIndex());
	}

	//Start 
	
	public void startNextTurn(){
		if (getCurrentWormIndex() >= (getWorms().size()-1))
			startNextRound();
		else
			setCurrentWormIndex(getCurrentWormIndex()+1);

	}

	private void startNextRound(){

		for (Worm worm: getWorms()){
			worm.newRound();
		}
		setCurrentWormIndex(0);

	}

	public void	startGame(){
		setCurrentWormIndex(0);
	}
	
	//All objects
	
	
	/**
	 * Return the object of this world at the given index.
	 * 
	 * @param  index
	 *         The index of the owning to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of objects of this person.
	 *       | (index < 1) || (index > getNbObjects())
	 */
	@Basic
	@Raw
	public Object getObjectAt(int index) throws IndexOutOfBoundsException {
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
	 * 
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
	public boolean canHaveAsObjectAt(Object object, int index) {
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
	 * Check whether this world has the given object as one of
	 * its objects.
	 *
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
	 *  
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
	public int getIndexOfObject(Object object) {
		return objects.indexOf(object);
	}
	/**
	 * Return a list of all the objects of this world.
	 * 
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
	 * 
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
	/**
	 * Return a list of all the objects that are worms of this world.
	 * 
	 * @return The size of the resulting list is smaller than or equal to the number of
	 *         objects in this world.
	 *       | result.size() <= getNbObjects()
	 * @return Each object in the resulting list is a worm.
	 *       | for each index in 0..result-size()-1 :
	 *       |   result.get(index) == ..........
	 *       
	 */
	
	//List of Worms
	
	
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
	 * Return a list of all the objects that is food of this world.
	 * 
	 * @return The size of the resulting list is smaller than or equal to the number of
	 *         objects in this world.
	 *       | result.size() <= getNbObjects()
	 * @return Each object in the resulting list is food.
	 *       | for each index in 0..result-size()-1 :
	 *       |   result.get(index) == ..........
	 *       
	 */
	
	//List of Food
	
	
	public Collection<Food> getFood() {
		ArrayList<Object> lijst = new ArrayList<Object>(objects);
		Collection<Food> food = new ArrayList<Food>();
		
		
		for (int i = 0; i < lijst.size(); i++) {
			if (lijst.get(i) instanceof Food)
				food.add((Food) lijst.get(i));
		}
		return food;
	}





	/**
	 * Variable referencing a list collecting all the objects of
	 * this world.
	 * @invar  Each object registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each object in objects:
	 *       |   ( (object != null) && (!object.isTerminated()) )
	 */
	private  List<Object> objects = new ArrayList<Object>();
	
	private final Random perimeter;
	private boolean[][] passableMap;
	private double width;
	private double height;
	private double upperboundWidth;
	private double upperboundHeight;
	private int currentWormIndex;
	
}
