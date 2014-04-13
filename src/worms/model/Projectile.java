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
		this.setXpos(worm);
		this.setYpos(worm);
		this.setDirection(worm);
		this.setMass(mass);
	}
	public void setXpos(Worm worm){
		this.xpos = worm.getXpos()+worm.getRadius();
	}
	public double getXpos(){
		return this.xpos;
	}
	public void setYpos(Worm worm){
		this.ypos = worm.getYpos()+worm.getRadius();
	}
	public double getYpos(){
		return this.ypos;
	}
	public void setDirection(Worm worm){
		this.direction = worm.getDirection();
	}
	public double getDirection(){
		return this.direction;
	}
	public void setMass(int mass){
		this.mass = mass;
	}
	public int getMass(){
		return this.mass;
	}
	
	
	
	
	private double xpos;
	private double ypos;
	private double direction;
	private int mass;

}
