package yio.tro.antiyoy.menu.behaviors.editor;

import yio.tro.antiyoy.menu.ButtonYio;
import yio.tro.antiyoy.menu.behaviors.Reaction;

public class RbSwitchFilterOnlyLand extends Reaction {

    @Override
    public void reactAction(ButtonYio buttonYio) {
        getGameController(buttonYio).getLevelEditor().switchFilterOnlyLand();
    }
}
