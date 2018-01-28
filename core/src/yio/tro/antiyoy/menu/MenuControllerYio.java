package yio.tro.antiyoy.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.StringTokenizer;

import yio.tro.antiyoy.YioGdxGame;
import yio.tro.antiyoy.factor_yio.FactorYio;
import yio.tro.antiyoy.gameplay.game_view.GameView;
import yio.tro.antiyoy.menu.behaviors.Reaction;
import yio.tro.antiyoy.menu.scenes.Scenes;
import yio.tro.antiyoy.stuff.LanguagesManager;
import yio.tro.antiyoy.stuff.RectangleYio;


public class MenuControllerYio {

    public static int SPAWN_ANIM = 2, DESTROY_ANIM = 2;
    public static double SPAWN_SPEED = 1.5, DESTROY_SPEED = 1.5;

    public final YioGdxGame yioGdxGame;
    private final ButtonFactory buttonFactory;
    public ButtonRenderer buttonRenderer;
    TextureRegion unlockedLevelIcon, lockedLevelIcon, openedLevelIcon;
    public FactorYio infoPanelFactor;
    public final ArrayList<ButtonYio> buttons;
    public ArrayList<CheckButtonYio> checkButtons;
    public SpecialActionController specialActionController;
    public ArrayList<InterfaceElement> interfaceElements;


    public MenuControllerYio(YioGdxGame yioGdxGame) {
        this.yioGdxGame = yioGdxGame;
        buttonFactory = new ButtonFactory(this);
        buttons = new ArrayList<ButtonYio>();
        buttonRenderer = new ButtonRenderer();
        infoPanelFactor = new FactorYio();
        specialActionController = new SpecialActionController(this);
        unlockedLevelIcon = GameView.loadTextureRegion("unlocked_level_icon.png", true);
        lockedLevelIcon = GameView.loadTextureRegion("locked_level_icon.png", true);
        openedLevelIcon = GameView.loadTextureRegion("opened_level_icon.png", true);
        interfaceElements = new ArrayList<>();
        initCheckButtons();
        applyAnimStyle();

        Scenes.createScenes(this);

        Scenes.sceneMainMenu.create();
        checkToCreateSingleMessage();
    }


    private void checkToCreateSingleMessage() {


//        if (SingleMessages.achikapsRelease) {
//            SingleMessages.achikapsRelease = false;
//            SingleMessages.save();
//            createSingleMessageMenu("achikaps_release");
//            return;
//        }
    }


