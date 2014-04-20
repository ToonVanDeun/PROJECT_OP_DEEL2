package worms.model;

public class Weapon {
	
	
	public Weapon(){
		this.setName("Rifle");
		this.getName();
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}
	
	public void setMass(){
		if (this.name == "Rifle"){
			this.mass = 10;
		} else {
			this.mass = 300;
		}
	}
	public int getMass(){
		return this.mass;
	}
	public void setForce(){
		if (this.name == "Rifle"){
			this.force = 1.5;
		} else {
			this.force = 2.5+(worm.getPropulsionYield()*0.07);
		}
	}
	public double getForce(){
		return this.force;
	}
	public void setCost(){
		if (this.name == "Rifle"){
			this.cost = 10;
		} else {
			this.cost = 50;
		}
	}
	public int getCost(){
		return this.cost;
	}
	public void setDamage(){
		if (this.name == "Rifle"){
			this.damage = 20;
		} else {
			this.damage = 80;
		}
	}
	public int getDamage(){
		return this.damage;
	}
	public void changeWeapon(){
		System.out.println("naam "+this.name);
		if (this.name == "Rifle"){
			System.out.println("if_naam1 "+this.name);
			this.setName("Bazooka");
			System.out.println("if_naam2 "+this.name);
			this.getName();
			System.out.println("if_naam3 "+this.name);
		}  else {
			System.out.println("else_naam1 "+this.name);
			this.setName("Rifle");
			this.getName();
			System.out.println("else_naam2 "+this.name);
		}
		System.out.println("naam1 "+this.name);
	}
	
	private Worm worm;
	private String name;
	private int mass;
	private double force;
	private int cost;
	private int damage;
	


}
