package modelo.level.door;

import modelo.level.LevelObject;
import modelo.level.Mechanism;
import util.Config;
import util.Constantes;

public class Door extends Mechanism
{
	public static final int CLOSED = 1;
	public static final int OPEN = 2;
	public static final int CLOSING = 1;
	public static final int OPENING = 2;

	private LevelObject lever;
	private LevelObject passage;
	private int state;
	private boolean visible;

	public Door(LevelObject lever)
	{
		this.lever = lever;
		float width = Config.getInstance().getLevelTileWidthUnits();
		float eight = Config.getInstance().getLevelTileHeightUnits();
		float x = this.lever.x + width * 2;
		float y = this.lever.y - eight * 2;
		this.state = CLOSED;
		this.passage = new LevelObject(Constantes.It_door_passage, x, y, this.lever.getP0(), width * 2, eight * 2);
		this.active = false;
		this.visible=false;
	}

	public LevelObject getLever()
	{
		return lever;
	}

	public LevelObject getPassage()
	{
		return passage;
	}

	@Override
	public void update(float deltaTime)
	{

	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

}
