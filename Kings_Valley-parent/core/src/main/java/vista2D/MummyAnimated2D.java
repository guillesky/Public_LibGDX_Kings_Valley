package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.gameCharacters.mummys.Mummy;

/**
 * Representa graficamente una momia
 * 
 * @author Guillermo Lazzurri
 */
public class MummyAnimated2D extends GameCharacterAnimated2D
{
    /**
     * Animacion para la aparicion de la momia
     */
    protected Animation<TextureRegion> mummyAnimationAppear;

    /**
     * Constructor de clase. Llama a super(character, animations);
     * 
     * @param character  La momia que debe ser representada
     * @param animations El array de animaciones correspondiente a los a los
     *                   estados. Incorpora un sexto elemnto correspondiente al
     *                   indice <br>
     *                   TileMapGrafica2D.APPEAR=5; (animacion correspondiente a la
     *                   aparicion de la momia)
     * 
     */
    public MummyAnimated2D(GameCharacter character, Animation<TextureRegion>[] animations)
    {
	super(character, animations);
	this.mummyAnimationAppear = animations[TileMapGrafica2D.APPEAR];

    }

    @Override
    public void updateElement(float deltaTime)
    {
	Mummy mummy = (Mummy) this.getLevelObject();

	int state = mummy.getState();
	if (state == Mummy.ST_APPEARING)
	    this.animation = this.mummyAnimationAppear;
	super.updateElement(deltaTime);
    }

    /**
     * Solo se dibuja si la momia no esta en el limbo.<br>
     * if (mummy.getState() != Mummy.ST_LIMBUS) super.render(batch);
     * 
     */
    @Override
    public void render(SpriteBatch batch)
    {
	Mummy mummy = (Mummy) this.getLevelObject();
	if (mummy.getState() != Mummy.ST_LIMBUS)
	    super.render(batch);

    }

}
