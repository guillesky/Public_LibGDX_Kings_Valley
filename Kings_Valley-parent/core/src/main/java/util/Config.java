package util;

public class Config
{

	private static final int INDEX_SPEED_WALK = 0;
	private static final int INDEX_SPEED_STAIR = 1;
	private static final int INDEX_MIN_TIME_TO_DECIDE = 2;
	private static final int INDEX_MAX_TIME_TO_DECIDE = 3;
	private static final int INDEX_MIN_TIME_DECIDING = 4;
	private static final int INDEX_MAX_TIME_DECIDING = 5;
	private static final int INDEX_DECICION_FACTOR = 6;
	private static final int INDEX_DECICION_FACTOR_JUMP = 7;
	private static final int INDEX_BEST_DECICION_PROBALITY = 8;
	private static final int QUAD_DISTANCE_VISION = 9;

	private float characterSpeedFall;
	private float characterSpeedJump;

	private float playerSpeedWalk;
	private float playerSpeedWalkStairs;

	private float minMummySpawnDistanceToPlayer;
	private float[] mummyWhiteParameters;
	private float[] mummyBlueParameters;
	private float[] mummyYellowParameters;
	private float[] mummyOrangeParameters;
	private float[] mummyRedParameters;

	private float characterWidth;
	private float characterHeight;
	private float characterFeetWidth;
	private float characterFeetHeight;

	private float levelObjectWidth;
	private float levelObjectHeight;

	private float stairWidth;
	private float stairHeight;

	private float giratoryWidth;

	private float levelTileWidthUnits;
	private float levelTileHeightUnits;
	private float flyingDaggerSpeed;
	private float flyingDaggerSpeedFall;

	private float speedGame = 1.0f;

	private static final Config instance = new Config();

	private Config()
	{
		this.defaultValues(10, 10);
	}

	public void defaultValues(float levelTileWidthUnits, float levelTileHeightUnits)
	{
		this.levelTileWidthUnits = levelTileWidthUnits;
		this.levelTileHeightUnits = levelTileHeightUnits;

		this.characterSpeedFall = (int) (this.levelTileHeightUnits * (-60));
		this.characterSpeedJump = (int) (this.levelTileHeightUnits * (15));

		this.playerSpeedWalk = (int) (6 * this.levelTileWidthUnits);
		this.playerSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
		this.flyingDaggerSpeed = this.levelTileWidthUnits * 18;
		this.flyingDaggerSpeedFall = -this.flyingDaggerSpeed;
	
		this.setMummyParameters(levelTileWidthUnits);
		
		this.characterWidth = this.levelTileWidthUnits * 0.6f;
		this.characterHeight = this.levelTileHeightUnits * 1.6f;
		this.characterFeetHeight = this.levelTileHeightUnits * 0.1f;
		this.characterFeetWidth = this.levelTileWidthUnits * 0.1f;

		this.levelObjectWidth = this.levelTileWidthUnits;
		this.levelObjectHeight = this.levelTileHeightUnits;

		this.stairWidth = this.levelTileWidthUnits * 0.1f;
		this.stairHeight = this.levelTileHeightUnits * .1f;

		this.giratoryWidth = this.levelTileWidthUnits * 1.3f;

	}

