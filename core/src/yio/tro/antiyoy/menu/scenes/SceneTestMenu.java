package yio.tro.antiyoy.menu.scenes;

import yio.tro.antiyoy.menu.MenuControllerYio;
import yio.tro.antiyoy.menu.behaviors.Reaction;

public class SceneTestMenu extends AbstractScene{


    public SceneTestMenu(MenuControllerYio menuControllerYio) {
        super(menuControllerYio);
    }


    @Override
    public void create() {
        menuControllerYio.beginMenuCreation();

        menuControllerYio.getYioGdxGame().beginBackgroundChange(2, false, true);


        menuControllerYio.spawnBackButton(38721132, Reaction.rbChooseGameModeMenu);

        menuControllerYio.endMenuCreation();
    }
}