    private void initCheckButtons() {
        checkButtons = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            CheckButtonYio.getCheckButton(this, generateRectangle(0, 0, 0, 0), i + 1);
            getCheckButtonById(i + 1).destroy();
        }
    }


    public void move() {
        infoPanelFactor.move();
        specialActionController.move();

        for (CheckButtonYio checkButton : checkButtons) {
            checkButton.move();
        }

        for (ButtonYio buttonYio : buttons) {
            buttonYio.move();
        }

        for (InterfaceElement interfaceElement : interfaceElements) {
            if (interfaceElement.isVisible()) {
                interfaceElement.move();
            }
        }

        checkToPerformAction();
    }


    private void checkToPerformAction() {
        for (int i = interfaceElements.size() - 1; i >= 0; i--) {
            InterfaceElement interfaceElement = interfaceElements.get(i);
            if (!interfaceElement.isVisible()) continue;

            if (interfaceElement.checkToPerformAction()) return;
        }

        for (int i = buttons.size() - 1; i >= 0; i--) {
            if (buttons.get(i).checkToPerformAction()) return;
        }
    }


    public void addMenuBlockToArray(ButtonYio buttonYio) {
        // considered that menu block is not in array at this moment
        ListIterator iterator = buttons.listIterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.add(buttonYio);
    }


    public void removeInterfaceElementFromArray(ButtonYio buttonYio) {
        ListIterator iterator = buttons.listIterator();
        ButtonYio currentBlock;
        while (iterator.hasNext()) {
            currentBlock = (ButtonYio) iterator.next();
            if (currentBlock == buttonYio) {
                iterator.remove();
                return;
            }
        }
    }


    public ButtonYio getButtonById(int id) { // can return null
        for (ButtonYio buttonYio : buttons) {
            if (buttonYio.id == id) return buttonYio;
        }
        return null;
    }


    public void onPause() {
        for (ButtonYio button : buttons) {
            button.onPause();
        }

        for (InterfaceElement interfaceElement : interfaceElements) {
            interfaceElement.onPause();
        }
    }


    public void onResume() {
        for (ButtonYio button : buttons) {
            button.onResume();
        }

        for (InterfaceElement interfaceElement : interfaceElements) {
            interfaceElement.onResume();
        }
    }


    public void loadButtonOnce(ButtonYio buttonYio, String fileName) {
        if (buttonYio.notRendered()) {
            buttonYio.loadTexture(fileName);
        }
    }


    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = interfaceElements.size() - 1; i >= 0; i--) {
            InterfaceElement interfaceElement = interfaceElements.get(i);
            if (interfaceElement.isTouchable() && interfaceElement.isVisible()) {
                if (interfaceElement.touchDown(screenX, screenY, pointer, button)) return true;
            }
        }

        for (CheckButtonYio checkButton : checkButtons) {
            if (checkButton.isTouchable()) {
                if (checkButton.checkTouch(screenX, screenY, pointer, button)) return true;
            }
        }

        for (ButtonYio buttonYio : buttons) {
            if (buttonYio.isTouchable()) {
                if (buttonYio.checkTouch(screenX, screenY, pointer, button)) return true;
            }
        }

        return false;
    }


    public void touchDragged(int screenX, int screenY, int pointer) {
        // order doesn't matter here because no 'break' here
        for (InterfaceElement interfaceElement : interfaceElements) {
            if (interfaceElement.isTouchable() && interfaceElement.isVisible()) {
                interfaceElement.touchDrag(screenX, screenY, pointer);
            }
        }
    }


    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (int i = interfaceElements.size() - 1; i >= 0; i--) {
            InterfaceElement interfaceElement = interfaceElements.get(i);
            if (interfaceElement.isTouchable() && interfaceElement.isVisible()) {
                if (interfaceElement.touchUp(screenX, screenY, pointer, button)) return true;
            }
        }

        return false;
    }


    public boolean onMouseWheelScrolled(int amount) {
        for (InterfaceElement interfaceElement : interfaceElements) {
            if (interfaceElement.isTouchable() && interfaceElement.isVisible()) {
                if (interfaceElement.onMouseWheelScrolled(amount)) {
                    return true;
                }
            }
        }

        return false;
    }


    public void beginMenuCreation() {
        infoPanelFactor.setValues(1, 0);
        infoPanelFactor.destroy(1, 3);

        for (InterfaceElement interfaceElement : interfaceElements) {
            interfaceElement.destroy();
        }

        for (CheckButtonYio checkButton : checkButtons) {
            checkButton.destroy();
        }

        for (ButtonYio buttonYio : buttons) {
            buttonYio.destroy();

            if (buttonYio.id == 3 && buttonYio.isVisible()) {
                buttonYio.appearFactor.setValues(1, 0);
                buttonYio.appearFactor.destroy(1, 2);
            }
            if (buttonYio.id >= 22 && buttonYio.id <= 29 && buttonYio.isVisible()) {
                buttonYio.appearFactor.destroy(1, 2.1);
            }
            if (buttonYio.id == 30 && buttonYio.appearFactor.get() > 0) {
                buttonYio.appearFactor.setValues(1, 0);
                buttonYio.appearFactor.destroy(1, 1);
            }
        }
        if (yioGdxGame.gameView != null) yioGdxGame.gameView.beginDestroyProcess();
    }


    public void endMenuCreation() {

    }


    void forceSpawningButtonsToTheEnd() {
        for (ButtonYio buttonYio : buttons) {
            if (buttonYio.appearFactor.getGravity() > 0) {
                buttonYio.appearFactor.setValues(1, 0);
            }
        }
    }


    public ArrayList<String> getArrayListFromString(String src) {
        ArrayList<String> list = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(src, "#");
        while (tokenizer.hasMoreTokens()) {
            list.add(tokenizer.nextToken());
        }
        return list;
    }


    public RectangleYio generateRectangle(double x, double y, double width, double height) {
        return new RectangleYio(x * Gdx.graphics.getWidth(), y * Gdx.graphics.getHeight(), width * Gdx.graphics.getWidth(), height * Gdx.graphics.getHeight());
    }


    public RectangleYio generateSquare(double x, double y, double size) {
        return generateRectangle(x, y, size / YioGdxGame.screenRatio, size);
    }


    public String getString(String key) {
        return LanguagesManager.getInstance().getString(key);
    }


    public void createSpecialThanksMenu() {
        Scenes.sceneInfoMenu.create("special_thanks", Reaction.rbInfo, 312837182);
    }


    public void renderTextAndSomeEmptyLines(ButtonYio buttonYio, String text, int emptyLines) {
        if (buttonYio.notRendered()) {
            buttonYio.addTextLine(text);
            for (int i = 0; i < emptyLines; i++) {
                buttonYio.addTextLine(" ");
            }
            buttonRenderer.renderButton(buttonYio);
        }
    }


    public void hideAllEditorPanels() {
        Scenes.sceneEditorHexPanel.hide();
        Scenes.sceneEditorObjectPanel.hide();
        Scenes.sceneEditorParams.hide();
        Scenes.sceneEditorAutomationPanel.hide();
        Scenes.sceneEditorMoneyPanel.hide();

        yioGdxGame.gameController.getLevelEditor().onAllPanelsHide();
    }


    public void destroyButton(int id) {
        ButtonYio buttonYio = getButtonById(id);
        if (buttonYio == null) return;
        buttonYio.destroy();
    }


    public void applyAnimStyle() {
        SPAWN_ANIM = 2;
        SPAWN_SPEED = 1.5;
        DESTROY_ANIM = 2;
        DESTROY_SPEED = 1.5;
    }


    public String getColorNameByIndex(int index, String keyModifier) {
        index = yioGdxGame.gameController.getColorIndexWithOffset(index);
        switch (index) {
            default:
            case 6:
            case 0:
                return getString("green" + keyModifier);
            case 1:
            case 5:
                return getString("red" + keyModifier);
            case 2:
                return getString("magenta" + keyModifier);
            case 3:
                return getString("cyan" + keyModifier);
            case 4:
                return getString("yellow" + keyModifier);
            case 7:
                return getString("gray" + keyModifier);
        }
    }


    public void forceDyingButtonsToEnd() {
        for (ButtonYio button : buttons) {
            if (button.appearFactor.getGravity() < 0) {
                button.appearFactor.setValues(0, 0);
            }
        }
    }


    public ButtonYio spawnBackButton(int id, Reaction reaction) {
        ButtonYio backButton = buttonFactory.getButton(generateRectangle(0.05, 0.9, 0.4, 0.07), id, null);
        loadButtonOnce(backButton, "back_icon.png");
        backButton.setShadow(true);
        backButton.setAnimation(Animation.UP);
        backButton.setReaction(reaction);
        backButton.setTouchOffset(0.05f * Gdx.graphics.getHeight());
        yioGdxGame.registerBackButtonId(id);
        return backButton;
    }


    public void addCheckButtonToArray(CheckButtonYio checkButtonYio) {
        checkButtons.listIterator().add(checkButtonYio);
    }


    public CheckButtonYio getCheckButtonById(int id) {
        for (CheckButtonYio checkButton : checkButtons) {
            if (checkButton.id == id) {
                return checkButton;
            }
        }
        return null;
    }


    public ButtonFactory getButtonFactory() {
        return buttonFactory;
    }


    public void removeButtonById(int id) {
        ListIterator<ButtonYio> iterator = buttons.listIterator();
        while (iterator.hasNext()) {
            ButtonYio button = iterator.next();
            if (button.id == id) {
                iterator.remove();
                return;
            }
        }
    }


    public void addElementToScene(InterfaceElement element) {
        // considered that ui element is not in array at this moment
        ListIterator iterator = interfaceElements.listIterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.add(element);
    }


    public void removeElementFromScene(InterfaceElement interfaceElement) {
        ListIterator iterator = interfaceElements.listIterator();
        InterfaceElement currentElement;
        while (iterator.hasNext()) {
            currentElement = (InterfaceElement) iterator.next();
            if (currentElement == interfaceElement) {
                iterator.remove();
                return;
            }
        }
    }


    public YioGdxGame getYioGdxGame() {
        return yioGdxGame;
    }


    public ButtonRenderer getButtonRenderer() {
        return buttonRenderer;
    }


    public void clear() {
        buttons.clear();
    }


    public void close() {

    }
}
