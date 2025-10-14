package engine.gameCharacters.mummys;

import engine.game.Game;
import util.GameRules;

/**
 * Clase que representa del estado de la momia "En el Limbo"
 * 
 * @author Guillermo Lazzurri
 */
public class MummyStateLimbus extends MummyState
{
	private boolean mustTeleport = false;

	/**
	 * Contructor de clase llamado cada vez que la momia muere durante los cambios
	 * de estado, llama a this(mummy, Config.getInstance().getMummyTimeInLimbus());
	 * 
	 * @param mummy        Correspondiente al sujeto del patron state
	 * @param mustTeleport true si la momia se debera teletransoportar, flase en
	 *                     caso contrario
	 */
	public MummyStateLimbus(Mummy mummy, boolean mustTeleport)
	{
		this(mummy, GameRules.getInstance().getMummyTimeInLimbus());
		this.mustTeleport = mustTeleport;
		this.mummy.resetStress();
	}

	/**
	 * Constructor de clase llamado la primera vez que se crea la momia. Estado por
	 * defecto. Llama a super(mummy, Mummy.ST_LIMBUS);
	 * 
	 * @param mummy        Correspondiente al sujeto del patron state
	 * @param timeToChange Indica cuanto tiempo debe estar la momia en el limbo
	 *                     (esto es necesario porque las momias estaran mas tiempo
	 *                     en el limbo si mueren durante el juego que en su primer
	 *                     aparacion)
	 */
	public MummyStateLimbus(Mummy mummy, float timeToChange)
	{
		super(mummy, Mummy.ST_LIMBUS);
		this.timeToChange = timeToChange;

	}

	/**
	 * Al pasar el tiempo hasta his.timeToChange, se cambiara al estado new
	 * MummyStateAppearing(this.mummy);
	 */
	@Override
	public void update(float deltaTime)

	{
		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			if (this.mustTeleport)
				this.teleport();
			this.mummy.mummyState = new MummyStateAppearing(this.mummy);
			this.mummy = null;
		}
	}

	
	/**
	 * Calcula la distancia al cuadrado del player a las coordenadas x e y pasadas
	 * por parametro, usado para calcular posibles destinos de teletransporte
	 * 
	 * @param paramX coordenada x de destino
	 * @param paramY coordenada y de destino
	 * @return distancia al cuadrado
	 */
	private float distanceQuadToPlayer(float paramX, float paramY)
	{
		float deltaX = paramX - this.mummy.player.getX();
		float deltaY = paramY - this.mummy.player.getY();

		return deltaX * deltaX + deltaY * deltaY;

	}
	
	/**
	 * Llamado para calcular un lugar de teletransportacion
	 */
	private void teleport()
	{
		float[] coords;
		do
		{
			coords = this.getRandomCellInFloor();

		} while (this.distanceQuadToPlayer(coords[0], coords[1]) < GameRules.getInstance()
				.getMinMummySpawnDistanceToPlayer());
		this.mummy.x = coords[0];
		this.mummy.y = coords[1];

	}

	/**
	 * @return retorna al azar una celda candidata para teletransporte
	 */
	private float[] getRandomCellInFloor()
	{
		int i;
		int j;
		do
		{
			i = Game.random.nextInt(this.mummy.getPyramid().getMapHeightInTiles() - 2) + 1;
			j = Game.random.nextInt(this.mummy.getPyramid().getMapWidthInTiles() - 2) + 1;

		} while (this.mummy.getPyramid().getCellInTiledCoord(j, i) != null || this.mummy.getPyramid().getCellInTiledCoord(j, i + 1) != null);

		while (this.mummy.getPyramid().getCellInTiledCoord(j, i - 1) == null)
			i--;
		float[] r =
		{ j * GameRules.getInstance().getLevelTileWidthUnits(), i * GameRules.getInstance().getLevelTileHeightUnits() };
		return r;
	}
	
	
	
	
	
	/**
	 * Retorna false
	 */
	@Override
	protected boolean isDanger()
	{
		return false;
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void die(boolean mustTeleport)
	{
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void doInBorderCliff()
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void doInCrashToWallOrGiratory(int crashStatus, int type)
	{

	}
}
