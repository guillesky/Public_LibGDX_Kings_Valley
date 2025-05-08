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

	private float characterSpeedFall;
	private float characterSpeedJump;

	private float playerSpeedWalk;
	private float playerSpeedWalkStairs;

	private float mummyWhiteSpeedWalk;
	private float mummyWhiteSpeedWalkStairs;

	private float mummyWhiteDecisionFactor;
	private float mummyWhiteMinTimeToDecide;
	private float mummyWhiteMaxTimeToDecide;
	private float mummyWhiteMinTimeDeciding;
	private float mummyWhiteMaxTimeDeciding;

	private float mummyBlueSpeedWalk;
	private float mummyBlueSpeedWalkStairs;

	private float mummyBlueDecisionFactor;
	private float mummyBlueMinTimeToDecide;
	private float mummyBlueMaxTimeToDecide;
	private float mummyBlueMinTimeDeciding;
	private float mummyBlueMaxTimeDeciding;

	private float mummyYellowSpeedWalk;
	private float mummyYellowSpeedWalkStairs;

	private float mummyYellowDecisionFactor;
	private float mummyYellowMinTimeToDecide;
	private float mummyYellowMaxTimeToDecide;
	private float mummyYellowMinTimeDeciding;
	private float mummyYellowMaxTimeDeciding;

	private float mummyOrangeSpeedWalk;
	private float mummyOrangeSpeedWalkStairs;

	private float mummyOrangeDecisionFactor;
	private float mummyOrangeMinTimeToDecide;
	private float mummyOrangeMaxTimeToDecide;

	private float mummyOrangeMinTimeDeciding;
	private float mummyOrangeMaxTimeDeciding;

	private float mummyRedSpeedWalk;
	private float mummyRedSpeedWalkStairs;

	private float mummyRedDecisionFactor;
	private float mummyRedMinTimeToDecide;
	private float mummyRedMaxTimeToDecide;
	private float mummyRedMinTimeDeciding;
	private float mummyRedMaxTimeDeciding;
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


