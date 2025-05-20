package vista2D;

public class GraphicsFileConfig
{
    private String archiPlayer = "pics/vick.png";
    private String archiPickingCell = "pics/picking_cell.png";
    private String archiCollectables = "pics/collectables.png";
    private String archiGiratory = "pics/giratory.png";
    private String archiMummyBlue = "pics/mummy_blue.png";
    private String archiMummyPink = "pics/mummy_pink.png";
    private String archiMummyRed = "pics/mummy_red.png";
    private String archiMummyWhite = "pics/mummy_white.png";
    private String archiMummyYellow = "pics/mummy_yellow.png";
    private String archiMummyAppear = "pics/mummy_appear.png";
    private String archiMummyDisappear = "pics/mummy_disappear.png";
    private String archiFlyingDagger = "pics/knife.png";
    private String archiDoorPassage = "pics/exitdoor_central.png";
    private String archiDoorLeft = "pics/exitdoor_left.png";
    private String archiDoorRight = "pics/exitdoor_right.png";
    private String archiDoorLever = "pics/exitdoor_lever.png";
    
    

    private int CharacterFrameWidth = 16;
    private int CharacterFrameHeight = 20;
    private float frameDuration = 0.1f;

    private int mummyStartIddle = 9;
    private int mummyCountIddle = 2;
    private float mummyIddleFrameDuration = 0.3f;
    private int mummyStartFall = 8;
    private int mummyCountFall = 1;

    private int mummyStartWalk = 0;
    private int mummyCountWalk = 8;
    private int mummyCountAppear = 10;
    private int mummyCountDeath = 5;

    private int giratoryWidth = 20;
    private int giratoryHeight = 10;
    
    private int collectableCount = 7;
    private int collectableTileWidth = 10;
    private int collectableTileHeight = 10;

    private int playerStartIddle = 2;
    private int playerCountIddle = 1;
    private int playerStartFall = 5;
    private int playerCountFall = 1;

    private int playerStartWalk = 0;
    private int playerCountWalk = 5;
    
    private int playerPickerStartIddle = 8;
    private int playerPickerCountIddle = 1;
    private int playerPickerStartFall = 11;
    private int playerPickerCountFall = 1;

    private int playerPickerStartWalk = 6;
    private int playerPickerCountWalk = 5;

    private int playerPickerStartPicking = 19;
    private int playerPickerCountPicking = 2;

    
    private int playerDaggerStartIddle = 14;
    private int playerDaggerCountIddle = 1;
    private int playerDaggerStartFall = 17;
    private int playerDaggerCountFall = 1;

    private int playerDaggerStartWalk = 12;
    private int playerDaggerCountWalk = 5;

    private int playerDaggerStartThrowing = 17;
    private int playerDaggerCountThrowing = 2;
    
    private int playerStartDeath = 21;
    private int playerCountDeath = 4;
    
    private int flyingDaggerCount=8;
    private int flyingDaggerWidth=16;
    private int flyingDaggerHeight=20;
    private int pickingCellCount=4;
    private int doorLeverWidth=10;
    private int doorLeverHeight=10;;
    private int doorSingleWidth=10;
    private int doorSingleHeight=30;
    private int doorPassageWidth=20;
    private int doorPassageHeight=20;
    
    
    private float pickingCellFrameDuration=0.25f;
    

    
    public String getArchiPlayer()
    {
	return archiPlayer;
    }

    public String getArchiPickingCell()
    {
	return archiPickingCell;
    }

    public String getArchiCollectables()
    {
	return archiCollectables;
    }

    public String getArchiGiratory()
    {
	return archiGiratory;
    }

    public String getArchiMummyBlue()
    {
	return archiMummyBlue;
    }

    public String getArchiMummyPink()
    {
	return archiMummyPink;
    }

    public String getArchiMummyRed()
    {
	return archiMummyRed;
    }

    public String getArchiMummyWhite()
    {
	return archiMummyWhite;
    }

    public String getArchiMummyYellow()
    {
	return archiMummyYellow;
    }

    public String getArchiMummyAppear()
    {
	return archiMummyAppear;
    }

    public String getArchiMummyDisappear()
    {
	return archiMummyDisappear;
    }

    public int getMummyStartIddle()
    {
	return mummyStartIddle;
    }

    public int getMummyCountIddle()
    {
	return mummyCountIddle;
    }

    public int getMummyStartFall()
    {
	return mummyStartFall;
    }

    public int getMummyCountFall()
    {
	return mummyCountFall;
    }

    public int getMummyStartWalk()
    {
	return mummyStartWalk;
    }