	private void setMummyParameters(float levelTileWidthUnits)
	{
		
/*		this.mummyWhiteSpeedWalk = (int) (2.5 * this.levelTileWidthUnits);
		this.mummyWhiteSpeedWalkStairs = (int) (3 * this.levelTileWidthUnits);
		this.mummyWhiteDecisionFactor = 0.5f;
		this.mummyWhiteMinTimeToDecide = 5;
		this.mummyWhiteMaxTimeToDecide = 10;
		this.mummyWhiteMinTimeDeciding = 2.0f;
		this.mummyWhiteMaxTimeDeciding = 4.0f;

		this.mummyBlueSpeedWalk = (int) (4.5 * this.levelTileWidthUnits);
		this.mummyBlueSpeedWalkStairs = (int) (5 * this.levelTileWidthUnits);
		this.mummyBlueDecisionFactor = 0.5f;
		this.mummyBlueMinTimeToDecide = 5;
		this.mummyBlueMaxTimeToDecide = 8;
		this.mummyBlueMinTimeDeciding = 2.0f;
		this.mummyBlueMaxTimeDeciding = 3.0f;
		
		this.mummyYellowSpeedWalk = (int) (5 * this.levelTileWidthUnits);
		this.mummyYellowSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
		this.mummyYellowDecisionFactor = 0.3f;
		this.mummyYellowMinTimeToDecide = 5;
		this.mummyYellowMaxTimeToDecide = 6;
		this.mummyYellowMinTimeDeciding = 1.5f;
		this.mummyYellowMaxTimeDeciding = 2.5f;
		
		this.mummyOrangeSpeedWalk = (int) (6 * this.levelTileWidthUnits);
		this.mummyOrangeSpeedWalkStairs = (int) (7 * this.levelTileWidthUnits);
		this.mummyOrangeDecisionFactor = 0.4f;
		this.mummyOrangeMinTimeToDecide = 4;
		this.mummyOrangeMaxTimeToDecide = 5;
		this.mummyOrangeMinTimeDeciding = 1.0f;
		this.mummyOrangeMaxTimeDeciding = 2.0f;
		
		this.mummyRedSpeedWalk = (int) (6.5 * this.levelTileWidthUnits);
		this.mummyRedSpeedWalkStairs = (int) (7.5 * this.levelTileWidthUnits);
		this.mummyRedDecisionFactor = 0.5f;
		this.mummyRedMinTimeToDecide = 3;
		this.mummyRedMaxTimeToDecide = 5;
		this.mummyRedMinTimeDeciding = 0.2f;
		this.mummyRedMaxTimeDeciding = 1.0f;



*/
		
		

		

		
		
		
		
		
		this.mummyWhiteParameters= new float[10];
		this.mummyBlueParameters= new float[10];
		this.mummyYellowParameters= new float[10];
		this.mummyOrangeParameters= new float[10];
		this.mummyRedParameters= new float[10];
		
		this.minMummySpawnDistanceToPlayer=64;
		
		this.mummyWhiteParameters[Config.INDEX_SPEED_WALK]=(2.5f * this.levelTileWidthUnits);
		this.mummyWhiteParameters[Config.INDEX_SPEED_STAIR]=(3f * this.levelTileWidthUnits);
		this.mummyWhiteParameters[Config.INDEX_MIN_TIME_TO_DECIDE]=5;
		this.mummyWhiteParameters[Config.INDEX_MAX_TIME_TO_DECIDE]=10;
		this.mummyWhiteParameters[Config.INDEX_MIN_TIME_DECIDING]=2;
		this.mummyWhiteParameters[Config.INDEX_MAX_TIME_DECIDING]=4;
		this.mummyWhiteParameters[Config.INDEX_DECICION_FACTOR]=0.5f;
		this.mummyWhiteParameters[Config.INDEX_DECICION_FACTOR_JUMP]=0.25f;
		this.mummyWhiteParameters[Config.INDEX_BEST_DECICION_PROBALITY]=0.4f;
		this.mummyWhiteParameters[Config.QUAD_DISTANCE_VISION]=36;
	
		this.mummyBlueParameters[Config.INDEX_SPEED_WALK]=(4.5f * this.levelTileWidthUnits);
		this.mummyBlueParameters[Config.INDEX_SPEED_STAIR]=(5f * this.levelTileWidthUnits);
		this.mummyBlueParameters[Config.INDEX_MIN_TIME_TO_DECIDE]=5;
		this.mummyBlueParameters[Config.INDEX_MAX_TIME_TO_DECIDE]=8;
		this.mummyBlueParameters[Config.INDEX_MIN_TIME_DECIDING]=2;
		this.mummyBlueParameters[Config.INDEX_MAX_TIME_DECIDING]=3;
		this.mummyBlueParameters[Config.INDEX_DECICION_FACTOR]=0.5f;
		this.mummyBlueParameters[Config.INDEX_DECICION_FACTOR_JUMP]=0.25f;
		this.mummyBlueParameters[Config.INDEX_BEST_DECICION_PROBALITY]=0.5f;
		this.mummyBlueParameters[Config.QUAD_DISTANCE_VISION]=49;
	
	
		this.mummyYellowParameters[Config.INDEX_SPEED_WALK]=(5f * this.levelTileWidthUnits);
		this.mummyYellowParameters[Config.INDEX_SPEED_STAIR]=(6f * this.levelTileWidthUnits);
		this.mummyYellowParameters[Config.INDEX_MIN_TIME_TO_DECIDE]=5;
		this.mummyYellowParameters[Config.INDEX_MAX_TIME_TO_DECIDE]=6;
		this.mummyYellowParameters[Config.INDEX_MIN_TIME_DECIDING]=1.5f;
		this.mummyYellowParameters[Config.INDEX_MAX_TIME_DECIDING]=2.5f;
		this.mummyYellowParameters[Config.INDEX_DECICION_FACTOR]=0.5f;
		this.mummyYellowParameters[Config.INDEX_DECICION_FACTOR_JUMP]=0.25f;
		this.mummyYellowParameters[Config.INDEX_BEST_DECICION_PROBALITY]=0.6f;
		this.mummyYellowParameters[Config.QUAD_DISTANCE_VISION]=64;
	
	
		this.mummyOrangeParameters[Config.INDEX_SPEED_WALK]=(6f * this.levelTileWidthUnits);
		this.mummyOrangeParameters[Config.INDEX_SPEED_STAIR]=(7f * this.levelTileWidthUnits);
		this.mummyOrangeParameters[Config.INDEX_MIN_TIME_TO_DECIDE]=4;
		this.mummyOrangeParameters[Config.INDEX_MAX_TIME_TO_DECIDE]=5;
		this.mummyOrangeParameters[Config.INDEX_MIN_TIME_DECIDING]=1f;
		this.mummyOrangeParameters[Config.INDEX_MAX_TIME_DECIDING]=2f;
		this.mummyOrangeParameters[Config.INDEX_DECICION_FACTOR]=0.5f;
		this.mummyOrangeParameters[Config.INDEX_DECICION_FACTOR_JUMP]=0.25f;
		this.mummyOrangeParameters[Config.INDEX_BEST_DECICION_PROBALITY]=0.7f;
		this.mummyOrangeParameters[Config.QUAD_DISTANCE_VISION]=81;
	
		this.mummyRedParameters[Config.INDEX_SPEED_WALK]=(6.5f * this.levelTileWidthUnits);
		this.mummyRedParameters[Config.INDEX_SPEED_STAIR]=(7.5f * this.levelTileWidthUnits);
		this.mummyRedParameters[Config.INDEX_MIN_TIME_TO_DECIDE]=3;
		this.mummyRedParameters[Config.INDEX_MAX_TIME_TO_DECIDE]=5;
		this.mummyRedParameters[Config.INDEX_MIN_TIME_DECIDING]=0.2f;
		this.mummyRedParameters[Config.INDEX_MAX_TIME_DECIDING]=1f;
		this.mummyRedParameters[Config.INDEX_DECICION_FACTOR]=0.5f;
		this.mummyRedParameters[Config.INDEX_DECICION_FACTOR_JUMP]=0.25f;
		this.mummyRedParameters[Config.INDEX_BEST_DECICION_PROBALITY]=0.8f;
		this.mummyRedParameters[Config.QUAD_DISTANCE_VISION]=121;
	
		
		
		
		
	}

