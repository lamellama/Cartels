package yio.tro.antiyoy.menu.scenes.gameplay;

import yio.tro.antiyoy.SoundControllerYio;
import yio.tro.antiyoy.YioGdxGame;
import yio.tro.antiyoy.gameplay.rules.GameRules;
import yio.tro.antiyoy.menu.Animation;
import yio.tro.antiyoy.menu.ButtonYio;
import yio.tro.antiyoy.menu.MenuControllerYio;
import yio.tro.antiyoy.menu.behaviors.Reaction;
import yio.tro.antiyoy.menu.scenes.AbstractScene;
import yio.tro.antiyoy.menu.scenes.Scenes;
import yio.tro.antiyoy.stuff.GraphicsYio;

public class SceneGameOverlay extends AbstractScene {


    public SceneGameOverlay(MenuControllerYio menuControllerYio) {
        super(menuControllerYio);
    }


    @Override
    public void create() {
        if (GameRules.inEditorMode) {
            Scenes.sceneEditorInstruments.create();
            return;
        }

        if (GameRules.aiOnlyMode) {
            Scenes.sceneReplayOverlay.create();
            return;
        }

        menuControllerYio.beginMenuCreation();

        ButtonYio inGameMenuButton = buttonFactory.getButton(generateSquare(1 - 0.07 / YioGdxGame.screenRatio, 0.93, 0.07), 30, null);
        menuControllerYio.loadButtonOnce(inGameMenuButton, "menu_icon.png");
        inGameMenuButton.setReaction(Reaction.rbPauseMenu);
        inGameMenuButton.setAnimation(Animation.UP);
        inGameMenuButton.enableRectangularMask();
        inGameMenuButton.disableTouchAnimation();

        ButtonYio endTurnButton = buttonFactory.getButton(generateSquare(1 - 0.07 / YioGdxGame.screenRatio, 0, 0.07), 31, null);
        menuControllerYio.loadButtonOnce(endTurnButton, "end_turn.png");
        endTurnButton.setReaction(Reaction.rbEndTurn);
        endTurnButton.setAnimation(Animation.DOWN);
        endTurnButton.enableRectangularMask();
        endTurnButton.disableTouchAnimation();
        endTurnButton.setPressSound(SoundControllerYio.soundEndTurn);

        ButtonYio undoButton = buttonFactory.getButton(generateSquare(0, 0, 0.07), 32, null);
        menuControllerYio.loadButtonOnce(undoButton, "undo.png");
        undoButton.setReaction(Reaction.rbUndo);
        undoButton.setAnimation(Animation.DOWN);
        undoButton.enableRectangularMask();
        undoButton.setTouchOffset(0.08f * GraphicsYio.width);
        undoButton.disableTouchAnimation();

        menuControllerYio.endMenuCreation();
    }
}