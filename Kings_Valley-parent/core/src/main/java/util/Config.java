package util;

public class Config
{
    private float characterSpeedFall = -600;
    private float playerSpeedWalk = 60;
    private float playerSpeedWalkStairs = 60;
    private float playerSpeedJump = 160;

    private float mummyWhiteSpeedWalk = 60;
    private float mummyWhiteSpeedWalkStairs = 60;
    private float mummyWhiteSpeedJump = 160;

    private float mummyBlueSpeedWalk = 60;
    private float mummyBlueSpeedWalkStairs = 60;
    private float mummyBlueSpeedJump = 160;

    private float mummyOrangeSpeedWalk = 60;
    private float mummyOrangeSpeedWalkStairs = 60;
    private float mummyOrangeSpeedJump = 160;

    private float mummyRedSpeedWalk = 60;
    private float mummyRedSpeedWalkStairs = 60;
    private float mummyRedSpeedJump = 160;

    private float characterWidth = 6;
    private float characterHeight = 18;
    private float characterFeetWidth = 1;
    private float characterFeetHeight = 1;

    private float levelItemWidth = 10;
    private float levelItemHeight = 10;

    private float stairWidth = 5;
    private float stairHeight = 2;

    private float giratoryWidth = 13;
    private float giratoryHeight = 30;
    
    private float levelTileWidthUnits = 10;
    private float levelTileHeightUnits = 10;
    private float speedGame = 1;

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
	
	this.playerSpeedWalk = (int) (6 * this.levelTileWidthUnits);
	this.playerSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
	this.playerSpeedJump = (int) (this.levelTileHeightUnits * (16));

	this.mummyWhiteSpeedWalk = (int) (6 * this.levelTileWidthUnits);
	this.mummyWhiteSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
	this.mummyWhiteSpeedJump = (int) (this.levelTileHeightUnits * (16));

	this.mummyBlueSpeedWalk = (int) (6 * this.levelTileWidthUnits);
	this.mummyBlueSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
	this.mummyBlueSpeedJump = (int) (this.levelTileHeightUnits * (16));

	this.mummyOrangeSpeedWalk = (int) (6 * this.levelTileWidthUnits);
	this.mummyOrangeSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
	this.mummyOrangeSpeedJump = (int) (this.levelTileHeightUnits * (16));

	this.mummyRedSpeedWalk = (int) (6 * this.levelTileWidthUnits);
	this.mummyRedSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
	this.mummyRedSpeedJump = (int) (this.levelTileHeightUnits * (16));

	this.characterWidth = this.levelTileWidthUnits * 0.6f;
	this.characterHeight = this.levelTileHeightUnits * 1.8f;
	this.characterFeetHeight = this.levelTileHeightUnits * 0.1f;
	this.characterFeetWidth = this.levelTileWidthUnits * 0.1f;

	;

	this.levelItemWidth = this.levelTileWidthUnits;
	this.levelItemHeight = this.levelTileHeightUnits;

	this.stairWidth = this.levelTileWidthUnits * 0.5f;
	this.stairHeight = this.levelTileHeightUnits * 0.2f;

	this.giratoryWidth = this.levelTileWidthUnits * 1.3f;
	this.giratoryHeight = this.levelTileHeightUnits * 3.0f;

    
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

    public float getPlayerSpeedJump()
    {
	return playerSpeedJump;
    }

    public void setPlayerSpeedJump(float playerSpeedJump)
    {
	this.playerSpeedJump = playerSpeedJump;
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

    public float getMummyWhiteSpeedJump()
    {
	return mummyWhiteSpeedJump;
    }

    public void setMummyWhiteSpeedJump(float mummyWhiteSpeedJump)
    {
	this.mummyWhiteSpeedJump = mummyWhiteSpeedJump;
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

    public float getMummyBlueSpeedJump()
    {
	return mummyBlueSpeedJump;
    }

    public void setMummyBlueSpeedJump(float mummyBlueSpeedJump)
    {
	this.mummyBlueSpeedJump = mummyBlueSpeedJump;
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

    public float getMummyOrangeSpeedJump()
    {
	return mummyOrangeSpeedJump;
    }

    public void setMummyOrangeSpeedJump(float mummyOrangeSpeedJump)
    {
	this.mummyOrangeSpeedJump = mummyOrangeSpeedJump;
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

    public float getMummyRedSpeedJump()
    {
	return mummyRedSpeedJump;
    }

    public void setMummyRedSpeedJump(float mummyRedSpeedJump)
    {
	this.mummyRedSpeedJump = mummyRedSpeedJump;
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

    public float getLevelItemWidth()
    {
	return levelItemWidth;
    }

    public void setLevelItemWidth(float levelItemWidth)
    {
	this.levelItemWidth = levelItemWidth;
    }

    public float getLevelItemHeight()
    {
	return levelItemHeight;
    }

    public void setLevelItemHeight(float levelItemHeight)
    {
	this.levelItemHeight = levelItemHeight;
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

	public float getGiratoryHeight()
	{
		return giratoryHeight;
	}

	public void setGiratoryHeight(float giratoryHeight)
	{
		this.giratoryHeight = giratoryHeight;
	}

}
