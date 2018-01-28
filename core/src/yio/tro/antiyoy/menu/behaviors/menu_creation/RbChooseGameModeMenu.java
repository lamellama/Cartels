package yio.tro.antiyoy.menu.behaviors.menu_creation;

import yio.tro.antiyoy.menu.ButtonYio;
import yio.tro.antiyoy.menu.behaviors.Reaction;
import yio.tro.antiyoy.menu.scenes.Scenes;

/**
 * Created by ivan on 15.08.2015.
 */
public class RbChooseGameModeMenu extends Reaction {

    @Override
    public void reactAction(ButtonYio buttonYio) {
        Scenes.sceneChoodeGameModeMenu.create();
        getYioGdxGame(buttonYio).setGamePaused(true);
        getYioGdxGame(buttonYio).setAnimToPlayButtonSpecial();
    }
}
