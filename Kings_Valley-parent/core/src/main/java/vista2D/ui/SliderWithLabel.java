package vista2D.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SliderWithLabel extends Table
{
    private final Label labelTitle;
    protected final Slider slider;
    protected final Label labelValue;
    protected ChangeListener changeListener;

    public SliderWithLabel(String texto, float min, float max, float step, float valorInicial, Skin skin,
	    int usserObject)
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

	// Escuchar cambios y actualizar el número
	slider.addListener(this.changeListener);

	// Construir el layout en vertical
	/*
	 * this.add(labelTitle); this.row(); this.add(slider).width(300); this.row();
	 * this.add(labelValue);
	 */
	this.add(labelTitle).width(350).left().padRight(10);
	this.add(slider).width(300).padRight(10);
	this.add(labelValue).width(40).right();

    }

    public SliderWithLabel(String texto, float min, float max, float step, float valorInicial, Skin skin,
	    int usserObject, ChangeListener changeListener)
    {
	this(texto, min, max, step, valorInicial, skin, usserObject);
	this.addChangeListener(changeListener);

    }

    public float getValue()
    {
	return slider.getValue();
    }

    public void setValue(float value)
    {
	slider.setValue(value);
    }

   

    public void setText(String text)
    {
	this.labelTitle.setText(text);
    }

    public void addChangeListener(ChangeListener changeListener)
    {
	this.slider.addListener(changeListener);
    }
}
