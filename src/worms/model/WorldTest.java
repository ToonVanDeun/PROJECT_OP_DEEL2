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

	@Before
	public void setUp() throws Exception {
	
	Random random = null;
	world = new World(100, 100, passableMap, random  );
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
	

}
