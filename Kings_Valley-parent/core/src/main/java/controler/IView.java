package controler;

import util.GameConfig;

public interface IView
{
void updateCredits(String credits);
void updateGameConfig(GameConfig gameConfig);
int getDificultLevel();
String getLanguage();
float getMasterVolume();
float getMusicVolume();
float getSoundsVolume();
void setControler(Controler Controler);



}
