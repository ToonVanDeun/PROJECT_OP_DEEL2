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
	private static World world;
	public static boolean[][] passableMap;
	public static boolean[][] passableMap1;

	@Before
	public void setUp() throws Exception {
	
	//initialization
	Random random = null;
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
	public void test_isAdjacent() {
		//shit werkt nog ni....
		for (double i=0;i<100;i+=0.1){
			for (double j=0;j<100;j+=0.1){
				if (world.isAdjacent(i, j, 1)) {
					System.out.println("i: "+i+"j: "+j+"true"+world.isAdjacent(i, j, 2));
				}
				
				
			}
		}
		//assertEquals(world.isAdjacent(10, 48, 2),true);
	}
	
	//worm index etc
	//Start and turns
	
	//lists of objects

}
