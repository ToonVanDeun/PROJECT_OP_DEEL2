/**
 * 
 */
package worms.model;

/**
 * @author Toon
 *
 */
public class Projectile extends Object{

	public Projectile(World world,Worm worm, int mass) {
		super(world);
		this.position = new Position(world,this);
		this.setDirection(worm);
		this.setMass();
	}
	
	/**
	 * Sets the x-position of the projectile.
	 * @param xpos
	 * 			The (new) x-position of the projectile
	 * @post	the given x-position is the new x-position of the projectile.
	 * 			| new.getXpos() == xpos
	 * @throws	IllegalArgumentException
	 * 			If xpos isn't a valid x-position the exception is thrown.
	 * 			| ! position.isValidPos(xpos)
	 */
	public void setXpos(Worm worm){
		this.xpos = worm.getXpos()+(worm.getRadius()*Math.cos(this.direction));
		position.setXpos(xpos);
	}
	/**
	 * Returns the x-position of the projectile.
	 */
	public double getXpos(){
		return position.getXpos();
	}
	/**
	 * Sets the y-position of the projectile.
	 * @param ypos
	 * 			The (new) y-position of the projectile
	 * @post	the given y-position is the new y-position of the projectile.
	 * 			| new.getYpos() == ypos
	 * @throws	IllegalArgumentException
	 * 			If ypos isn't a valid y position the exception is thrown.
	 * 			| ! isValidPos(ypos)
	 */
	public void setYpos(Worm worm){
		this.ypos = worm.getYpos()+(worm.getRadius()*Math.sin(this.direction));
		position.setYpos(ypos);
	}
	/**
	 * Returns the y-position of the projectile.
	 */
	public double getYpos(){
		return position.getYpos();
	}
	public void setDirection(Worm worm){
		this.direction = worm.getDirection();
	}
	public double getDirection(){
		return this.direction;
	}
	public void setMass(){
		if (worm.getSelectedWeapon()=="Rifle"){
			this.mass = 10;
		}
		else {
			this.mass = 300;
		}
	}
	public int getMass(){
		return this.mass;
	}
	public void setRadius() {
		this.radius = Math.pow((3.0/4.0)*(this.getMass()/(density*Math.PI)) ,1.0/3.0);
	}
	public double getRadius() {
		return this.radius;
	}
	public boolean canJump() {
		World world = this.getWorld();
		return ( world.isPassable(this.getXpos(), this.getYpos(), this.getRadius()));
	}
	
	
	private double density = 7800;
	private double radius;
	private Worm worm;
	private Position position;
	private double xpos;
	private double ypos;
	private double direction;
	private int mass;

}
