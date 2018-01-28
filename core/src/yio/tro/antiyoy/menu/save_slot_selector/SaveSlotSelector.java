package yio.tro.antiyoy.menu.save_slot_selector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;

import yio.tro.antiyoy.SoundControllerYio;
import yio.tro.antiyoy.factor_yio.FactorYio;
import yio.tro.antiyoy.gameplay.ClickDetector;
import yio.tro.antiyoy.menu.InterfaceElement;
import yio.tro.antiyoy.menu.MenuControllerYio;
import yio.tro.antiyoy.menu.render.MenuRender;
import yio.tro.antiyoy.stuff.Fonts;
import yio.tro.antiyoy.stuff.GraphicsYio;
import yio.tro.antiyoy.stuff.LanguagesManager;
import yio.tro.antiyoy.stuff.PointYio;
import yio.tro.antiyoy.stuff.RectangleYio;
import yio.tro.antiyoy.stuff.scroll_engine.ScrollEngineYio;

public class SaveSlotSelector extends InterfaceElement {

    public static final int MIN_ITEMS_NUMBER = 9;
    public static final int EMPTY_ITEM_CUT = 9;

    MenuControllerYio menuControllerYio;
    public RectangleYio position, viewPosition;
    public FactorYio appearFactor, textAlphaFactor;
    PointYio currentTouch, lastTouch;
    public ArrayList<SsItem> items;
    float hook;
    private float itemHeight;
    ScrollEngineYio scrollEngineYio;
    ClickDetector clickDetector;
    public BitmapFont titleFont, descFont;
    public String label;
    public PointYio labelPosition;
    float labelWidth;
    SsItem clickedItem;
    boolean touched, alphaTriggered;
    public RectangleYio topEdge, bottomEdge;
    boolean operationType;
    private String slotPrefsString;
    private float topLabelOffset;


    public SaveSlotSelector(MenuControllerYio menuControllerYio, int id) {
        super(id);
        this.menuControllerYio = menuControllerYio;

        position = new RectangleYio();
        viewPosition = new RectangleYio();
        appearFactor = new FactorYio();
        currentTouch = new PointYio();
        lastTouch = new PointYio();
        clickDetector = new ClickDetector();
        touched = false;
        titleFont = Fonts.gameFont;
        descFont = Fonts.smallerMenuFont;
        label = LanguagesManager.getInstance().getString("slots");
        labelPosition = new PointYio();
        labelWidth = GraphicsYio.getTextWidth(titleFont, label);
        items = new ArrayList<>();
        clickedItem = null;
        textAlphaFactor = new FactorYio();
        alphaTriggered = false;
        topEdge = new RectangleYio();
        bottomEdge = new RectangleYio();
        operationType = false;
        slotPrefsString = SaveSystem.SAVE_SLOT_PREFS;

        initMetrics();
        initScrollEngine();
    }


    private void initScrollEngine() {
        scrollEngineYio = new ScrollEngineYio();

        scrollEngineYio.setSlider(0, 0); // will be updated later
        updateScrollEngineLimits();
        scrollEngineYio.setFriction(0.02);
        scrollEngineYio.setSoftLimitOffset(0.05f * GraphicsYio.width);
    }


    private void updateScrollEngineLimits() {
        scrollEngineYio.setLimits(0, getScrollLimit());
    }


    private double getScrollLimit() {
        return items.size() * itemHeight - itemHeight / 2;
    }


    private void initMetrics() {
        itemHeight = 0.115f * GraphicsYio.height;
        topLabelOffset = 0.18f * GraphicsYio.height;
    }


    private void updateItems() {
        items.clear();

        SaveSystem saveSystem = menuControllerYio.yioGdxGame.saveSystem;
        ArrayList<String> keys = saveSystem.getKeys(slotPrefsString);

        int index = 0;
        if (!operationType) {
            addEmptyItem(saveSystem.getKeyForNewSlot(slotPrefsString), index);
            index++;
        }

        for (String key : keys) {
            SaveSlotInfo slotInfo = saveSystem.getSlotInfo(key, slotPrefsString);

            if (slotInfo.name.equals("")) {
                addEmptyItem(key, index);
                index++;
                continue;
            }

            addItem(index, key, slotInfo);
            index++;
        }
    }


