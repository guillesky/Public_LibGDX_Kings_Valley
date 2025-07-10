package vista2D.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SliderWithLabel extends Table
{
    protected final Label labelTitle;
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

	
	slider.addListener(this.changeListener);

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

    public int getValue()
    {
	return (int) slider.getValue();
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
