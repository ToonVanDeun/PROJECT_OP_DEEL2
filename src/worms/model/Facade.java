package worms.model;
import java.util.Collection;
import java.util.Random;


import worms.model.IFacade;
import worms.model.Worm;

public  class Facade implements IFacade {
	/**
	 * constructor for Facade.
	 */
	public Facade() {
		
	}
	/**
	 * Checks whether a given worm can turn over a given angel.
	 */
	@Override
	public boolean canTurn(Worm worm, double angle) {
		return worm.isValidTurn(angle);
	}

	/**
	 * Turns a given worm over a given angel.
	 */
	@Override
	public void turn(Worm worm, double angle) {
		worm.turn(angle);
	}

	/**
	 * Get the x- and y-position of a given worm that's jumping at a given time t,
	 *  after the jump started.
	 */
	@Override
	public double[] getJumpStep(Worm worm, double t) {
		try {
			return worm.jumpStep(t);
		} catch (IllegalStateException exc) {
			throw new ModelException("can't jump");
		}
	}

	/**
	 * Get a worm's x-position.
	 */
	@Override
	public double getX(Worm worm) {
		return worm.getXpos();
	}

	/**
	 * Get a worm's y-position.
	 */
	@Override
	public double getY(Worm worm) {
		return worm.getYpos();
	}

	/**
	 * Get a worm's orientation.
	 */
	@Override
	public double getOrientation(Worm worm) {
		return worm.getDirection();
	}

