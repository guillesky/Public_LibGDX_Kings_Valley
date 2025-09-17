package modelo;

public interface IGraphic
{

  

    void addGraphicElement(DrawableElement element);

    void removeGraphicElement(DrawableElement element);

    void reset();

    float getTimeToExitLevel();

    float getTimeToEnterLevel();

    float getTimeDying();

    float getTimeToEndGame();

}
