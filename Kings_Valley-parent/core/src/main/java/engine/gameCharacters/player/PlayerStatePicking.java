package engine.gameCharacters.player;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import engine.DrawableElement;
import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.level.GiratoryMechanism;
import engine.level.PairInt;
import engine.level.Pyramid;
import util.GameRules;
import util.Constants;

/**
 * Representa el esta "picando"
 * 
 * @author Guillermo Lazzurri
 */
public class PlayerStatePicking extends PlayerState
{
	private ArrayList<PairInt> coordToPick = new ArrayList<PairInt>();
	private float timePicking = 0;

	/**
	 * Constructor de clase, llama a super(player, Player.ST_PICKING);
	 * 
	 * @param player      Corresponde al sujeto del patron state
	 * @param coordToPick Contiene las coordenadas que deben picarse
	 */
	public PlayerStatePicking(Player player, ArrayList<PairInt> coordToPick)
	{
		super(player, Player.ST_PICKING);
		this.coordToPick = coordToPick;

	}

	/**
	 * Si recien comienza a picar agrega el objeto grafico
	 * this.player.getPyramid().addGraphicElement(new
	 * DrawableElement(Constantes.DRAWABLE_PICKING_CELL, pairInt)); (para repreenta
	 * la celda que se esta picando) y dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.PLAYER_PICKING,
	 * this.player);<br>
	 * Si termino de picar elimina el objeto grafico this.player.getPyramid()
	 * .removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_PICKING_CELL,
	 * pairInt));<br>
	 * Si ya no hay que picar mas se realiza el cambio de estado mediante
	 * this.player.setPlayerState(new PlayerStateMoving(this.player, state));
	 * 
	 */
	@Override
	public void update(Vector2 v, boolean b, float deltaTime)
	{
		Pyramid pyramid = this.player.getPyramid();
		PairInt pairInt = coordToPick.get(0);
		if (this.getTimePicking() == 0)// Recien comienza a picar
		{
			this.player.getPyramid().addGraphicElement(new DrawableElement(Constants.DRAWABLE_PICKING_CELL, pairInt));
			Game.getInstance().eventFired(KVEventListener.PLAYER_PICKING, this.player);
		}
		this.incTimePicking(deltaTime);

		if (this.getTimePicking() >= GameRules.getInstance().getTimeToEndPicking()) // termino de picar una celda
		{

			TiledMapTileLayer layer = (TiledMapTileLayer) pyramid.getMap().getLayers().get("front");
			layer.setCell(pairInt.getX(), pairInt.getY(), null);
			coordToPick.remove(0);
			this.player.getPyramid()
					.removeGraphicElement(new DrawableElement(Constants.DRAWABLE_PICKING_CELL, pairInt));
			if (coordToPick.isEmpty())
			{
				int state;
				if (v.x == 0)
					state = GameCharacter.ST_IDDLE;
				else
					state = GameCharacter.ST_WALKING;
				this.player.setPlayerState(new PlayerStateMoving(this.player, state));

			}
			this.resetTimePicking();
		}

	}

	/**
	 * Se realiza el cambio de estado mediante this.player.setPlayerState(new
	 * PlayerStateDying(this.player));
	 * 
	 */
	@Override
	protected void die()
	{
		this.player.setPlayerState(new PlayerStateDying(this.player));

	}

	/**
	 * Retorna el tiempo transcurrido desde que comenzo a picar
	 * @return el tiempo transcurrido desde que comenzo a picar
	 */
	protected float getTimePicking()
	{
		return timePicking;
	}

	/**
	 * incrementa el tiempo que el player esta picando
	 * 
	 * @param delta tiempo que transcurrio desde la ultima vez que estaba picando
	 */
	protected void incTimePicking(float delta)
	{
		this.timePicking += delta;
	}

	/**
	 * Pone en cero el tiempo de picado
	 */
	protected void resetTimePicking()
	{
		this.timePicking = 0;
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void doPicker()
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void throwDagger()
	{

	}
}
