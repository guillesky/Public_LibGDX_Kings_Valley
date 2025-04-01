package vista2D;

public class GraphicsFileConfig
{
    private String archiPlayer = "pics/vick.png";
    private String archiPickingCell = "pics/picking_cell.png";
    private String archiCollectables = "pics/colectables.png";
    private String archiGiratory3 = "pics/giratory3.png";
    private String archiMummyBlue = "pics/mummy_blue.png";
    private String archiMummyOrange = "pics/mummy_orange.png";
    private String archiMummyRed = "pics/mummy_red.png";
    private String archiMummyWhite = "pics/mummy_white.png";
    private String archiMummyYellow = "pics/mummy_yellow.png";
    private String archiMummyAppear = "pics/mummy_appear.png";
    private String archiMummyDisappear = "pics/mummy_disappear.png";

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
    private int giratory3Height = 30;
    private int giratory2Height = 20;
    private int collectableCount = 7;
    private int collectableTileWidth = 10;
    private int collectableTileHeight = 10;

    private int playerStartIddle = 2;
    private int playerCountIddle = 1;
    private int playerStartFall = 5;
    private int playerCountFall = 1;

    private int playerStartWalk = 0;
    private int playerCountWalk = 5;
    private int playerStartDeath = 21;
    private int playerCountDeath = 4;

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

    public String getArchiGiratory3()
    {
	return archiGiratory3;
    }

    public String getArchiMummyBlue()
    {
	return archiMummyBlue;
    }

    public String getArchiMummyOrange()
    {
	return archiMummyOrange;
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

    public int getGiratory3Height()
    {
	return giratory3Height;
    }

    public int getGiratory2Height()
    {
	return giratory2Height;
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

}
