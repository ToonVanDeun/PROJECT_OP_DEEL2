package worms.model;

import be.kuleuven.cs.som.annotate.Value;

@Value
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
		if (this.name == "Rifle"){
			this.setName("Bazooka");
			this.getName();
		}  else {
			this.setName("Rifle");
			this.getName();
		}
	}
	
	private Worm worm;
	private String name;
	private int mass;
	private double force;
	private int cost;
	private int damage;
	


}
