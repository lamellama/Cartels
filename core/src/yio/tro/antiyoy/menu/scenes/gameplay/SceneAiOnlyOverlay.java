package yio.tro.antiyoy.menu.scenes.gameplay;

import yio.tro.antiyoy.YioGdxGame;
import yio.tro.antiyoy.menu.Animation;
import yio.tro.antiyoy.menu.ButtonYio;
import yio.tro.antiyoy.menu.MenuControllerYio;
import yio.tro.antiyoy.menu.behaviors.Reaction;
import yio.tro.antiyoy.menu.scenes.AbstractScene;
import yio.tro.antiyoy.menu.speed_panel.SpeedPanel;

public class SceneAiOnlyOverlay extends AbstractScene {


    public SpeedPanel speedPanel;
    public ButtonYio inGameMenuButton;


    public SceneAiOnlyOverlay(MenuControllerYio menuControllerYio) {
        super(menuControllerYio);

        speedPanel = null;
    }


    @Override
    public void create() {
        menuControllerYio.beginMenuCreation();

        createInGameMenuButton();

        createSpeedPanel();
        speedPanel.appear();

        menuControllerYio.endMenuCreation();
    }


    private void createInGameMenuButton() {
        inGameMenuButton = buttonFactory.getButton(generateSquare(1 - 0.07 / YioGdxGame.screenRatio, 0.93, 0.07), 530, null);
        menuControllerYio.loadButtonOnce(inGameMenuButton, "menu_icon.png");
        inGameMenuButton.setReaction(Reaction.rbPauseMenu);
        inGameMenuButton.setAnimation(Animation.UP);
        inGameMenuButton.enableRectangularMask();
        inGameMenuButton.disableTouchAnimation();
    }


    private void createSpeedPanel() {
        if (speedPanel != null) return;

        speedPanel = new SpeedPanel(menuControllerYio, -1);
        menuControllerYio.addElementToScene(speedPanel);
    }
}
