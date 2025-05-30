package util;

public class Config
{

	private static final int INDEX_SPEED_WALK = 0;
	private static final int INDEX_SPEED_STAIR = 1;
	private static final int INDEX_TIME_TO_DECIDE = 2;
	private static final int INDEX_TIME_DECIDING = 3;
	private static final int INDEX_DECICION_FACTOR_FALL = 4;
	private static final int INDEX_DECICION_FACTOR_JUMP = 5;
	
	

    private float characterSpeedFall;
    private float characterSpeedJump;

    private float playerSpeedWalk;
    private float playerSpeedWalkStairs;

    private float minMummySpawnDistanceToPlayer;
    private float mummyTimeInLimbus;
    private float mummyTimeDying;
    private float mummyTimeAppearing;

    private float[] mummyWhiteParameters;
    private float[] mummyBlueParameters;
    private float[] mummyYellowParameters;
    private float[] mummyPinkParameters;
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
    private float timeToEndGiratory = 1f;
    private float timeToEndTrapMechanism = 1f;
    private float timeToOpenCloseDoor = 1f;

    private float speedGame = 1.0f;

    private static final Config instance = new Config();

    private Config()
    {
	this.defaultValues(64, 64);
    }

    public void defaultValues(float levelTileWidthUnits, float levelTileHeightUnits)
    {
	this.levelTileWidthUnits = levelTileWidthUnits;
	this.levelTileHeightUnits = levelTileHeightUnits;

	this.characterSpeedFall = (int) (this.levelTileHeightUnits * (-60));
	this.characterSpeedJump = (int) (this.levelTileHeightUnits * (15.3));

	this.playerSpeedWalk = (int) (6 * this.levelTileWidthUnits);
	this.playerSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
	this.flyingDaggerSpeed = this.levelTileWidthUnits * 18;
	this.flyingDaggerSpeedFall = -this.flyingDaggerSpeed * 0.5f;

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

	this.mummyWhiteParameters = new float[10];
	this.mummyBlueParameters = new float[10];
	this.mummyYellowParameters = new float[10];
	this.mummyPinkParameters = new float[10];
	this.mummyRedParameters = new float[10];

	this.minMummySpawnDistanceToPlayer = 64;
	this.mummyTimeAppearing = 2;
	this.mummyTimeDying = 1;
	this.mummyTimeInLimbus = 5;
	this.mummyWhiteParameters[Config.INDEX_SPEED_WALK] = this.playerSpeedWalk*0.5f;
	this.mummyWhiteParameters[Config.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs*0.5f;
	this.mummyWhiteParameters[Config.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyWhiteParameters[Config.INDEX_TIME_DECIDING] = 1f;
	this.mummyWhiteParameters[Config.INDEX_DECICION_FACTOR_FALL] =0.5f;
	this.mummyWhiteParameters[Config.INDEX_DECICION_FACTOR_JUMP] =0.5f;
	   
	
	this.mummyPinkParameters[Config.INDEX_SPEED_WALK] = this.playerSpeedWalk*0.5f;
	this.mummyPinkParameters[Config.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs*2f;
	this.mummyPinkParameters[Config.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyPinkParameters[Config.INDEX_TIME_DECIDING] =1f;
	this.mummyPinkParameters[Config.INDEX_DECICION_FACTOR_FALL] =0.5f;
	this.mummyPinkParameters[Config.INDEX_DECICION_FACTOR_JUMP] =0.5f;
	   
	
	this.mummyYellowParameters[Config.INDEX_SPEED_WALK] = this.playerSpeedWalk;
	this.mummyYellowParameters[Config.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs*0.5f;
	this.mummyYellowParameters[Config.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyYellowParameters[Config.INDEX_TIME_DECIDING] = 1f;
	this.mummyYellowParameters[Config.INDEX_DECICION_FACTOR_FALL] =0.5f;
	this.mummyYellowParameters[Config.INDEX_DECICION_FACTOR_JUMP] =0.5f;
	   
	this.mummyBlueParameters[Config.INDEX_SPEED_WALK] = this.playerSpeedWalk;
	this.mummyBlueParameters[Config.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs;
	this.mummyBlueParameters[Config.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyBlueParameters[Config.INDEX_TIME_DECIDING] = 0f;
	this.mummyBlueParameters[Config.INDEX_DECICION_FACTOR_FALL] =0.5f;
	this.mummyBlueParameters[Config.INDEX_DECICION_FACTOR_JUMP] =0.5f;
	   
	
	this.mummyRedParameters[Config.INDEX_SPEED_WALK] = this.playerSpeedWalk;
	this.mummyRedParameters[Config.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs*2f;
	this.mummyRedParameters[Config.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyRedParameters[Config.INDEX_TIME_DECIDING] = 0f;
	this.mummyRedParameters[Config.INDEX_DECICION_FACTOR_FALL] =0.5f;
	this.mummyRedParameters[Config.INDEX_DECICION_FACTOR_JUMP] =0.5f;
    
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

    public float[] getMummyPinkParameters()
    {

	return this.mummyPinkParameters;
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

    public void setMummyPinkParameters(float[] mummyPinkParameters)
    {
	this.mummyPinkParameters = mummyPinkParameters;
    }

    public void setMummyRedParameters(float[] mummyRedParameters)
    {
	this.mummyRedParameters = mummyRedParameters;
    }

    public float getMummyTimeInLimbus()
    {
	return mummyTimeInLimbus;
    }

    public void setMummyTimeInLimbus(float mummyTimeInLimbus)
    {
	this.mummyTimeInLimbus = mummyTimeInLimbus;
    }

    public float getMummyTimeDying()
    {
	return mummyTimeDying;
    }

    public void setMummyTimeDying(float mummyTimeDying)
    {
	this.mummyTimeDying = mummyTimeDying;
    }

    public float getMummyTimeAppearing()
    {
	return mummyTimeAppearing;
    }

    public void setMummyTimeAppearing(float mummyTimeAppearing)
    {
	this.mummyTimeAppearing = mummyTimeAppearing;
    }

    public float getTimeToEndGiratory()
    {
        return timeToEndGiratory;
    }

    public void setTimeToEndGiratory(float timeToEndGiratory)
    {
        this.timeToEndGiratory = timeToEndGiratory;
    }

    public float getTimeToEndTrapMechanism()
    {
        return timeToEndTrapMechanism;
    }

    public void setTimeToEndTrapMechanism(float timeToEndTrapMechanism)
    {
        this.timeToEndTrapMechanism = timeToEndTrapMechanism;
    }

    public float getTimeToOpenCloseDoor()
    {
        return timeToOpenCloseDoor;
    }

    public void setTimeToOpenCloseDoor(float timeToOpenCloseDoor)
    {
        this.timeToOpenCloseDoor = timeToOpenCloseDoor;
    }

}