	public float getCharacterSpeedFall()
	{
		return characterSpeedFall;
	}

	public void setCharacterSpeedFall(float characterSpeedFall)
	{
		this.characterSpeedFall = characterSpeedFall;
	}

	public float getPlayerSpeedWalk()
	{
		return playerSpeedWalk;
	}

	public void setPlayerSpeedWalk(float playerSpeedWalk)
	{
		this.playerSpeedWalk = playerSpeedWalk;
	}

	public float getPlayerSpeedWalkStairs()
	{
		return playerSpeedWalkStairs;
	}

	public void setPlayerSpeedWalkStairs(float playerSpeedWalkStairs)
	{
		this.playerSpeedWalkStairs = playerSpeedWalkStairs;
	}

	
	public float getCharacterWidth()
	{
		return characterWidth;
	}

	public void setCharacterWidth(float characterWidth)
	{
		this.characterWidth = characterWidth;
	}

	public float getCharacterHeight()
	{
		return characterHeight;
	}

	public void setCharacterHeight(float characterHeight)
	{
		this.characterHeight = characterHeight;
	}

	public float getLevelObjectWidth()
	{
		return levelObjectWidth;
	}

	public void setLevelObjectWidth(float levelObjectWidth)
	{
		this.levelObjectWidth = levelObjectWidth;
	}

