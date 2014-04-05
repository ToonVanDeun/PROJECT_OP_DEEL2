package worms.model;
import java.util.Collection;
import java.util.Random;

import worms.model.IFacade;
import worms.model.Worm;

public  class Facade implements IFacade {
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

	@Override
	public void addEmptyTeam(World world, String newName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewFood(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewWorm(World world) {
		Worm worm = new Worm(world);
		world.addWorm(worm);
		
	}

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

	@Override
	public Food createFood(World world, double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}

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
		return new Worm(x,y,direction,radius, name);
	}

	@Override
	public void fall(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Projectile getActiveProjectile(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Worm getCurrentWorm(World world) {
		return world.getCurrentWorm();
	}

	@Override
	public Collection<Food> getFood(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHitPoints(Worm worm) {
		return worm.getHitPoints();
	}

	@Override
	public double[] getJumpStep(Projectile projectile, double t) {
		// TODO Auto-generated method stub
		return null;
	}

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

	@Override
	public int getMaxHitPoints(Worm worm) {
		return worm.getMaxHitPoints();
	}

	@Override
	public double getRadius(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRadius(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSelectedWeapon(Worm worm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTeamName(Worm worm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWinner(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Worm> getWorms(World world) {
		return world.getWorms();
	}

	@Override
	public double getX(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getX(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isActive(Food food) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActive(Projectile projectile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdjacent(World world, double x, double y, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAlive(Worm worm) {
		return worm.getIsAlive();
	}

	@Override
	public boolean isGameFinished(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isImpassable(World world, double x, double y, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

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

	@Override
	public void selectNextWeapon(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shoot(Worm worm, int yield) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startGame(World world) {
		world.startGame();
		
	}

	@Override
	public void startNextTurn(World world) {
		world.startNextTurn();
		
	}
	
}