    private void addItem(int index, String key, SaveSlotInfo slotInfo) {
        SsItem ssItem = new SsItem(this);
        ssItem.setKey(key);
        ssItem.setTitle(slotInfo.name);
        ssItem.setDescription(slotInfo.description);
        ssItem.setBckViewType(index % 3);

        items.add(ssItem);
    }


    private void updateSingleItem(String key) {
        SaveSystem saveSystem = menuControllerYio.yioGdxGame.saveSystem;
        SaveSlotInfo slotInfo = saveSystem.getSlotInfo(key, slotPrefsString);

        for (SsItem item : items) {
            if (item.key.equals(key)) {
                item.setTitle(slotInfo.name);
                item.setDescription(slotInfo.description);
                break;
            }
        }
    }


    private void addEmptyItem(String key, int index) {
        if (items.size() > EMPTY_ITEM_CUT) return;

        SsItem ssItem = new SsItem(this);
        ssItem.setTitle(LanguagesManager.getInstance().getString("empty"));
        ssItem.setDescription("");
        ssItem.setKey(key);
        ssItem.setBckViewType(index % 3);

        items.add(ssItem);
    }


    void updateMetrics() {
        float currentY = (float) position.height - topLabelOffset;

        for (SsItem item : items) {
            item.position.width = position.width;
            item.position.height = itemHeight;
            item.delta.x = 0;
            item.delta.y = currentY;
            currentY -= itemHeight;
        }
    }


    @Override
    public void move() {
        moveFactors();

        updateViewPosition();
        moveItems();
        scrollEngineYio.move();
        updateHook();
        updateLabelPosition();
        updateEdgeRectangles();
    }


    private void updateEdgeRectangles() {
        SsItem firstItem = items.get(0);
        topEdge.setBy(firstItem.position);
        topEdge.y += firstItem.position.height;

        SsItem lastItem = items.get(items.size() - 1);
        bottomEdge.setBy(lastItem.position);
        bottomEdge.y -= lastItem.position.height;
    }


    private void moveFactors() {
        textAlphaFactor.move();

        if (!appearFactor.hasToMove()) return;

        appearFactor.move();

        if (!alphaTriggered && appearFactor.get() > 0.95) {
            textAlphaFactor.appear(3, 0.7);
            alphaTriggered = true;
        }
    }


    private void updateLabelPosition() {
        labelPosition.x = (float) (viewPosition.x + viewPosition.width / 2 - labelWidth / 2);
        labelPosition.y = (float) (viewPosition.y + viewPosition.height - 0.02f * GraphicsYio.width) + hook;
    }


    private void updateHook() {
        hook = +(float) scrollEngineYio.getSlider().a;

        hook -= (1 - appearFactor.get()) * 0.2f * GraphicsYio.width;
    }


    private void moveItems() {
        for (SsItem item : items) {
            item.move();

            if (!touched) {
                item.moveSelection();
            }
        }
    }


    private void updateViewPosition() {
        viewPosition.setBy(position);

        if (appearFactor.get() < 1) {
            viewPosition.x += (float) ((1 - appearFactor.get()) * 0.5 * position.width);
            viewPosition.y += (float) ((1 - appearFactor.get()) * 0.5 * position.height);
            viewPosition.width -= 2 * (float) ((1 - appearFactor.get()) * 0.5 * position.width);
            viewPosition.height -= 2 * (float) ((1 - appearFactor.get()) * 0.5 * position.height);
        }
    }


    @Override
    public FactorYio getFactor() {
        return appearFactor;
    }


    @Override
    public void destroy() {
        appearFactor.destroy(DES_TYPE, DES_SPEED);
        textAlphaFactor.destroy(3, 4);
    }


    @Override
    public void appear() {
        appearFactor.setValues(0.01, 0);
        appearFactor.appear(SPAWN_TYPE, SPAWN_SPEED);

        onAppear();
    }


    private void onAppear() {
        alphaTriggered = false;
        scrollEngineYio.resetToBottom();
    }


    private void updateAll() {
        updateItems();
        updateMetrics();
        updateScrollEngineLimits();
    }


    @Override
    public boolean isVisible() {
        return appearFactor.get() > 0;
    }