	/**
	 * Get a worms radius.
	 */
	@Override
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}

	/**
	 * Set the radius of the given worm to the given radius.
	 */
	@Override
	public void setRadius(Worm worm, double newRadius) {
		try {
			worm.setRadius(newRadius);
		} catch (IllegalArgumentException exc) {
			throw new ModelException("not a valid radius");
		}
	}

	/**
	 * Get the minimal allowed radius of a given worm.
	 */
	@Override
	public double getMinimalRadius(Worm worm) {
		return 0.25;
	}

	/**
	 * Get the amount of actionpoints of a given worm.
	 */
	@Override
	public int getActionPoints(Worm worm) {
		return worm.getActionPoints();
	}

	/**
	 * Get the maximum allowed amount of actionpoints of a given worm.
	 */
	@Override
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxActionPoints();
	}

	/**
	 * Get the name of a given worm.
	 */
	@Override
	public String getName(Worm worm) {
		return worm.getName();
	}

	/**
	 * Give a worm a new name.
	 */
	@Override
	public void rename(Worm worm, String newName) {
		try {
			worm.setName(newName);
		} catch (IllegalArgumentException exc) {
			throw new ModelException("that name is not valid");
		}
	}

	/**
	 * Get the mass of a given worm.
	 */
	@Override
	public double getMass(Worm worm) {
		return worm.getMass();
	}
	/**
	 * Adds a new empty team wroms van join.
	 */
	@Override
	public void addEmptyTeam(World world, String newName) {
		try {
			new Team(world, newName);
		} catch (IllegalArgumentException exc) {
			throw new ModelException("that name is not valid");
		}
	}
	/**
	 * Adds a food object to the world.
	 */
	@Override
	public void addNewFood(World world) {
		new Food(world);
	}
	/**
	 * Adds a new worm object to the world.
	 */
	@Override
	public void addNewWorm(World world) {
		new Worm(world);
		
	}
	/**
	 * Checks whether or not a worm can fall
	 */
	@Override
	public boolean canFall(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Checks whether a given worm can move over a given number of steps.
	 */
	@Override
	public boolean canMove(Worm worm) {
		return worm.isValidStep();
	}
	/**
	 * Creates a new Food Object in the world with a given position.
	 */
	@Override
	public Food createFood(World world, double x, double y) {
		return new Food(world, x, y);
	}
	/**
	 * Creates a new world with the given variables.
	 */
	@Override
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		return new World(width, height, passableMap, random);
	}
	/**
	 * Creates a worm with a given x-and y-position, direction, radius and name. 
	 * Other values also get initialized.
	 */
	@Override
	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name) {
		return new Worm(world,x,y,direction,radius, name);
		//return null;
	}
	/**
	 * Makes a certain worm fall.
	 */
	@Override
	public void fall(Worm worm) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Returns which projectile is active in the world.
	 */
	@Override
	public Projectile getActiveProjectile(World world) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Returns which worm is active in the world.
	 */
	@Override
	public Worm getCurrentWorm(World world) {
		return world.getCurrentWorm();
	}
	/**
	 * Returns the list of all food objects in the world.
	 */
	@Override
	public Collection<Food> getFood(World world) {
		return world.getFood();
	}
	/**
	 * Returns the hitpoints of a worm.
	 */
	@Override
	public int getHitPoints(Worm worm) {
		return worm.getHitPoints();
	}
	/**
	 *	Get the x- and y-position of a given projectile that's jumping at a given time t,
	 *  after the jump started. 
	 */
	@Override
	public double[] getJumpStep(Projectile projectile, double t) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Get the time it takes the given projectile to jump.
	 */
	@Override
	public double getJumpTime(Projectile projectile, double timeStep) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Get the time it takes the given worm to jump.
	 */
	@Override
	public double getJumpTime(Worm worm, double timeStep) {
		try {
			return worm.jumpTime();
		} catch (IllegalStateException exc) {
			throw new ModelException("can't jump");
		}
	}
	/**
	 * Returns the maximum hit points of a worm.
	 */
	@Override
	public int getMaxHitPoints(Worm worm) {
		return worm.getMaxHitPoints();
	}
	/**
	 * Returns the radius of a food object.
	 */
	@Override
	public double getRadius(Food food) {
		return food.getRadius();
	}
	/**
	 * Returns the radius of a projectile object.
	 */
	@Override
	public double getRadius(Projectile projectile) {
		return projectile.getRadius();
	}
	/**
	 * Returns the selected weapon.
	 */
	@Override
	public String getSelectedWeapon(Worm worm) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * returns the name of a team a worm is in.
	 */
	@Override
	public String getTeamName(Worm worm) {
		return worm.getTeamName();
	}
	/**
	 * Returns the winner of the game.
	 */
	@Override
	public String getWinner(World world) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Returns the list of all worm objects in the world.
	 */
	@Override
	public Collection<Worm> getWorms(World world) {
		return world.getWorms();
	}
	/**
	 * Gets the food's x-position.
	 */
	@Override
	public double getX(Food food) {
		return food.getXpos();
	}
	/**
	 * Gets the projectile's x-position.
	 */
	@Override
	public double getX(Projectile projectile) {
		return projectile.getXpos();
	}
	/**
	 * Gets the food's y-position.
	 */
	@Override
	public double getY(Food food) {
		return food.getYpos();
	}
	/**
	 * Gets the projectile's y-position.
	 */
	@Override
	public double getY(Projectile projectile) {
		return projectile.getYpos();
	}
	/**
	 * Returns whether a food object is active.
	 */
	@Override
	public boolean isActive(Food food) {
		return true;
	}
	/**
	 * Returns whether a projectile object is active.
	 */
	@Override
	public boolean isActive(Projectile projectile) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Returns a given position with a given radius is adjacent for a given world.
	 */
	@Override
	public boolean isAdjacent(World world, double x, double y, double radius) {
		return world.isAdjacent(x, y, radius);
	}
	/**
	 * Returns whether a worm is alive.
	 */
	@Override
	public boolean isAlive(Worm worm) {
		return worm.getIsAlive();
	}
	/**
	 * Returns whether a game is finished or not.
	 */
	@Override
	public boolean isGameFinished(World world) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Returns a given position with a given radius is impassable for a given world.
	 */
	@Override
	public boolean isImpassable(World world, double x, double y, double radius) {
		return world.isImpassable(x, y, radius);
	}
	/**
	 * Makes a given projectile jump.
	 */
	@Override
	public void jump(Projectile projectile, double timeStep) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Makes a given worm jump.
	 */
	@Override
	public void jump(Worm worm, double timeStep) {
		try {
			worm.jump();	
		} catch (IllegalStateException exc) {
			throw new ModelException("can't jump");
		}
		
	}
	/**
	 * Makes a given worm move (in the current direction).
	 */
	@Override
	public void move(Worm worm) {
		try{
			worm.move();
		} catch (IllegalArgumentException exc) {
			throw new ModelException("not allowed to move");
		}
	}
	/**
	 * Makes a worm select the next weapon available.
	 */
	@Override
	public void selectNextWeapon(Worm worm) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Makes a worm shoot with a given yield.
	 */
	@Override
	public void shoot(Worm worm, int yield) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Makes the game start in a given world.
	 */
	@Override
	public void startGame(World world) {
		world.startGame();
		
	}
	/**
	 * Makes the next turn start in a given world.
	 */
	@Override
	public void startNextTurn(World world) {
		world.startNextTurn();
		
	}
	
}
