package util;

public class Config
{
    private float characterSpeedFall ;
    private float characterSpeedJump ;
    
    private float playerSpeedWalk ;
    private float playerSpeedWalkStairs ;
    

    private float mummyWhiteSpeedWalk ;
    private float mummyWhiteSpeedWalkStairs;
    
    private float mummyWhiteDecisionFactor;
    private float mummyWhiteMinTimeToDecide;
    private float mummyWhiteMaxTimeToDecide;

    private float mummyBlueSpeedWalk ;
    private float mummyBlueSpeedWalkStairs;
    
    private float mummyBlueDecisionFactor;
    private float mummyBlueMinTimeToDecide;
    private float mummyBlueMaxTimeToDecide;

    private float mummyYellowSpeedWalk;
    private float mummyYellowSpeedWalkStairs;
    
    private float mummyYellowDecisionFactor;
    private float mummyYellowMinTimeToDecide;
    private float mummyYellowMaxTimeToDecide;

    private float mummyOrangeSpeedWalk ;
    private float mummyOrangeSpeedWalkStairs;
    
    private float mummyOrangeDecisionFactor;
    private float mummyOrangeTimeToDecide;
   
    private float mummyRedSpeedWalk;
    private float mummyRedSpeedWalkStairs ;
    
    private float mummyRedDecisionFactor;
    private float mummyRedMinTimeToDecide;
    private float mummyRedMaxTimeToDecide;

    private float characterWidth ;
    private float characterHeight ;
    private float characterFeetWidth;
    private float characterFeetHeight ;

    private float levelItemWidth;
    private float levelItemHeight;

    private float stairWidth ;
    private float stairHeight ;

    private float giratoryWidth;

    private float levelTileWidthUnits;
    private float levelTileHeightUnits;
    private float speedGame=1.0f;

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
	this.characterSpeedJump = (int) (this.levelTileHeightUnits * (16));
	
	this.playerSpeedWalk = (int) (6 * this.levelTileWidthUnits);
	this.playerSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
	

	this.mummyWhiteSpeedWalk = (int) (2.5 * this.levelTileWidthUnits);
	this.mummyWhiteSpeedWalkStairs = (int) (3 * this.levelTileWidthUnits);
	this.mummyWhiteDecisionFactor=0.5f;
	this.mummyWhiteMinTimeToDecide=5;
	this.mummyWhiteMaxTimeToDecide=10;

	this.mummyBlueSpeedWalk = (int) (4.5 * this.levelTileWidthUnits);
	this.mummyBlueSpeedWalkStairs = (int) (5 * this.levelTileWidthUnits);
	this.mummyBlueDecisionFactor=0.5f;
	this.mummyBlueMinTimeToDecide=5;
	this.mummyBlueMaxTimeToDecide=8;
	
	
	this.mummyYellowSpeedWalk = (int) (5 * this.levelTileWidthUnits);
	this.mummyYellowSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
	this.mummyYellowDecisionFactor=0.3f;
	this.mummyYellowMinTimeToDecide=5;
	this.mummyYellowMaxTimeToDecide=6;
	
	
	
	
	

	this.mummyOrangeSpeedWalk = (int) (6 * this.levelTileWidthUnits);
	this.mummyOrangeSpeedWalkStairs = (int) (7 * this.levelTileWidthUnits);
	this.mummyOrangeDecisionFactor=0.4f;
	this.mummyOrangeTimeToDecide=5;
	

	this.mummyRedSpeedWalk = (int) (6.5 * this.levelTileWidthUnits);
	this.mummyRedSpeedWalkStairs = (int) (7.5 * this.levelTileWidthUnits);
	this.mummyRedDecisionFactor=0.5f;
	this.mummyRedMinTimeToDecide=3;
	this.mummyRedMaxTimeToDecide=5;

	this.characterWidth = this.levelTileWidthUnits * 0.6f;
	this.characterHeight = this.levelTileHeightUnits * 1.8f;
	this.characterFeetHeight = this.levelTileHeightUnits * 0.1f;
	this.characterFeetWidth = this.levelTileWidthUnits * 0.1f;

	this.levelItemWidth = this.levelTileWidthUnits;
	this.levelItemHeight = this.levelTileHeightUnits;

	this.stairWidth = this.levelTileWidthUnits * 0.5f;
	this.stairHeight = this.levelTileHeightUnits * 0.2f;

	this.giratoryWidth = this.levelTileWidthUnits * 1.3f;

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

    public float getMummyOrangeTimeToDecide()
    {
        return mummyOrangeTimeToDecide;
    }

    public void setMummyOrangeTimeToDecide(float mummyOrangeMinTimeToDecide)
    {
        this.mummyOrangeTimeToDecide = mummyOrangeMinTimeToDecide;
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
    

}
