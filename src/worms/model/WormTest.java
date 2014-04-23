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

public class WormTest {

	private static final String expected  = null;


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
	private Collection<Worm> worms = new ArrayList<Worm>();
	private Collection<Food> food = new ArrayList<Food>();
	
	private static Worm worm1;
	private static Worm worm2;
	private static Worm worm_team;
	private static Worm worm_position;
	private static Worm worm_position2;
	private static Worm worm_position3;
	private static Worm worm_direction;
	private static Worm worm_radius;
	private static Worm worm_name;
	private static Worm worm_move;
	private static Worm worm_turn;
	private static Worm worm_jump;
	
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
			//worms
			worm1 = new Worm(worldWithObjects, 2, 2, 1, 1, "Timon");
			worm2 = new Worm(worldWithObjects, 3, 3, 1, 1, "Poemba");
			worm_team = new Worm(world, 0, 0, 0, 5, "Team");
			worm_position = new Worm(world, 0, 0, 0, 5, "Position");
			worm_position2 = new Worm(world, 10, 30, 0, 1, "Position2");
			worm_position3 = new Worm(world, 10, 70, 0, 1, "Position3");
			worm_direction = new Worm(world,0, 0, 0, 5, "Direction");
			worm_radius = new Worm(world, 0, 0, 0, 5, "Radius");
			worm_name = new Worm(world, 0, 0, 0, 1, "Name");
			worm_move = new Worm(world, 0, 0, (Math.PI)/4, 1, "Move");
			worm_turn = new Worm(world, 0, 0, 0, 1, "Turn");
			worm_jump = new Worm(world, 0, 0, 3 * Math.PI / 2, 1, "Jump");
			
			Food food1 = new Food(worldWithObjects,5,5);
			Food food2 = new Food(worldWithObjects,6,6);
			worms.add(worm1);
			worms.add(worm2);
			food.add(food1);
			food.add(food2);
			
			team1 = new Team(world, "TeamTof");
			team2 = new Team(world, "TeamSuperTof");
			worm_team.setTeamTo(team1);
		
	}

	@After
	public void tearDown() throws Exception {
	}	
	
	//Team
	@Test
	public void test_getTeam_valid1() {
		assertEquals( worm_team.getTeam(),team1);
	}
	@Test
	public void test_getTeamName_valid() {
		assertEquals( worm_team.getTeamName(),(String) "TeamTof");
	}
	@Test
	public void test_canHaveAsTeam_valid1() {
		assertEquals(worm_team.canHaveAsTeam(team1),false);
	}
	@Test
	public void test_canHaveAsTeam_valid2() {
		assertEquals(worm_position.canHaveAsTeam(team1),true);
	}
	@Test
	public void test_setTeamTo_valid() {
		worm_position.setTeamTo(team1);
		assertEquals(worm_position.getTeam(),(team1));
	}
	@Test(expected = IllegalArgumentException.class)
	public void test_setTeamTo_fails() {
		worm_position.setTeamTo(null);
		assertEquals(worm_position.getTeam(),(null));
	}
	@Test
	public void test_setTeamRandom_valid1() {
		worm_position.setTeamRandom();
		assert (worm_position.getTeam()==team1 || worm_position.getTeam()== team2);
	}
	@Test
	public void test_setTeamRandom_valid2() {
		worm1.setTeamRandom();
		assertEquals(worm_position.getTeam(),null);
	}
	@Test
	public void test_unsetTeam_valid() {
		worm_team.unsetTeam();
		assertEquals(worm_team.getTeam(),null);
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
	
	//Position related (fall...)
	@Test
	public void test_canFall_valid1() {
		assertEquals(worm_position2.canFall(),true);
	}
	@Test
	public void test_canFall_valid2() { 
		assertEquals(worm_position3.canFall(),false);
	}
	//WERKT VREEMDGENOEG NOG NIET
//	@Test
//	public void test_Fall_valid1() { 
//		worm_position2.fall();
//		System.out.println(worm_position3.getXpos());
//		System.out.println(worm_position3.getYpos());
//	}
	
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
	@Test
	public void test_setName_validCase5() {
		worm_name.setName("James o'Hara 007");
		assertEquals(worm_name.getName(), "James o'Hara 007");
	}
	@Test
	public void test_setName_validCase6() {
		worm_name.setName("Abcde'g7777h'''   789");
		assertEquals(worm_name.getName(), "Abcde'g7777h'''   789");
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
	
	//ActionPoints
	//werkt nog niet wegens probleem bij move.
	
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
//	@Test
//	public void test_jumpStep_valid() {
//		worm_jump.turn((3.0/4.0)*Math.PI);
//		double calculated_pos[] = {2.612802330,1.386971082};
//		assert Math.abs((worm_jump.jumpStep(0.5)[0] - calculated_pos[0])) <0.3;
//		assert Math.abs((worm_jump.jumpStep(0.5)[1] - calculated_pos[1])) <0.3;
//	}
//	@Test
	
	//hitpoints
	@Test
	public void test_setHitPoints_valid1() {
		worm_position2.setHitPoints(5);
		assertEquals(worm_position2.getHitPoints(),5);
	}
	@Test
	public void test_setHitPoints_valid2() {
		worm_position2.setHitPoints(-500);
		assertEquals(worm_position2.getHitPoints(),0);
	}
	@Test
	public void test_setHitPoints_valid3() {
		worm_position2.setHitPoints(-500);
		assertEquals(worm_position2.getIsAlive(),false);
	}
	
}
