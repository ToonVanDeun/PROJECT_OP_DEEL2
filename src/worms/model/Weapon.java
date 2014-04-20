package worms.model;

public class Weapon {
	
	private Object activeWeapon;
	public Weapon(){
		this.setNameRifle();
	}
	
	public void setNameRifle(){
		this.name = "rifle";
		this.stateRifle = true;
		this.stateBazooka = false;
	}
	public void setNameBazooka(){
		this.name = "Bazooka";
		this.stateBazooka = true;
		this.stateRifle = false;
	}
	public String getName(){
		return this.name;
	}
	
	public void setMass(){
		if (this.stateRifle == true){
			this.mass = 10;
		} else {
			this.mass = 300;
		}
	}
	public int getMass(){
		return this.mass;
	}
	public void setForce(){
		if (this.stateRifle == true){
			this.force = 1.5;
		} else {
			this.force = 2.5+(worm.getPropulsionYield()*0.07);
		}
	}
	public double getForce(){
		return this.force;
	}
	public void setCost(){
		if (this.stateRifle == true){
			this.cost = 10;
		} else {
			this.cost = 50;
		}
	}
	public int getCost(){
		return this.cost;
	}
	public void setDamage(){
		if (this.stateRifle == true){
			this.damage = 20;
		} else {
			this.damage = 80;
		}
	}
	public int getDamage(){
		return this.damage;
	}
	public void changeWeapon(){
		if (this.stateRifle == true){
			this.setNameBazooka();
		} else {
			this.setNameRifle();
		}
	}
	public boolean getstate(){
		if (this.stateBazooka = true) {
			this.activeWeapon = bazooka;
		}
	}
	
	private Worm worm;
	private String name;
	private int mass;
	private double force;
	private int cost;
	private int damage;
	private boolean stateRifle;
	private boolean stateBazooka;


}
