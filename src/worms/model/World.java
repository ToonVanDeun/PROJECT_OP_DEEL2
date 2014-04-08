/**
 * 
 */
package worms.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * @author Toon
 *
 */
public class World {
	public World(double width, double height, boolean[][] passableMap, Random random) {
		this.setHeight(height);
		this.setWidth(width);
		this.setPassableMap(passableMap);
		this.perimeter = random;
		
	}
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
		this.passableMap = passableMap;
	}
	public Random getPerimeter() {
		return this.perimeter;		
	}
	public void addWorm(Worm worm){
		worms.add(worm);
	}
	public void removeWorm(Worm worm) {
		worms.remove(worm);
	}
	public ArrayList<Worm> getWorms(){
		return worms;
	}
	
	
	private int getCurrentWormIndex() {
		return currentWormIndex;
	}

	private void setCurrentWormIndex(int currentWormIndex) {
		this.currentWormIndex = currentWormIndex;
	}

	public Worm getCurrentWorm(){
		return this.worms.get(this.getCurrentWormIndex());
	}

	public void startNextTurn(){
		if (getCurrentWormIndex() >= (worms.size()-1))
			startNextRound();
		else
			setCurrentWormIndex(getCurrentWormIndex()+1);

	}

	private void startNextRound(){

		for (Worm worm: worms){
			worm.newRound();
		}
		setCurrentWormIndex(0);

	}

	public void	startGame(){
		setCurrentWormIndex(0);
	}

	public void addFood(Food food) {
			foods.add(food);
		}
		public ArrayList<Food> getFood() {
			return foods;
		}
		public void removeFood(Food food) {
			foods.remove(food);
		}
		
	private ArrayList<Food> foods = new ArrayList<Food>();
	private ArrayList<Worm> worms = new ArrayList<Worm>();
	private int currentWormIndex;
	private final Random perimeter;
	private boolean[][] passableMap;
	private double width;
	private double height;
	private double upperboundWidth;
	private double upperboundHeight;
}
