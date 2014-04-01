package worms.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	private static Worm worm_position;
	private static Worm worm_direction;
	private static Worm worm_radius;
	private static Worm worm_name;
	private static Worm worm_move;
	private static Worm worm_turn;
	private static Worm worm_jump;
	
	
	@Before
	public void setUp() throws Exception {
		worm_position = new Worm(0, 0, 0, 5, "Position");
		worm_direction = new Worm(0, 0, 0, 5, "Direction");
		worm_radius = new Worm(0, 0, 0, 5, "Radius");
		worm_name = new Worm(0, 0, 0, 1, "Name");
		worm_move = new Worm(0, 0, (Math.PI)/4, 1, "Move");
		worm_turn = new Worm(0, 0, 0, 1, "Turn");
		worm_jump = new Worm(0, 0, 3 * Math.PI / 2, 1, "Jump");
	}

	@After
	public void tearDown() throws Exception {
	}	
	
	//position
	@Test
	public void test_isValidPos_valid1() {
		assertEquals( worm_position.isValidPos(2.9),true);
	}
	@Test
	public void test_isValidPos_valid2() {
		assertEquals( worm_position.isValidPos(Double.POSITIVE_INFINITY),true);
	}
	@Test
	public void test_isValidPos_valid3() {
		assertEquals( worm_position.isValidPos(Double.NEGATIVE_INFINITY),true);
	}
	@Test
	public void test_isValidPos_fails() {
		assertEquals(worm_position.isValidPos(Double.NaN),false);
	}
	
	//direction
	@Test
	public void test_isValidDirection_valid() {
		assertEquals(worm_direction.isValidDirection(2.9),true);
	}
	@Test
	public void test_isValidDirection_fails() {
		assertEquals(worm_direction.isValidDirection(Double.NaN),false);
	}
	
	//radius
	@Test
	public void test_setRadius_valid() {
		worm_radius.setRadius(5.3);
		assert worm_radius.getRadius() ==  5.3;	
	}
	@Test(expected = IllegalArgumentException.class)
	public void test_setRadius_fails() {
		worm_radius.setRadius(0.1);	
	}
	@Test
	public void test_isValidRadius_valid1() {
		assertEquals(worm_radius.isValidRadius(2.9),true);
	}
	@Test
	public void test_isValidRadius_valid2() {
		assertEquals(worm_radius.isValidRadius(Double.POSITIVE_INFINITY),true);
	}
	@Test
	public void test_isValidRadius_failsCase1() {
		assertEquals(worm_radius.isValidRadius(Double.NaN),false);
	}
	@Test
	public void test_isValidRadius_failsCase2() {
		assertEquals(worm_radius.isValidRadius(0.1),false);
	}
	@Test
	public void test_isValidRadius_failsCase3() {
		assertEquals(worm_radius.isValidRadius(Double.NEGATIVE_INFINITY),false);
	}
	//name
	@Test
	public void test_setName_validCase1() {
		worm_name.setName("Az");
		assertEquals(worm_name.getName(), "Az");
	}
	@Test
	public void test_setName_validCase2() {
		worm_name.setName("Ab cd ef g");
		assertEquals(worm_name.getName(), "Ab cd ef g");
	}
	@Test
	public void test_setName_validCase3() {
		worm_name.setName("Abcde'gh'hij");
		assertEquals(worm_name.getName(), "Abcde'gh'hij");
	}
	@Test
	public void test_setName_validCase4() {
		worm_name.setName("Azerty5");
		assertEquals(worm_name.getName(), "Azerty5");
	}
	@Test(expected = IllegalArgumentException.class)
	public void test_setName_failsCase1() {
		worm_name.setName("A");
	}
	@Test(expected = IllegalArgumentException.class)
	public void test_setName_failsCase2() {
		worm_name.setName("azerty");
	}
	@Test(expected = IllegalArgumentException.class)
	public void test_setName_failsCase3() {
		worm_name.setName("Azerty///");
	}
	
	//move
	@Test
	public void test_move_validCase1() {
		int initialActionPoints = worm_move.getActionPoints();
		double oldXpos = worm_move.getXpos();
		double oldYpos = worm_move.getYpos();
		worm_move.move();
		assertEquals(worm_move.getActionPoints(), (initialActionPoints
				- Math.round(5.0*Math.sqrt(2.0)/2)));
		assert worm_move.getXpos() == (oldXpos + Math.cos(Math.PI/4));
		assert worm_move.getYpos() == (oldYpos + Math.sin(Math.PI/4));
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void test_move_fails() {
//		worm_move.move(1500);
//	}
//	@Test
//	public void test_isValidStep_valid() {
//		assertEquals(worm_move.isValidStep(5),true);
//	}
//	@Test
//	public void test_isValidStep_fails() {
//		assertEquals(worm_move.isValidStep(1500),false);
//	}
	//turn
	@Test
	public void test_isValidTurn_valid() {
		assertEquals(worm_turn.isValidTurn(5),true);
	}
	@Test
	public void test_isValidTurn_fails() {
		assertEquals(worm_turn.isValidTurn(1500),false);
	}
	@Test
	public void test_turn_validCase1() {
		worm_turn.turn(Math.PI);
		assert worm_turn.getDirection() == Math.PI;
	}
	@Test
	public void test_turn_validCase2() {
		worm_turn.turn(3*Math.PI);
		assert worm_turn.getDirection() == Math.PI;
	}
	@Test
	public void test_turn_validCase3() {
		worm_turn.turn(-Math.PI);
		assert worm_turn.getDirection() == Math.PI;
	}
	@Test(expected = AssertionError.class)
	public void test_turn_failsNotEnoughActionPoints() {
		worm_turn.turn(170*Math.PI);
	}
	
	//jump
	@Test(expected = IllegalStateException.class)
	public void test_jump_failDirection() {
		worm_jump.jump();
	}
	@Test
	public void test_jump_valid() {
		double oldXpos = worm_jump.getXpos();
		worm_jump.turn((3.0/4.0)*Math.PI);
		worm_jump.jump();
		assert Math.abs(worm_jump.getXpos() - (oldXpos + 5.59)) <0.1 ;
	}
	@Test(expected = IllegalStateException.class)
	public void test_jump_failsAP() {
		worm_jump.turn((3.0/4.0)*Math.PI);
		worm_jump.jump();
		worm_jump.jump();
	}
	@Test
	public void test_jumpTime_valid() {
		worm_jump.turn((3.0/4.0)*Math.PI);
		assert Math.abs((worm_jump.jumpTime() - 1.065726760)) <0.1;
	}
	@Test
	public void test_jumpStep_valid() {
		worm_jump.turn((3.0/4.0)*Math.PI);
		double calculated_pos[] = {2.612802330,1.386971082};
		assert Math.abs((worm_jump.jumpStep(0.5)[0] - calculated_pos[0])) <0.3;
		assert Math.abs((worm_jump.jumpStep(0.5)[1] - calculated_pos[1])) <0.3;
	}
	@Test
	public void test_canJump_valid1() {
		worm_jump.turn((-9.0/2.0)*Math.PI);
		assert worm_jump.canJump()==true;
	}
	@Test
	public void test_canJump_valid2() {
		worm_jump.turn((5.0/2.0)*Math.PI);
		assert worm_jump.canJump()==true;
	}
	@Test
	public void test_canJump_valid3() {
		worm_jump.turn((7.0/2.0)*Math.PI);
		assert worm_jump.canJump()==true;
	}
	@Test
	public void test_canJump_invalid1() {
		worm_jump.turn((4.0/2.0)*Math.PI);
		assert worm_jump.canJump()==false;
	}
	@Test
	public void test_canJump_fails() {
		assert worm_jump.canJump()==false;
	}
	@Test
	public void test_canJump_failsAP() {
		worm_jump.turn((3.0/4.0)*Math.PI);
		worm_jump.jump();
		assert worm_jump.canJump()==false;
	}
}
