package worms.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of objects that can be in a world of class World. 
 * Classes Worm, Food, Team and Projectile all inherit from Object.
 * 
 * @author 	Toon Stuyck
 * 			Toon Van Deun
 * 			Burgerlijk Ingenieur
 * 			https://github.com/ToonVanDeun/PROJECT_OP_DEEL2
 * @version 2.0
 */
@Value
public abstract class Object {
	/**
	 * Initialize this new object with given world.
	 * @param  world
	 *         The world the object is being part of.
	 * @effect The world of this new object is set to the given
	 *         world.
	 *       | setWorldTo(world)
	 */
	@Model
	protected Object(World world) throws IllegalArgumentException {
		setWorldTo(world);
	}
	/**
	 * Return the world of this object.
	 *   A null reference is returned if this object is not in a world.
	 */
	@Basic
	public World getWorld() {
		return this.world;
	}
	/**
	 * Check whether this object can have the given world
	 * as its world.
	 * @param  world
	 *         The world to check.
	 * @return True, if the object is not yet in a world.
	 * 			| !world.getAllObjects().contains(this)
	 */
	public boolean canHaveAsWorld(World world) {
		// nog niet juis controleert enkel of object niet in gegeven wereld zit.
		// moet ook controleren of het niet in een andere wereld zit.
		if (world.getAllObjects().contains(this))
			return false;
		return true;
	}
	/**
	 * Check whether this object is in a world.
	 * @return True if and only if the world of this object is effective.
	 *       | result == (getWorld() != null)
	 */
	public boolean hasWorld() {
		return getWorld() != null;
	}
	/**
	 * Set the world of this object to the given world.
	 * @param  	world
	 *         	The new world for this object.
	 * @post   	The world of this object is the same as the given world.
	 *       	| new.getWorld() == world
	 * @post   	The number of objects of the given world
	 *         	is incremented by 1.
	 *       	| (new world).getNbObjects() == world.getNbObjects() + 1
	 * @post   	The given world has this objects as its new last
	 *         	object.
	 *       	| (new world).getObjectAt(getNbObjects()+1) == this
	 * @throws 	IllegalArgumentException
	 *         	The given world is not effective.
	 *       	| (world == null)
	 * @throws 	IllegalStateException
	 *         	This object already belongs to a world.
	 *       	| hasWorld()
	 */	
	public void setWorldTo(World world)
			throws IllegalArgumentException, IllegalStateException {
		if ((world == null))
			throw new IllegalArgumentException();
		if (this.hasWorld())
			throw new IllegalStateException("Already belongs to a world!");
		setWorld(world);
		world.addAsObject(this);
	}
	/**
	 * Set the World of this object to the given world.
	 * @param 	world
	 *         	The new world for this object.
	 * @pre    	This object can have the given world as its world.
	 *       	| this.canHaveAsWorld(world)
	 * @post   	The world of this object is the same as the given world.
	 *       	| new.getWorld() == world
	 */
	private void setWorld(@Raw World world) {
		assert this.canHaveAsWorld(world);
		this.world = world;
	}
	/**
	 * Unset the world, if any, from this object.
	 * @post   	This object no longer belongs to a world.
	 *       	| ! new.hasWorld()
	 * @post   	The former world of this object, if any, no longer
	 *         	has this object as one of its objects.
	 *       	|    (getWorld() == null)
	 *       	| || (! (new getWorld()).hasAsObject(object))
	 * @post   	All objects registered beyond the position at which
	 *         	this owning was registered shift one position to the left.
	 *       	| (getWorld() == null) ||
	 *       	| (for each index in
	 *       	|        getWorld().getIndexOfObject(object)+1..getWorld().getNbObjects():
	 *       	|    (new getWorld()).getObjectAt(index-1) == getWorld().getObjectAt(index) ) 
	 */
	public void unsetWorld() {
		if (hasWorld()) {
			World formerWorld = this.getWorld();
			this.world = null;
			formerWorld.removeAsObject(this);
		}
	}
	/**
	 * Returns the radius of the object.
	 */
	@Basic 
	public double getRadius() {
		return this.radius;
	}
	/**
	 * Returns the X position of the object
	 */
	@Basic 
	public double getXpos() {
		return position.getXpos();
	}
	/**
	 * Returns the Y position of the object
	 */
	@Basic 
	public double getYpos(){
		return position.getYpos();
	}
	
	//Variables
	/**
	 * Variable referencing the world of this object.
	 */
	private World world;
	/**
	 * Variable referencing the position of this object.
	 */
	private Position position;
	double radius;
}
