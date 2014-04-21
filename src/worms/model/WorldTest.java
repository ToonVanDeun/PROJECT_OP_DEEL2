package worms.model;

import static org.junit.Assert.*;

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
	public static boolean[][] passableMap;
	public static boolean[][] passableMap1;

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
		//worms
		Worm worm1 = new Worm(worldWithObjects, 2, 2, 1, 1, "Timon");
		Worm worm2 = new Worm(worldWithObjects, 3, 3, 1, 1, "Poemba");
		Food food1 = new Food(worldWithObjects,5,5);
		Food food2 = new Food(worldWithObjects,6,6);
		
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
		assertEquals(worldWithObjects.getNbObjects(),4);
	}
	@Test(expected = AssertionError.class)
	public void test_getNbObjects_fails() {
		assertEquals(worldWithObjects.getNbObjects(),7);
	}
	@Test
	public void test_canHaveAsObject_valid1() {
		Food food3 = new Food(worldWithObjects,7,7);
		System.out.println(worldWithObjects.canHaveAsObject(food3));
		assertEquals(worldWithObjects.canHaveAsObject(food3),true);
	}
	//Lists of objects
	

}
