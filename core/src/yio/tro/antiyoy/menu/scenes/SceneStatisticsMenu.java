package yio.tro.antiyoy.menu.scenes;

import yio.tro.antiyoy.Settings;
import yio.tro.antiyoy.gameplay.MatchStatistics;
import yio.tro.antiyoy.menu.Animation;
import yio.tro.antiyoy.menu.ButtonYio;
import yio.tro.antiyoy.menu.MenuControllerYio;
import yio.tro.antiyoy.menu.behaviors.Reaction;
import yio.tro.antiyoy.stuff.GraphicsYio;

public class SceneStatisticsMenu extends AbstractScene{


    private ButtonYio replayButton;


    public SceneStatisticsMenu(MenuControllerYio menuControllerYio) {
        super(menuControllerYio);
    }


    public void create(MatchStatistics matchStatistics) {
        menuControllerYio.beginMenuCreation();

        menuControllerYio.getYioGdxGame().beginBackgroundChange(0, false, true);

        menuControllerYio.spawnBackButton(111, Reaction.rbChooseGameModeMenu);

        ButtonYio textPanel = buttonFactory.getButton(generateRectangle(0.05, 0.1, 0.9, 0.7), 112, null);
        textPanel.cleatText();
        textPanel.addTextLine(getString("statistics") + ":");
        textPanel.addTextLine(getString("turns_made") + " " + matchStatistics.turnsMade);
        textPanel.addTextLine(getString("units_died") + " " + matchStatistics.unitsDied);
        textPanel.addTextLine(getString("units_produced") + " " + matchStatistics.unitsProduced);
        textPanel.addTextLine(getString("money_spent") + " " + matchStatistics.moneySpent);
        textPanel.addTextLine(getString("time") + " " + matchStatistics.getTimeString());
        for (int i = 0; i < 10; i++) {
            textPanel.addTextLine("");
        }
        menuControllerYio.getButtonRenderer().renderButton(textPanel);
        textPanel.setTouchable(false);
        textPanel.setAnimation(Animation.FROM_CENTER);

        createReplayButton();

        menuControllerYio.endMenuCreation();
    }


    private void createReplayButton() {
        if (!Settings.replaysEnabled) return;

        replayButton = buttonFactory.getButton(generateRectangle(0.55, 0.9, 0.4, 0.07), 113, getString("replay"));
        replayButton.setReaction(Reaction.rbStartInstantReplay);
        replayButton.setAnimation(Animation.UP);
        replayButton.setTouchOffset(0.05f * GraphicsYio.width);
        replayButton.disableTouchAnimation();
    }


    @Override
    public void create() {

    }
}