    public int getMummyCountWalk()
    {
	return mummyCountWalk;
    }

    public int getGiratoryWidth()
    {
	return giratoryWidth;
    }

    public int getGiratoryHeight()
    {
	return giratoryHeight;
    }

    
    public int getCollectableTileWidth()
    {
	return collectableTileWidth;
    }

    public int getCollectableTileHeight()
    {
	return collectableTileHeight;
    }

    public int getPlayerStartIddle()
    {
	return playerStartIddle;
    }

    public int getPlayerCountIddle()
    {
	return playerCountIddle;
    }

    public int getPlayerStartFall()
    {
	return playerStartFall;
    }

    public int getPlayerCountFall()
    {
	return playerCountFall;
    }

    public int getPlayerStartWalk()
    {
	return playerStartWalk;
    }

    public int getPlayerCountWalk()
    {
	return playerCountWalk;
    }

    public int getPlayerStartDeath()
    {
	return playerStartDeath;
    }

    public int getPlayerCountDeath()
    {
	return playerCountDeath;
    }

    public int getCollectableCount()
    {
	return collectableCount;
    }

    public int getCharacterFrameWidth()
    {
	return CharacterFrameWidth;
    }

    public int getCharacterFrameHeight()
    {
	return CharacterFrameHeight;
    }

    public float getFrameDuration()
    {
	return frameDuration;
    }

    public int getMummyCountAppear()
    {
	return mummyCountAppear;
    }

    public int getMummyCountDeath()
    {
	return mummyCountDeath;
    }

    public float getMummyIddleFrameDuration()
    {
        return mummyIddleFrameDuration;
    }

    public int getPlayerPickerStartIddle()
    {
        return playerPickerStartIddle;
    }

    public int getPlayerPickerCountIddle()
    {
        return playerPickerCountIddle;
    }

    public int getPlayerPickerStartFall()
    {
        return playerPickerStartFall;
    }

    public int getPlayerPickerCountFall()
    {
        return playerPickerCountFall;
    }

    public int getPlayerPickerStartWalk()
    {
        return playerPickerStartWalk;
    }

    public int getPlayerPickerCountWalk()
    {
        return playerPickerCountWalk;
    }

    public int getPlayerPickerStartPicking()
    {
        return playerPickerStartPicking;
    }

    public int getPlayerPickerCountPicking()
    {
        return playerPickerCountPicking;
    }

    public int getPlayerDaggerStartIddle()
    {
        return playerDaggerStartIddle;
    }

    public int getPlayerDaggerCountIddle()
    {
        return playerDaggerCountIddle;
    }

    public int getPlayerDaggerStartFall()
    {
        return playerDaggerStartFall;
    }

    public int getPlayerDaggerCountFall()
    {
        return playerDaggerCountFall;
    }

    public int getPlayerDaggerStartWalk()
    {
        return playerDaggerStartWalk;
    }

    public int getPlayerDaggerCountWalk()
    {
        return playerDaggerCountWalk;
    }

    public int getPlayerDaggerStartThrowing()
    {
        return playerDaggerStartThrowing;
    }

    public int getPlayerDaggerCountThrowing()
    {
        return playerDaggerCountThrowing;
    }

    public String getArchiFlyingDagger()
    {
        return archiFlyingDagger;
    }

    protected int getFlyingDaggerCount()
    {
        return flyingDaggerCount;
    }

    protected int getFlyingDaggerWidth()
    {
        return flyingDaggerWidth;
    }

    protected int getFlyingDaggerHeight()
    {
        return flyingDaggerHeight;
    }

    protected int getPickingCellCount()
    {
        return pickingCellCount;
    }

    protected float getPickingCellFrameDuration()
    {
        return pickingCellFrameDuration;
    }

	public String getArchiDoorPassage()
	{
		return archiDoorPassage;
	}

	public String getArchiDoorLeft()
	{
		return archiDoorLeft;
	}

	public String getArchiDoorRight()
	{
		return archiDoorRight;
	}

	public String getArchiDoorLever()
	{
		return archiDoorLever;
	}

	public int getDoorLeverWidth()
	{
		return doorLeverWidth;
	}

	public int getDoorLeverHeight()
	{
		return doorLeverHeight;
	}

	public int getDoorSingleWidth()
	{
		return doorSingleWidth;
	}

	public int getDoorSingleHeight()
	{
		return doorSingleHeight;
	}

	public int getDoorPassageWidth()
	{
		return doorPassageWidth;
	}

	public int getDoorPassageHeight()
	{
		return doorPassageHeight;
	}
    

}