	public float getLevelObjectHeight()
	{
		return levelObjectHeight;
	}

	public void setLevelObjectHeight(float levelObjectHeight)
	{
		this.levelObjectHeight = levelObjectHeight;
	}

	public float getStairWidth()
	{
		return stairWidth;
	}

	public void setStairWidth(float stairWidth)
	{
		this.stairWidth = stairWidth;
	}

	public float getStairHeight()
	{
		return stairHeight;
	}

	public void setStairHeight(float stairHeight)
	{
		this.stairHeight = stairHeight;
	}

	public float getLevelTileWidthUnits()
	{
		return levelTileWidthUnits;
	}

	public void setLevelTileWidthUnits(float levelTileWidthUnits)
	{
		this.levelTileWidthUnits = levelTileWidthUnits;
	}

	public float getLevelTileHeightUnits()
	{
		return levelTileHeightUnits;
	}

	public void setLevelTileHeightUnits(float levelTileHeightUnits)
	{
		this.levelTileHeightUnits = levelTileHeightUnits;
	}

	public static Config getInstance()
	{
		return instance;
	}

	public float getSpeedGame()
	{
		return speedGame;
	}

	public void setSpeedGame(float speedGame)
	{
		this.speedGame = speedGame;
	}

	public float getCharacterFeetWidth()
	{
		return characterFeetWidth;
	}

	public void setCharacterFeetWidth(float characterFeetWidth)
	{
		this.characterFeetWidth = characterFeetWidth;
	}

	public float getCharacterFeetHeight()
	{
		return characterFeetHeight;
	}

	public void setCharacterFeetHeight(float characterFeetHeight)
	{
		this.characterFeetHeight = characterFeetHeight;
	}

	public float getGiratoryWidth()
	{
		return giratoryWidth;
	}

	public void setGiratoryWidth(float giratoryWidth)
	{
		this.giratoryWidth = giratoryWidth;
	}

	

	public float getCharacterSpeedJump()
	{
		return characterSpeedJump;
	}

	public void setCharacterSpeedJump(float characterSpeedJump)
	{
		this.characterSpeedJump = characterSpeedJump;
	}

	public float getFlyingDaggerSpeed()
	{
		return flyingDaggerSpeed;
	}

	public void setFlyingDaggerSpeed(float flyingDaggerSpeed)
	{
		this.flyingDaggerSpeed = flyingDaggerSpeed;
	}

	public float getFlyingDaggerSpeedFall()
	{
		return flyingDaggerSpeedFall;
	}

	public void setFlyingDaggerSpeedFall(float flyingDaggerSpeedFall)
	{
		this.flyingDaggerSpeedFall = flyingDaggerSpeedFall;
	}

	

	public float[] getMummyBlueParameters()
	{

		return this.mummyBlueParameters;
	}

	public float[] getMummyWhiteParameters()
	{

		return this.mummyWhiteParameters;
	}

	public float[] getMummyYellowParameters()
	{

		return this.mummyYellowParameters;
	}

	public float[] getMummyOrangeParameters()
	{

		return this.mummyOrangeParameters;
	}

	public float[] getMummyRedParameters()
	{

		return this.mummyRedParameters;
	}

	public float getMinMummySpawnDistanceToPlayer()
	{
	    return minMummySpawnDistanceToPlayer;
	}

	public void setMinMummySpawnDistanceToPlayer(float minMummySpawnDistanceToPlayer)
	{
	    this.minMummySpawnDistanceToPlayer = minMummySpawnDistanceToPlayer;
	}

	public void setMummyWhiteParameters(float[] mummyWhiteParameters)
	{
	    this.mummyWhiteParameters = mummyWhiteParameters;
	}

	public void setMummyBlueParameters(float[] mummyBlueParameters)
	{
	    this.mummyBlueParameters = mummyBlueParameters;
	}

	public void setMummyYellowParameters(float[] mummyYellowParameters)
	{
	    this.mummyYellowParameters = mummyYellowParameters;
	}

	public void setMummyOrangeParameters(float[] mummyOrangeParameters)
	{
	    this.mummyOrangeParameters = mummyOrangeParameters;
	}

	public void setMummyRedParameters(float[] mummyRedParameters)
	{
	    this.mummyRedParameters = mummyRedParameters;
	}

	
}
