package worms.model;

import be.kuleuven.cs.som.annotate.Value;

/**
 * A class of weapons involving a name.
 * Complemented with methodes that interact with the weapon and changes certain values.
 * 
 * 
 * @invar	
 * 	
 * @author 	Toon Stuyck
 * 			Toon Van Deun
 * 			Burgerlijk Ingenieur
 * 			https://github.com/ToonVanDeun/PROJECT_OP_DEEL2
 * @version 1.0
 */
@Value
public class Weapon {
	/**
	 * Initialize a weapon with the name Rifle.
	 *  @post	The weapon is given the name.
	 * 			| 	new.getName() == Rifle
	 */
	public Weapon(){
		this.setName("Rifle");
		this.setMass();
		this.setForce();
		this.setCost();
		this.setDamage();
	}
	/**
	 * Sets the name of the weapon.
	 * @param name
	 * 			The name of the weapon
	 */
	private void setName(String name){
		this.name = name;
	}
	/**
	 * Returns the name of the weapon.
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * The method sets the mass of the weapon.
	 */
	private void setMass(){
		if (this.name == "Rifle"){
			this.mass = 10;
		} else {
			this.mass = 300;
		}
	}
	/**
	 * The method gets the mass of the weapon.
	 */
	public int getMass(){
		return this.mass;
	}
	/**
	 * The method sets the force that is exerted by the weapon.
	 */
	private void setForce(){
		if (this.name == "Rifle"){
			this.force = 1.5;
		} else {
			this.force = 2.5+(worm.getPropulsionYield()*0.07);
		}
	}
	/**
	 * The method returns the force that is exerted by the weapon.
	 */
	public double getForce(){
		return this.force;
	}
	/**
	 * The method sets the cost of action points to use the weapon.
	 */
	private void setCost(){
		if (this.name == "Rifle"){
			this.cost = 10;
		} else {
			this.cost = 50;
		}
	}
	/**
	 * The method returns the cost of action points to use the weapon.
	 */
	public int getCost(){
		return this.cost;
	}
	/**
	 * The method sets the damage the weapon will inflict.
	 */
	private void setDamage(){
		if (this.name == "Rifle"){
			this.damage = 20;
		} else {
			this.damage = 80;
		}
	}
	/**
	 * The method returns the damage the weapon will inflict.
	 */
	public int getDamage(){
		return this.damage;
	}
	/**
	 * The method makes you change between the two weapons.
	 * @post	If the current weapon is the Rifle, the weapon will change to the Bazooka
	 * 				and if the current weapon is the Bazooka it will change in to the RIfle.
	 * 			| if (old.getName() == "Rifle")
	 * 			|	then (new.getName() == "Bazooka")
	 * 			| if (old.getName() == "Bazooka")
	 * 			|	then (new.getName() == "Rifle")
	 */
	public void changeWeapon(){
		if (this.name == "Rifle"){
			this.setName("Bazooka");
		}  else {
			this.setName("Rifle");
		}
	}
	
	// Variables
	private Worm worm;
	private String name;
	private int mass;
	private double force;
	private int cost;
	private int damage;
	


}
