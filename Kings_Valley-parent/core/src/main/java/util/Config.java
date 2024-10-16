package util;

public class Config
{
    private int characterSpeedFall = -600;
    private int playerSpeedWalk = 60;
    private int playerSpeedWalkStairs = 60;
    private int playerSpeedJump = 160;

    private int mummyWhiteSpeedWalk = 60;
    private int mummyWhiteSpeedWalkStairs = 60;
    private int mummyWhiteSpeedJump = 160;

    private int mummyBlueSpeedWalk = 60;
    private int mummyBlueSpeedWalkStairs = 60;
    private int mummyBlueSpeedJump = 160;

    private int mummyOrangeSpeedWalk = 60;
    private int mummyOrangeSpeedWalkStairs = 60;
    private int mummyOrangeSpeedJump = 160;

    private int mummyRedSpeedWalk = 60;
    private int mummyRedSpeedWalkStairs = 60;
    private int mummyRedSpeedJump = 160;

    private float characterWidth = 6;
    private float characterHeight = 18;
    private float characterFeetWidth = 1;
    private float characterFeetHeight = 1;
    

    private float levelItemWidth = 10;
    private float levelItemHeight = 10;

    private float stairWidth = 5;
    private float stairHeight = 2;

    private float levelTileWidthUnits = 10;
    private float levelTileHeightUnits = 10;
    private float speedGame=1;

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
	this.characterFeetHeight=this.levelTileHeightUnits * 0.1f;
	this.characterFeetWidth=this.levelTileWidthUnits * 0.1f;
	
	;

	this.levelItemWidth = this.levelTileWidthUnits;
	this.levelItemHeight = this.levelTileHeightUnits;

	this.stairWidth = this.levelTileWidthUnits * 0.5f;
	this.stairHeight = this.levelTileHeightUnits * 0.2f;

    }

    public int getCharacterSpeedFall()
    {
	return characterSpeedFall;
    }

    public void setCharacterSpeedFall(int characterSpeedFall)
    {
	this.characterSpeedFall = characterSpeedFall;
    }

    public int getPlayerSpeedWalk()
    {
	return playerSpeedWalk;
    }

    public void setPlayerSpeedWalk(int playerSpeedWalk)
    {
	this.playerSpeedWalk = playerSpeedWalk;
    }

    public int getPlayerSpeedWalkStairs()
    {
	return playerSpeedWalkStairs;
    }

    public void setPlayerSpeedWalkStairs(int playerSpeedWalkStairs)
    {
	this.playerSpeedWalkStairs = playerSpeedWalkStairs;
    }

    public int getPlayerSpeedJump()
    {
	return playerSpeedJump;
    }

    public void setPlayerSpeedJump(int playerSpeedJump)
    {
	this.playerSpeedJump = playerSpeedJump;
    }

    public int getMummyWhiteSpeedWalk()
    {
	return mummyWhiteSpeedWalk;
    }

    public void setMummyWhiteSpeedWalk(int mummyWhiteSpeedWalk)
    {
	this.mummyWhiteSpeedWalk = mummyWhiteSpeedWalk;
    }

    public int getMummyWhiteSpeedWalkStairs()
    {
	return mummyWhiteSpeedWalkStairs;
    }

    public void setMummyWhiteSpeedWalkStairs(int mummyWhiteSpeedWalkStairs)
    {
	this.mummyWhiteSpeedWalkStairs = mummyWhiteSpeedWalkStairs;
    }

    public int getMummyWhiteSpeedJump()
    {
	return mummyWhiteSpeedJump;
    }

    public void setMummyWhiteSpeedJump(int mummyWhiteSpeedJump)
    {
	this.mummyWhiteSpeedJump = mummyWhiteSpeedJump;
    }

    public int getMummyBlueSpeedWalk()
    {
	return mummyBlueSpeedWalk;
    }

    public void setMummyBlueSpeedWalk(int mummyBlueSpeedWalk)
    {
	this.mummyBlueSpeedWalk = mummyBlueSpeedWalk;
    }

    public int getMummyBlueSpeedWalkStairs()
    {
	return mummyBlueSpeedWalkStairs;
    }

    public void setMummyBlueSpeedWalkStairs(int mummyBlueSpeedWalkStairs)
    {
	this.mummyBlueSpeedWalkStairs = mummyBlueSpeedWalkStairs;
    }

    public int getMummyBlueSpeedJump()
    {
	return mummyBlueSpeedJump;
    }

    public void setMummyBlueSpeedJump(int mummyBlueSpeedJump)
    {
	this.mummyBlueSpeedJump = mummyBlueSpeedJump;
    }

    public int getMummyOrangeSpeedWalk()
    {
	return mummyOrangeSpeedWalk;
    }

    public void setMummyOrangeSpeedWalk(int mummyOrangeSpeedWalk)
    {
	this.mummyOrangeSpeedWalk = mummyOrangeSpeedWalk;
    }

    public int getMummyOrangeSpeedWalkStairs()
    {
	return mummyOrangeSpeedWalkStairs;
    }

    public void setMummyOrangeSpeedWalkStairs(int mummyOrangeSpeedWalkStairs)
    {
	this.mummyOrangeSpeedWalkStairs = mummyOrangeSpeedWalkStairs;
    }

    public int getMummyOrangeSpeedJump()
    {
	return mummyOrangeSpeedJump;
    }

    public void setMummyOrangeSpeedJump(int mummyOrangeSpeedJump)
    {
	this.mummyOrangeSpeedJump = mummyOrangeSpeedJump;
    }

    public int getMummyRedSpeedWalk()
    {
	return mummyRedSpeedWalk;
    }

    public void setMummyRedSpeedWalk(int mummyRedSpeedWalk)
    {
	this.mummyRedSpeedWalk = mummyRedSpeedWalk;
    }

    public int getMummyRedSpeedWalkStairs()
    {
	return mummyRedSpeedWalkStairs;
    }

    public void setMummyRedSpeedWalkStairs(int mummyRedSpeedWalkStairs)
    {
	this.mummyRedSpeedWalkStairs = mummyRedSpeedWalkStairs;
    }

    public int getMummyRedSpeedJump()
    {
	return mummyRedSpeedJump;
    }

    public void setMummyRedSpeedJump(int mummyRedSpeedJump)
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

    
    
}
