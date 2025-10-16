package vista2D.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Representa un objeto visualcombinado de slider con un label descriptivo
 * 
 * @author Guillermo Lazzurri
 */
public class SliderWithLabel extends Table
{
	/**
	 * Etiqueta con el titulo del conjunto
	 */
	protected final Label labelTitle;
	/**
	 * Slider correspondiente
	 */
	protected final Slider slider;
	/**
	 * Etiqueta con el valor del slider
	 */
	protected final Label labelValue;
	/**
	 * ChangeListener asociado
	 */
	protected ChangeListener changeListener;

	/**
	 * Constructor de clase
	 * 
	 * @param texto          Texto del titulo del conjunto
	 * @param min            Valor minimo del slider
	 * @param max            Valor maximo del slider
	 * @param step           Valor de cada paso del slider
	 * @param valorInicial   Valor inicial del slider
	 * @param skin           Skin utilizada por el conjunto
	 * @param usserObject    userObject que se pasara por parametro al slider
	 * @param changeListener ChangeListener que debe agregarse inicialmente
	 */
	public SliderWithLabel(String texto, float min, float max, float step, float valorInicial, Skin skin,
			int usserObject, ChangeListener changeListener)
	{
		labelTitle = new Label(texto, skin);
		slider = new Slider(min, max, step, false, skin);
		this.slider.setUserObject(usserObject);
		labelValue = new Label(String.valueOf((int) valorInicial), skin);
		this.changeListener = new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				labelValue.setText(String.valueOf((int) slider.getValue()));
			}
		};
		slider.setValue(valorInicial);

		slider.addListener(this.changeListener);

		this.add(labelTitle).width(350).left().padRight(10);
		this.add(slider).width(300).padRight(10);
		this.add(labelValue).width(40).right();
		this.addChangeListener(changeListener);

	}

	/**
	 * Retorna el valor del slider
	 * @return el valor del slider
	 */
	public int getValue()
	{
		return (int) slider.getValue();
	}

	/**
	 * Setea el valor del slider
	 * 
	 * @param value Valor del slider
	 */
	public void setValue(float value)
	{
		slider.setValue(value);
	}

	/**
	 * Setea el texto de la etiqueta titulo
	 * 
	 * @param text Texto correspondiente a la etiqueta titulo
	 */
	public void setText(String text)
	{
		this.labelTitle.setText(text);
	}

	/**
	 * Agrega un ChangeListener al slider
	 * 
	 * @param changeListener ChangeListener a agregar al slider
	 */
	public void addChangeListener(ChangeListener changeListener)
	{
		this.slider.addListener(changeListener);
	}

	/**
	 * Propaga el objeto touchable a ca componente
	 */
	@Override
	public void setTouchable(Touchable touchable)
	{
		super.setTouchable(touchable);
		if (this.slider != null)
			this.slider.setTouchable(touchable);
		if (this.labelValue != null)
			this.labelValue.setTouchable(touchable);
		if (this.labelTitle != null)
			this.labelTitle.setTouchable(touchable);
	}

}
