package worms.model;

public class Bazooka extends Object {
	public Bazooka(World world) {
		super(world);
	}
	public void setState(boolean state){
		this.state = state;
	}
	public boolean getState(){
		return this.state;
	}
	public static void setName(){
		Bazooka.name = "Bazooka";
	}
	public static String getName(){
		return name;
	}
	public void setMass() {
		this.mass = 300;
	}
	public int getMass() {
		return this.mass;
	}
	public void setForce() {
		this.force = 2.5+(worm.getPropulsionYield()*0.07);
	}
	public double getForce() {
		return this.force;
	}
	public void setDamage() {
		this.damage = 80;
	}
	public int getDamage() {
		return this.damage;
	}
	public void setCostActionPoints() {
		this.cost = 50;
	}
	public int getCostActionPoints(){
		return this.cost;
	}
	
	private Worm worm;
	private int mass;
	private double force;
	private int damage;
	private int cost;
	private static String name;
	private boolean state;


}
