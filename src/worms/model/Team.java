package worms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of team that can be in a world of class World. 
 * The class inherits from Object and contains methods to add worms to and delete from a team.
 *       
 * @author 	Toon Stuyck
 * 			Toon Van Deun
 * 			Burgerlijk Ingenieur
 * 			https://github.com/ToonVanDeun/PROJECT_OP_DEEL2
 * @version 2.0
 */
public class Team extends Object {
	/**
	 * Initializes a team with a given world and name.
	 * @param 	world
	 * 			The world of the new team.
	 * @param 	name
	 * 			The name of the new team.
	 */
	public Team(World world, String name) {
		super(world);
		this.setName(name);		
	}
	/**
	 * Returns the name of the team.
	 */
	@Basic 
	public String getName() {
		return this.name;
	}
	/**
	 * Sets the name of the team to a given name if the given name is a valid name.
	 * @param 	name
	 * 			The new name of the team.
	 * @post	The name of the team is set to the new name.
	 * 			|new.getName() == name
	 * @throws 	IllegalArgumentException
	 * 			When the name is not a valid name the exception is thrown.
	 * 			| ! isValidName(name)
	 */
	@Immutable
	private void setName(String name) throws IllegalArgumentException{
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}
	/**
	 * Checks whether a given name is a valid name.
	 * @param 	name
	 * @post	Returns true if the given name is a valid name
	 * 			(if it starts with a capital and exists only of and at least 2 letters .)
	 * 			If the give name is not a valid name the method returns false.
	 * 			| result == match "[A-Z]{1}[a-zA-Z " ']{1,}"
	 */
	private static boolean isValidName(String name){
	    String regex = "^[A-Z]{1}[a-zA-Z]{1,}$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(name);
	    return matcher.find();
	}
	/**
	 * Checks whether a worm is a member of the team.
	 * @param 	worm
	 * 			The worm that needs to be checked.
	 * @return	True is the team contains the worm.
	 * 			|worms.contains(worm)
	 */
	public boolean hasAsWorm(Worm worm) {
		return worms.contains(worm);
	}
	/**
	 * Return a list of all the worms of this team.
	 * @return 	The size of the resulting list is equal to the number of
	 *         	worms in this team.
	 *       	| result.size() == getNbWorms()
	 * @return 	Each element in the resulting list is the same as the
	 *         	worms of this team at the corresponding index.
	 *       	| for each index in 0..result-size()-1 :
	 *       	|   result.get(index) == getWormAt(index+1)
	 */
	@Basic 
	public ArrayList<Worm> getAllWorms() {
		return new ArrayList<Worm>(worms);
	}
	/**
	 * Return a list of all the worms of this team that are alive.
	 * @return The size of the resulting list is equal to or less than the number of
	 *         worms in this team.
	 *       | result.size() <= worms.size()
	 * @return Each element in the resulting list is a worm of this team that is alive.
	 *       | for each index in 0..result.size()-1 :
	 *       |   result.get(index).getIsAlive() == true &&
	 *       |	 result.get(index).getTeam()==this
	 */
	public ArrayList<Worm> getAllAliveWorms() {		
		ArrayList<Worm> lijst = getAllWorms();
		ArrayList<Worm> aliveWorms = new ArrayList<Worm>(); 
		for (int i = 0; i < lijst.size(); i++) {
			if (lijst.get(i).getTeam() == this)
				aliveWorms.add((Worm) lijst.get(i));
		}
		return aliveWorms;
	}
	/**
	 * Add the given worm to the list of worms of this team.
	 * @param  	worm
	 *         	The worm to be added.
	 * @pre    	The given worm is effective and already references
	 *         	this team as its team.
	 *       	| (team != null) && (worm.getTeam() == this)
	 * @pre    	This team does not yet have the given worm
	 *         	as one of its worms.
	 *       	| ! hasAsWorm(worm)
	 * @post   	The number of worms of this team is incremented
	 *         	by 1.
	 *       	| new.getNbWorms() == getNbWorms() + 1
	 */
	public void addAsWorm(@Raw Worm worm) {
		assert (worm != null) && (worm.getTeam() == this);
		assert !hasAsWorm(worm);
		worms.add(worm);
	}
	/**
	 * Remove the given worm from the worms of this team.
	 * @param  	worm
	 *         	The worm to be removed.
	 * @pre    	The given worm is effective and does not have any
	 *         	team.
	 *       	| (worm != null) && (worm.getTeam() == null)
	 * @pre    	This team has the given worm as one of
	 *         	its worms.
	 *       	| hasAsWorm(worm)
	 * @post   	The number of worms of this team is decremented
	 *         	by 1.
	 *       	| new.getNbWorms() == getNbWorms() - 1
	 * @post   	This team no longer has the given worm as
	 *         	one of its worms.
	 *       	| (! new.hasAsWorm(worm))
	 * @post   	All worms registered beyond the removed worm
	 *         	shift one position to the left.
	 *       	| for each index in getIndexOfWorm(worm)+1..getNbWorms():
	 *       	|   new.getWormAt(index-1) == getWormAt(index) 
	 */
	public void removeAsWorm(Worm worm) {
		assert (worm != null);
		assert (hasAsWorm(worm));
		worms.remove(worm);
	}
	
	//variables
	private String name=null;
	private  List<Worm> worms = new ArrayList<Worm>();
}
