package modelo;

import com.badlogic.gdx.math.Vector2;

import util.Constantes;

public class Character implements IGraphicRenderer
{
	protected static final int st_init = 0; // Inicializando
	protected static final int st_walk = 1; // Andando
	protected static final int st_onstairs = 2; // En una escalera
	protected static final int st_falling = 3; // Cayendo
	protected static final int st_jump_left = 4; // Saltando izquierda
	protected static final int st_jump_top = 5; // Saltando arriba
	protected static final int st_jump_right = 6; // Saltando derecha
	protected static final int st_dying = 7; // Muriendo

	protected IGraphicRenderer graphicRenderer = null;
	protected int state = Character.st_init;
	protected Vector2 inputVector = new Vector2();
	protected Vector2 snapVector = new Vector2();;
	protected Vector2 motionVector = new Vector2();
	protected int speedFall = 600;
	protected int speedWalk = 60;
	protected int speedWalkStairs = 60;
	protected int speedJump = 160;
	protected int stairInit = Constantes.It_none;
	/*
	 * 
	 * 
	 * key_action: bool = false
	 * 
	 * 
	 */

	public IGraphicRenderer getGraphicRenderer()
	{
		return graphicRenderer;
	}

	public void setGraphicRenderer(IGraphicRenderer graphicRenderer)
	{
		this.graphicRenderer = graphicRenderer;
	}

	@Override
	public void updateElement(Object element)
	{
		if (this.graphicRenderer != null)
			this.graphicRenderer.updateElement(element);

	}

}
