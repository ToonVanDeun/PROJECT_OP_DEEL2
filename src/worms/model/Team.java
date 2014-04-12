/**
 * 
 */
package worms.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * @author Toon
 *
 */
public class Team extends Object {
	
	public Team(World world, String name) {
		super(world);
		this.setName(name);
		
		
	}
	
	/**
	 * Sets the name of the worm to a given name if the given name is a valid name.
	 * @param name
	 * 			The new name of the worm.
	 * @post	The name of the worm is set to the new name.
	 * 			|new.getName() == name
	 * @throws 	IllegalArgumentException
	 * 			When the name is not a valid name the exception is thrown.
	 * 			| ! isValidName(name)
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException{
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	/**
	 * Checks whether a given name is a valid name.
	 * @param name
	 * @post	Returns true if the given name is a valid name
	 * 			(if it starts with a capital and exists only of and at least 2 letters .)
	 * 			If the give name is not a valid name the method returns false.
	 * 			| result == match "[A-Z]{1}[a-zA-Z " ']{1,}"
	 */
	@Raw
	public static boolean isValidName(String name){
	    String regex = "^[A-Z]{1}[a-zA-Z]{1,}$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(name);
	    return matcher.find();
	}

	private String name;

}
