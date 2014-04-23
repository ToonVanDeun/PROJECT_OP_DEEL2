package worms.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorldTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	private Random random;
	private static World world;
	private static World worldWithObjects;
	private static World world_projectile;
	public static boolean[][] passableMap;
	public static boolean[][] passableMap1;
	private Collection<Worm> worms = new ArrayList<Worm>();
	private Collection<Food> food = new ArrayList<Food>();
	private static Team team1;
	private static Team team2;

	@Before
	public void setUp() throws Exception {
	
	//initialization
		//passable map
		boolean[][] passableMap = new boolean[50][50];
		boolean[][] passableMap1 = new boolean[50][50];
		
		for (int i=0;i<50;i++){
			for (int j=0;j<50;j++){
				passableMap[i][j] = true;
			}
		}
		passableMap1=passableMap;
		for (int i=0;i<25;i++){
			for (int j=0;j<50;j++){
				passableMap1[i][j] = false;
			}
		}
		//world
		world = new World(100, 100, passableMap1, random  );
		worldWithObjects = new World(100, 100, passableMap1, random  );
		world_projectile = new World(100, 100, passableMap1, random  );
		//worms
		Worm worm1 = new Worm(worldWithObjects, 2, 2, 1, 1, "Timon");
		Worm worm2 = new Worm(worldWithObjects, 3, 3, 1, 1, "Poemba");
		Worm worm_team = new Worm(worldWithObjects, 2, 2, 1, 1, "Team");
		Worm worm_shoot = new Worm(world_projectile, 2, 2, 1, 1, "Shoot");
		Food food1 = new Food(worldWithObjects,5,5);
		Food food2 = new Food(worldWithObjects,6,6);
		worms.add(worm1);
		worms.add(worm2);
		worms.add(worm_team);
		food.add(food1);
		food.add(food2);
		
		team1 = new Team(worldWithObjects, "TeamTof");
		team2 = new Team(worldWithObjects, "TeamSuperTof");
	}
	@After
	public void tearDown() throws Exception {
	}

	//Width and Height
	@Test
	public void test_setWidth_valid1() {
		world.setUpperboundWidth(300);
		world.setWidth(200);
		assert world.getWidth() == 200;
	}
	@Test(expected = IllegalArgumentException.class)
	public void test_setWidth_fails() {
		world.setWidth(200);
		assert world.getWidth() == 200;
	}
	@Test
	public void test_setUpperboundWidth_valid1() {
		world.setUpperboundWidth(300);
		world.setWidth(250);
		assert world.getWidth() == 250;
		assert world.getUpperboundWidth() == 300;
	}
	@Test(expected = IllegalArgumentException.class)
	public void test_setUpperboundWidth_fails() {
		world.setUpperboundWidth(300);
		world.setWidth(350);
		assert world.getWidth() == 350;
		assert world.getUpperboundWidth() == 300;
	}
	
	//passableMap
	@Test
	public void test_isImpassable_valid1() {
		assertEquals(world.isImpassable(10, 70, 2),true);
	}
	@Test(expected = AssertionError.class)
	public void test_isImpassable_fails() {
		assertEquals(world.isImpassable(10, 30, 2),true);
	}
	@Test
	public void test_isPassable_valid1() {
		assertEquals(world.isPassable(10, 30, 2),true);
	}
	@Test(expected = AssertionError.class)
	public void test_isPassable_fails() {
		assertEquals(world.isPassable(10, 70, 2),true);
	}
	@Test
	public void test_isAdjacent_valid1() {
		assertEquals(world.isAdjacent(10, 48, 2),true);
	}
	@Test
	public void test_isAdjacent_valid2() {
		assertEquals(world.isAdjacent(10, 50, 2),false);
	}
	
	//Worm index, Start and turns.
	@Test
	public void test_getCurrentWorm_valid1() {
		Worm worm1 = new Worm(world, 2, 2, 1, 1, "Tonny");
		Worm worm2 = new Worm(world, 3, 3, 1, 1, "Gregory");
		assertEquals(world.getCurrentWorm(),worm1);
	}
	@Test
	public void test_getCurrentWorm_valid2() {
		Worm worm1 = new Worm(world, 2, 2, 1, 1, "Tonny");
		Worm worm2 = new Worm(world, 3, 3, 1, 1, "Gregory");
		world.startNextTurn();
		assertEquals(world.getCurrentWorm(),worm2);
	}
	@Test
	public void test_getCurrentWorm_valid3() {
		Worm worm1 = new Worm(world, 2, 2, 1, 1, "Tonny");
		Worm worm2 = new Worm(world, 3, 3, 1, 1, "Gregory");
		world.startNextTurn();
		world.startNextTurn();
		assertEquals(world.getCurrentWorm(),worm1);
	}
	
	//Objects
	@Test
	public void test_getNbObjects_valid1() {
		assertEquals(worldWithObjects.getNbObjects(),7);
	}
	@Test(expected = AssertionError.class)
	public void test_getNbObjects_fails() {
		assertEquals(worldWithObjects.getNbObjects(),5);
	}
	@Test
	public void test_canHaveAsObject_valid1() {
		Food food3 = new Food(worldWithObjects,7,7);
		assertEquals(worldWithObjects.canHaveAsObject(food3),false);
	}
	@Test
	public void test_canHaveAsObject_valid2() {
		Food food3 = new Food(world,7,7);
		assertEquals(worldWithObjects.canHaveAsObject(food3),true);
	}
	@Test
	public void test_hasAsObject_valid1() {
		Food food3 = new Food(worldWithObjects,7,7);
		assertEquals(worldWithObjects.hasAsObject(food3),true);
	}
	@Test
	public void test_hasAsObject_fails() {
		Food food3 = new Food(world,7,7);
		assertEquals(worldWithObjects.hasAsObject(food3),false);
	}
	//Lists of objects
	@Test
	public void test_getAllObjects_valid() {
		ArrayList<Object> lijst = new ArrayList<Object>();
		Food food1 = new Food(world,7,7);
		Food food2 = new Food(world,8,8);
		Food food3 = new Food(world,9,9);
		lijst.add(food1);
		lijst.add(food2);
		lijst.add(food3);
		assertEquals(world.getAllObjects(),lijst);
	}
	@Test
	public void test_addAsObject_valid() {
		Food food3 = new Food(world,7,7);
		food3.unsetWorld();
		food3.setWorldTo(worldWithObjects);
		assertEquals(worldWithObjects.hasAsObject(food3),true);
	}
	@Test
	public void test_removeAsObject_valid() {
		Food food3 = new Food(worldWithObjects,7,7);
		food3.unsetWorld();
		assertEquals(worldWithObjects.hasAsObject(food3),false);
	}
	@Test
	public void test_getWorms_valid1() {
		Collection<Worm> worms = new ArrayList<Worm>();
		Worm worm1 = new Worm(world, 2, 2, 1, 1, "Timon");
		Worm worm2 = new Worm(world, 3, 3, 1, 1, "Poemba");
		Food food1 = new Food(world,7,7);
		worms.add(worm1);
		worms.add(worm2);
		assertEquals(world.getWorms(),worms);
	}
	@Test
	public void test_getWorms_valid2() {
		assertEquals(worldWithObjects.getWorms(),worms);
	}
	@Test
	public void test_getFood_valid1() {
		Collection<Food> food = new ArrayList<Food>();
		Food food1 = new Food(world,7,7);
		Food food2 = new Food(world,8,8);
		Food food3 = new Food(world,9,9);
		food.add(food1);
		food.add(food2);
		food.add(food3);
		assertEquals(world.getFood(),food);
	}
	@Test
	public void test_getFood_valid2() {
		assertEquals(worldWithObjects.getFood(),food);
	}

	//Team
	@Test
	public void test_getTeams_valid() {
		assertEquals(worldWithObjects.getTeams().size(),2);
	}
	
}
