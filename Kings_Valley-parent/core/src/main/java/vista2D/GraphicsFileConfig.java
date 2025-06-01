package vista2D;

public class GraphicsFileConfig
{
    private String archiPlayer;
    private String archiPickingCell;
    private String archiCollectables; 
    private String archiGiratory ;
    private String archiMummyBlue ;
    private String archiMummyPink ;
    private String archiMummyRed ;
    private String archiMummyWhite; 
    private String archiMummyYellow; 
    private String archiMummyAppear ;
    private String archiMummyDisappear;
    private String archiFlyingDagger ;
    private String archiDoorPassage;
    private String archiDoorLeft ; 
    private String archiDoorRight ;
    private String archiDoorLever ;
    private String archiNewTileset;
    private String archiSky;
    
    

    private float frameDuration ;

    private int mummyStartIddle ;
    private int mummyCountIddle ;
    private float mummyIddleFrameDuration ;
    private int mummyStartFall ;
    private int mummyCountFall ;

    private int mummyStartWalk ;
    private int mummyCountWalk ;
    private int mummyCountAppear ;
    private int mummyCountDeath ;

    private int giratoryCount ;
    
    
    
    private int collectableCount ;
  
    private int playerStartIddle ;
    private int playerCountIddle ;
    private int playerStartFall ;
    private int playerCountFall;

    private int playerStartWalk ;
    private int playerCountWalk;
    
    private int playerPickerStartIddle ;
    private int playerPickerCountIddle ;
    private int playerPickerStartFall ;
    private int playerPickerCountFall ;

    private int playerPickerStartWalk ;
    private int playerPickerCountWalk ;

    private int playerPickerStartPicking ;
    private int playerPickerCountPicking;

    
    private int playerDaggerStartIddle ;
    private int playerDaggerCountIddle ;
    private int playerDaggerStartFall ;
    private int playerDaggerCountFall ;

    private int playerDaggerStartWalk ;
    private int playerDaggerCountWalk;

    private int playerDaggerStartThrowing ;
    private int playerDaggerCountThrowing ;
    
    private int playerStartDeath ;
    private int playerCountDeath ;
    
    private int flyingDaggerCount;
   private int pickingCellCount;
    
    
    private int doorLeverCount;
  
    
    
    
    

    
    
    
    
    public String getArchiNewTileset()
    {
        return archiNewTileset;
    }

    public String getArchiSky()
    {
        return archiSky;
    }

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

    

    public int getGiratoryCount()
    {
	return giratoryCount;
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

  

    protected int getPickingCellCount()
    {
        return pickingCellCount;
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

	public int getDoorLeverCount()
	{
	    return doorLeverCount;
	}


	

}