*/
		
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

		
		
		
		
		this.mummyWhiteParameters= new float[9];
		this.mummyBlueParameters= new float[9];
		this.mummyYellowParameters= new float[9];
		this.mummyOrangeParameters= new float[9];
		this.mummyRedParameters= new float[9];
		
		this.mummyWhiteParameters[Config.INDEX_SPEED_WALK]=(2.5f * this.levelTileWidthUnits);
		this.mummyWhiteParameters[Config.INDEX_SPEED_STAIR]=(3f * this.levelTileWidthUnits);
		this.mummyWhiteParameters[Config.INDEX_MIN_TIME_TO_DECIDE]=5;
		this.mummyWhiteParameters[Config.INDEX_MAX_TIME_TO_DECIDE]=10;
		this.mummyWhiteParameters[Config.INDEX_MIN_TIME_DECIDING]=2;
		this.mummyWhiteParameters[Config.INDEX_MAX_TIME_DECIDING]=4;
		this.mummyWhiteParameters[Config.INDEX_DECICION_FACTOR]=0.5f;
		this.mummyWhiteParameters[Config.INDEX_DECICION_FACTOR_JUMP]=0.25f;
		this.mummyWhiteParameters[Config.INDEX_BEST_DECICION_PROBALITY]=0.4f;
		
	
		this.mummyBlueParameters[Config.INDEX_SPEED_WALK]=(4.5f * this.levelTileWidthUnits);
		this.mummyBlueParameters[Config.INDEX_SPEED_STAIR]=(5f * this.levelTileWidthUnits);
		this.mummyBlueParameters[Config.INDEX_MIN_TIME_TO_DECIDE]=5;
		this.mummyBlueParameters[Config.INDEX_MAX_TIME_TO_DECIDE]=8;
		this.mummyBlueParameters[Config.INDEX_MIN_TIME_DECIDING]=2;
		this.mummyBlueParameters[Config.INDEX_MAX_TIME_DECIDING]=3;
		this.mummyBlueParameters[Config.INDEX_DECICION_FACTOR]=0.5f;
		this.mummyBlueParameters[Config.INDEX_DECICION_FACTOR_JUMP]=0.25f;
		this.mummyBlueParameters[Config.INDEX_BEST_DECICION_PROBALITY]=0.5f;
	
			
		
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

	public float getMummyWhiteSpeedWalk()
	{
		return mummyWhiteSpeedWalk;
	}

	public void setMummyWhiteSpeedWalk(float mummyWhiteSpeedWalk)
	{
		this.mummyWhiteSpeedWalk = mummyWhiteSpeedWalk;
	}

	public float getMummyWhiteSpeedWalkStairs()
	{
		return mummyWhiteSpeedWalkStairs;
	}

	public void setMummyWhiteSpeedWalkStairs(float mummyWhiteSpeedWalkStairs)
	{
		this.mummyWhiteSpeedWalkStairs = mummyWhiteSpeedWalkStairs;
	}

	public float getMummyBlueSpeedWalk()
	{
		return mummyBlueSpeedWalk;
	}

	public void setMummyBlueSpeedWalk(float mummyBlueSpeedWalk)
	{
		this.mummyBlueSpeedWalk = mummyBlueSpeedWalk;
	}

	public float getMummyBlueSpeedWalkStairs()
	{
		return mummyBlueSpeedWalkStairs;
	}

	public void setMummyBlueSpeedWalkStairs(float mummyBlueSpeedWalkStairs)
	{
		this.mummyBlueSpeedWalkStairs = mummyBlueSpeedWalkStairs;
	}

	public float getMummyOrangeSpeedWalk()
	{
		return mummyOrangeSpeedWalk;
	}

	public void setMummyOrangeSpeedWalk(float mummyOrangeSpeedWalk)
	{
		this.mummyOrangeSpeedWalk = mummyOrangeSpeedWalk;
	}

	public float getMummyOrangeSpeedWalkStairs()
	{
		return mummyOrangeSpeedWalkStairs;
	}

	public void setMummyOrangeSpeedWalkStairs(float mummyOrangeSpeedWalkStairs)
	{
		this.mummyOrangeSpeedWalkStairs = mummyOrangeSpeedWalkStairs;
	}

	public float getMummyRedSpeedWalk()
	{
		return mummyRedSpeedWalk;
	}

	public void setMummyRedSpeedWalk(float mummyRedSpeedWalk)
	{
		this.mummyRedSpeedWalk = mummyRedSpeedWalk;
	}

	public float getMummyRedSpeedWalkStairs()
	{
		return mummyRedSpeedWalkStairs;
	}

	public void setMummyRedSpeedWalkStairs(float mummyRedSpeedWalkStairs)
	{
		this.mummyRedSpeedWalkStairs = mummyRedSpeedWalkStairs;
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

	public float getMummyWhiteDecisionFactor()
	{
		return mummyWhiteDecisionFactor;
	}

	public void setMummyWhiteDecisionFactor(float mummyWhiteDecisionFactor)
	{
		this.mummyWhiteDecisionFactor = mummyWhiteDecisionFactor;
	}

	public float getMummyWhiteMinTimeToDecide()
	{
		return mummyWhiteMinTimeToDecide;
	}

	public void setMummyWhiteMinTimeToDecide(float mummyWhiteMinTimeToDecide)
	{
		this.mummyWhiteMinTimeToDecide = mummyWhiteMinTimeToDecide;
	}

	public float getMummyWhiteMaxTimeToDecide()
	{
		return mummyWhiteMaxTimeToDecide;
	}

	public void setMummyWhiteMaxTimeToDecide(float mummyWhiteMaxTimeToDecide)
	{
		this.mummyWhiteMaxTimeToDecide = mummyWhiteMaxTimeToDecide;
	}

	public float getMummyBlueDecisionFactor()
	{
		return mummyBlueDecisionFactor;
	}

	public void setMummyBlueDecisionFactor(float mummyBlueDecisionFactor)
	{
		this.mummyBlueDecisionFactor = mummyBlueDecisionFactor;
	}

	public float getMummyBlueMinTimeToDecide()
	{
		return mummyBlueMinTimeToDecide;
	}

	public void setMummyBlueMinTimeToDecide(float mummyBlueMinTimeToDecide)
	{
		this.mummyBlueMinTimeToDecide = mummyBlueMinTimeToDecide;
	}

	public float getMummyBlueMaxTimeToDecide()
	{
		return mummyBlueMaxTimeToDecide;
	}

	public void setMummyBlueMaxTimeToDecide(float mummyBlueMaxTimeToDecide)
	{
		this.mummyBlueMaxTimeToDecide = mummyBlueMaxTimeToDecide;
	}

	public float getMummyYellowSpeedWalk()
	{
		return mummyYellowSpeedWalk;
	}

	public void setMummyYellowSpeedWalk(float mummyYellowSpeedWalk)
	{
		this.mummyYellowSpeedWalk = mummyYellowSpeedWalk;
	}

	public float getMummyYellowSpeedWalkStairs()
	{
		return mummyYellowSpeedWalkStairs;
	}

	public void setMummyYellowSpeedWalkStairs(float mummyYellowSpeedWalkStairs)
	{
		this.mummyYellowSpeedWalkStairs = mummyYellowSpeedWalkStairs;
	}

	public float getMummyYellowDecisionFactor()
	{
		return mummyYellowDecisionFactor;
	}

	public void setMummyYellowDecisionFactor(float mummyYellowDecisionFactor)
	{
		this.mummyYellowDecisionFactor = mummyYellowDecisionFactor;
	}

	public float getMummyYellowMinTimeToDecide()
	{
		return mummyYellowMinTimeToDecide;
	}

	public void setMummyYellowMinTimeToDecide(float mummyYellowMinTimeToDecide)
	{
		this.mummyYellowMinTimeToDecide = mummyYellowMinTimeToDecide;
	}

	public float getMummyYellowMaxTimeToDecide()
	{
		return mummyYellowMaxTimeToDecide;
	}

	public void setMummyYellowMaxTimeToDecide(float mummyYellowMaxTimeToDecide)
	{
		this.mummyYellowMaxTimeToDecide = mummyYellowMaxTimeToDecide;
	}

	public float getMummyOrangeDecisionFactor()
	{
		return mummyOrangeDecisionFactor;
	}

	public void setMummyOrangeDecisionFactor(float mummyOrangeDecisionFactor)
	{
		this.mummyOrangeDecisionFactor = mummyOrangeDecisionFactor;
	}

	public float getMummyOrangeMinTimeToDecide()
	{
		return mummyOrangeMinTimeToDecide;
	}

	public void setMummyOrangeMinTimeToDecide(float mummyOrangeMinTimeToDecide)
	{
		this.mummyOrangeMinTimeToDecide = mummyOrangeMinTimeToDecide;
	}

	public float getMummyOrangeMaxTimeToDecide()
	{
		return mummyOrangeMaxTimeToDecide;
	}

	public void setMummyOrangeMaxTimeToDecide(float mummyOrangeMaxTimeToDecide)
	{
		this.mummyOrangeMaxTimeToDecide = mummyOrangeMaxTimeToDecide;
	}

	public float getMummyRedDecisionFactor()
	{
		return mummyRedDecisionFactor;
	}

	public void setMummyRedDecisionFactor(float mummyRedDecisionFactor)
	{
		this.mummyRedDecisionFactor = mummyRedDecisionFactor;
	}

	public float getMummyRedMinTimeToDecide()
	{
		return mummyRedMinTimeToDecide;
	}

	public void setMummyRedMinTimeToDecide(float mummyRedMinTimeToDecide)
	{
		this.mummyRedMinTimeToDecide = mummyRedMinTimeToDecide;
	}

	public float getMummyRedMaxTimeToDecide()
	{
		return mummyRedMaxTimeToDecide;
	}

	public void setMummyRedMaxTimeToDecide(float mummyRedMaxTimeToDecide)
	{
		this.mummyRedMaxTimeToDecide = mummyRedMaxTimeToDecide;
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

	public float getMummyWhiteMinTimeDeciding()
	{
		return mummyWhiteMinTimeDeciding;
	}

	public float getMummyWhiteMaxTimeDeciding()
	{
		return mummyWhiteMaxTimeDeciding;
	}

	public float getMummyBlueMinTimeDeciding()
	{
		return mummyBlueMinTimeDeciding;
	}

	public float getMummyBlueMaxTimeDeciding()
	{
		return mummyBlueMaxTimeDeciding;
	}

	public float getMummyYellowMinTimeDeciding()
	{
		return mummyYellowMinTimeDeciding;
	}

	public float getMummyYellowMaxTimeDeciding()
	{
		return mummyYellowMaxTimeDeciding;
	}

	public float getMummyOrangeMinTimeDeciding()
	{
		return mummyOrangeMinTimeDeciding;
	}

	public float getMummyOrangeMaxTimeDeciding()
	{
		return mummyOrangeMaxTimeDeciding;
	}

	public float getMummyRedMinTimeDeciding()
	{
		return mummyRedMinTimeDeciding;
	}

	public float getMummyRedMaxTimeDeciding()
	{
		return mummyRedMaxTimeDeciding;
	}

	public void setMummyWhiteMinTimeDeciding(float mummyWhiteMinTimeDeciding)
	{
		this.mummyWhiteMinTimeDeciding = mummyWhiteMinTimeDeciding;
	}

	public void setMummyWhiteMaxTimeDeciding(float mummyWhiteMaxTimeDeciding)
	{
		this.mummyWhiteMaxTimeDeciding = mummyWhiteMaxTimeDeciding;
	}

	public void setMummyBlueMinTimeDeciding(float mummyBlueMinTimeDeciding)
	{
		this.mummyBlueMinTimeDeciding = mummyBlueMinTimeDeciding;
	}

	public void setMummyBlueMaxTimeDeciding(float mummyBlueMaxTimeDeciding)
	{
		this.mummyBlueMaxTimeDeciding = mummyBlueMaxTimeDeciding;
	}

	public void setMummyYellowMinTimeDeciding(float mummyYellowMinTimeDeciding)
	{
		this.mummyYellowMinTimeDeciding = mummyYellowMinTimeDeciding;
	}

	public void setMummyYellowMaxTimeDeciding(float mummyYellowMaxTimeDeciding)
	{
		this.mummyYellowMaxTimeDeciding = mummyYellowMaxTimeDeciding;
	}

	public void setMummyOrangeMinTimeDeciding(float mummyOrangeMinTimeDeciding)
	{
		this.mummyOrangeMinTimeDeciding = mummyOrangeMinTimeDeciding;
	}

	public void setMummyOrangeMaxTimeDeciding(float mummyOrangeMaxTimeDeciding)
	{
		this.mummyOrangeMaxTimeDeciding = mummyOrangeMaxTimeDeciding;
	}

	public void setMummyRedMinTimeDeciding(float mummyRedMinTimeDeciding)
	{
		this.mummyRedMinTimeDeciding = mummyRedMinTimeDeciding;
	}

	public void setMummyRedMaxTimeDeciding(float mummyRedMaxTimeDeciding)
	{
		this.mummyRedMaxTimeDeciding = mummyRedMaxTimeDeciding;
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

}
