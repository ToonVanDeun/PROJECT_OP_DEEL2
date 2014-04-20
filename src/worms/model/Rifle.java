package worms.model;

public class Rifle {
	public Rifle(){
		this.setState(true);
	}
	public void setState(boolean state){
		this.state = state;
	}
	public boolean getState(){
		return this.state;
	}
	public void setName() {
		Rifle.name = "Rifle";
	}
	public static String getName() {
		return name;
	}
	public void setMass() {
		this.mass = 10;
	}
	public int getMass() {
		return this.mass;
	}
	public void setForce() {
		this.force = 1.5;
	}
	public double getForce() {
		return this.force;
	}
	public void setDamage() {
		this.damage = 20;
	}
	public int getDamage() {
		return this.damage;
	}
	public void setCostActionPoints() {
		this.cost = 10;
	}
	public int getCostActionPoints(){
		return this.cost;
	}

	private int mass;
	private double force;
	private int damage;
	private int cost;
	private static String name;
	private boolean state;

}
