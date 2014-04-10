package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * 
 * A class of objects that can be in a world of class World. 
 * 
 * @invar  Each object can only be located in at most one world.
 *       | 
 * @invar  No world contains te same object twice.
 *       | 
 *       
 * @author Toon
 */
public abstract class Object {

	/**
	 * Initialize this new object with given world.
	 *
	 * @param  world
	 *         The world the object is being part of.
	 * @effect The world of this new object is set to the given
	 *         world.
	 *       | setOwnerTo(owner) !!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	@Raw
	@Model
	protected Object(World world) throws IllegalArgumentException {
		setWorldTo(world);
	}
	
	/**
	 * Return the world of this object.
	 *   A null reference is returned if this object is not in a world.
	 */
	@Basic
	@Raw
	public World getWorld() {
		return this.world;
	}

	/**
	 * Check whether this object can have the given world
	 * as its world.
	 *
	 * @param  world
	 *         The world to check.
	 * @return True, if the object is not yet in a world.
	 * 			| ...
	 */
	@Raw
	public boolean canHaveAsWorld(World world) {
		// nog niet juis controleert enkel of object niet in gegeven wereld zit.
		// moet ook controleren of het niet in een andere wereld zit.
		if (world.getAllObjects().contains(this))
			return false;
		return true;
		
	}

	

	/**
	 * Check whether this object is in a world.
	 *
	 * @return True if and only if the world of this object is effective.
	 *       | result == (getWorld() != null)
	 */
	@Raw
	public boolean hasWorld() {
		return getWorld() != null;
	}

	/**
	 * Set the world of this object to the given world.
	 *
	 * @param  world
	 *         The new world for this object.
	 * @post   The world of this object is the same as the given world.
	 *       | new.getWorld() == world
	 * @post   The number of objects of the given world
	 *         is incremented by 1.
	 *       | (new world).getNbOwnings() == world.getNbOwnings() + 1
	 * @post   The given world has this objects as its new last
	 *         object.
	 *       | (new world).getObjectAt(getNbObjects()+1) == this
	 * @throws IllegalArgumentException
	 *         The given world is not effective or it cannot have this object
	 *         as its new last object.
	 *       | (world == null) ||
	 *       |   (! world.canHaveAsObjectAt(this,world.getNbObjects()+1))
	 * @throws IllegalStateException
	 *         This object already belongs to a world.
	 *       | hasWorld()
	 */	
	public void setWorldTo(World world)
			throws IllegalArgumentException, IllegalStateException {
		if ((world == null)
				|| (!world.canHaveAsObjectAt(this, world.getNbObjects()+1)))
			throw new IllegalArgumentException();
		if (this.hasWorld())
			throw new IllegalStateException("Already belongs to a world!");
		setWorld(world);
		world.addAsObject(this);
	}

	/**
	 * Unset the world, if any, from this object.
	 *
	 * @post   This object no longer belongs to a world.
	 *       | ! new.hasWorld()
	 * @post   The former world of this object, if any, no longer
	 *         has this object as one of its objects.
	 *       |    (getWorld() == null)
	 *       | || (! (new getWorld()).hasAsObject(object))
	 * @post   All objects registered beyond the position at which
	 *         this owning was registered shift one position to the left.
	 *       | (getWorld() == null) ||
	 *       | (for each index in
	 *       |        getWorld().getIndexOfObject(object)+1..getWorld().getNbObjects():
	 *       |    (new getWorld()).getObjectAt(index-1) == getWorld().getObjectAt(index) ) 
	 */
	public void unsetWorld() {
		if (hasWorld()) {
			World formerWorld = this.getWorld();
			setWorld(null);
			formerWorld.removeAsObject(this);
		}
	}

	public double getRadius() {
		return this.radius;
	}	
	
	/**
	 * Set the owner of this ownable to the given owner.
	 *
	 * @param  owner
	 *         The new owner for this ownable.
	 * @pre    This ownable can have the given owner as its owner.
	 *       | canHaveAsOwner(owner)
	 * @post   The owner of this ownable is the same as the given owner.
	 *       | new.getOwner() == owner
	 */
	@Raw
	private void setWorld(@Raw World world) {
		assert canHaveAsWorld(world);
		this.world = world;
	}

	/**
	 * Variable referencing the world of this object.
	 */
	private World world;
		
	double radius;
}
