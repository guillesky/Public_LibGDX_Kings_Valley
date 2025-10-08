package vista2D.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Represente el slider de dificultad
 * 
 * @author Guillermo Lazzurri
 */
public class SliderDificult extends SliderWithLabel
{
	private String easy;
	private String normal;
	private String hard;

	/**
	 * Constructor de clase. llama a <br>
	 * super(text, min, max, step, valorInicial, skin, usserObject, changeListener);
	 * 
	 * 
	 * @param text          Texto del titulo del conjunto
	 * @param min            Valor minimo del slider
	 * @param max            Valor maximo del slider
	 * @param step           Valor de cada paso del slider
	 * @param valorInicial   Valor inicial del slider
	 * @param skin           Skin utilizada por el conjunto
	 * @param usserObject    userObject que se pasara por parametro al slider
	 * @param changeListener ChangeListener que debe agregarse inicialmente
	 * 
	 * @param easy           texto correspondiente a la etiqueta "facil"
	 * @param normal         texto correspondiente a la etiqueta "normal"
	 * @param hard           texto correspondiente a la etiqueta "dificil"
	 */
	public SliderDificult(String text, float min, float max, float step, float valorInicial, Skin skin, int usserObject,
			ChangeListener changeListener, String easy, String normal, String hard)
	{
		super(text, min, max, step, valorInicial, skin, usserObject, changeListener);
		this.easy = easy;
		this.normal = normal;
		this.hard = hard;
		this.labelValue.setText(this.setValueLabel());
		this.slider.removeListener(this.changeListener);
		this.changeListener = new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				labelValue.setText(setValueLabel());
			}
		};
		this.slider.addListener(this.changeListener);
		this.labelValue.setText(this.setValueLabel());
	}

	/**
	 * @return El string con la etiqueta de la dificultad mas el valor
	 *         correspondiente (por ejemplo "dificil 3" o "facil 2")
	 */
	private String setValueLabel()
	{
		String r = null;
		int v = (int) this.slider.getValue();
		if (v == 0)
			r = this.normal;
		else if (v > 0)
			r = this.hard + " " + v;
		else
			r = this.easy + " " + -v;
		return r;
	}

	/**
	 * Setea los textos del slider (necesario para cambios de idioma)
	 * 
	 * @param text   texto correspondiente a la etiqueta "nivel de dificultad"
	 * @param easy   texto correspondiente a la etiqueta "facil"
	 * @param normal texto correspondiente a la etiqueta "normal"
	 * @param hard   texto correspondiente a la etiqueta "dificil"
	 */
	public void setText(String text, String easy, String normal, String hard)
	{

		this.setText(text);
		this.easy = easy;
		this.normal = normal;
		this.hard = hard;

	}

}