    @Override
    public boolean checkToPerformAction() {
        if (clickedItem != null) {
            onSlotSelected();

            clickedItem = null;
            return true;
        }

        return false;
    }


    private void onSlotSelected() {
        String key = clickedItem.key;
        SaveSystem saveSystem = menuControllerYio.yioGdxGame.saveSystem;

        if (operationType) {
            loadSlot(key, saveSystem);
        } else {
            saveSlot(key, saveSystem);
        }
    }


    private void saveSlot(String key, SaveSystem saveSystem) {
        if (key.equals(SaveSystem.AUTOSAVE_KEY)) return; // don't overwrite autosave in selector

        if (!saveSystem.containsKey(key, slotPrefsString)) {
            saveSystem.addKey(key, slotPrefsString);
        }

        saveSystem.saveGame(key);

        SaveSlotInfo saveSlotInfo = new SaveSlotInfo();

        Preferences slotPrefs = Gdx.app.getPreferences(key);
        saveSlotInfo.name = SaveSystem.getNameString(slotPrefs);
        saveSlotInfo.description = SaveSystem.getDescriptionString(slotPrefs);
        saveSlotInfo.key = key;

        saveSystem.editSlot(key, saveSlotInfo, slotPrefsString);
        updateSingleItem(saveSlotInfo.key);
    }


    private void loadSlot(String key, SaveSystem saveSystem) {
        SaveSlotInfo slotInfo = saveSystem.getSlotInfo(key, slotPrefsString);
        if (slotInfo.name.length() < 3) {
            System.out.println("clicked on empty slot");
            return;
        }

        saveSystem.loadGame(slotInfo.key);
    }


    @Override
    public boolean isTouchable() {
        return true;
    }


    private void updateCurrentTouch(int screenX, int screenY) {
        lastTouch.setBy(currentTouch);
        currentTouch.set(screenX, screenY);
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!isVisible()) return false;

        updateCurrentTouch(screenX, screenY);
        touched = (screenY < position.y + position.height);

        if (touched) {
            clickDetector.touchDown(currentTouch);
            scrollEngineYio.updateCanSoftCorrect(false);

            checkToSelectItems();
        }

        return touched;
    }


    private void checkToSelectItems() {
        for (SsItem item : items) {
            if (item.isTouched(currentTouch)) {
                item.select();
            }
        }
    }


    @Override
    public boolean touchDrag(int screenX, int screenY, int pointer) {
        if (touched) {
            updateCurrentTouch(screenX, screenY);

            scrollEngineYio.setSpeed(currentTouch.y - lastTouch.y);

            clickDetector.touchDrag(currentTouch);
        }

        return touched;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        updateCurrentTouch(screenX, screenY);
        scrollEngineYio.updateCanSoftCorrect(true);

        if (touched) {
            touched = false;
            clickDetector.touchUp(currentTouch);

            if (clickDetector.isClicked()) {
                onClick();
            }

            return true;
        }

        return false;
    }


    private void onClick() {
        scrollEngineYio.setSpeed(0);

        for (SsItem item : items) {
            if (item.isTouched(currentTouch)) {
                onItemClicked(item);
            }
        }
    }


    private void onItemClicked(SsItem item) {
        clickedItem = item;

        SoundControllerYio.playSound(SoundControllerYio.soundPressButton);
    }


    @Override
    public boolean onMouseWheelScrolled(int amount) {
        if (amount == 1) {
            scrollEngineYio.giveImpulse(0.02 * GraphicsYio.width);
        } else if (amount == -1) {
            scrollEngineYio.giveImpulse(-0.02 * GraphicsYio.width);
        }

        return true;
    }


    @Override
    public void setTouchable(boolean touchable) {

    }


    public void setOperationType(boolean operationType) {
        this.operationType = operationType;

        updateAll();
    }


    @Override
    public boolean isButton() {
        return false;
    }


    @Override
    public void setPosition(RectangleYio position) {
        this.position.setBy(position);

        onPositionChanged();
    }


    private void onPositionChanged() {
        updateMetrics();
        scrollEngineYio.setSlider(0, position.height - topLabelOffset);
        updateScrollEngineLimits();
    }


    @Override
    public MenuRender getRenderSystem() {
        return MenuRender.renderSaveSlotSelector;
    }
}
