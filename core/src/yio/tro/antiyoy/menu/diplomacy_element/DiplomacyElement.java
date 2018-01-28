package yio.tro.antiyoy.menu.diplomacy_element;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;
import java.util.Collections;

import yio.tro.antiyoy.SoundControllerYio;
import yio.tro.antiyoy.factor_yio.FactorYio;
import yio.tro.antiyoy.gameplay.ClickDetector;
import yio.tro.antiyoy.gameplay.GameController;
import yio.tro.antiyoy.gameplay.diplomacy.DiplomacyManager;
import yio.tro.antiyoy.gameplay.diplomacy.DiplomaticContract;
import yio.tro.antiyoy.gameplay.diplomacy.DiplomaticEntity;
import yio.tro.antiyoy.gameplay.diplomacy.DiplomaticRelation;
import yio.tro.antiyoy.menu.InterfaceElement;
import yio.tro.antiyoy.menu.MenuControllerYio;
import yio.tro.antiyoy.menu.render.MenuRender;
import yio.tro.antiyoy.stuff.Fonts;
import yio.tro.antiyoy.stuff.GraphicsYio;
import yio.tro.antiyoy.stuff.LanguagesManager;
import yio.tro.antiyoy.stuff.PointYio;
import yio.tro.antiyoy.stuff.RectangleYio;
import yio.tro.antiyoy.stuff.Yio;
import yio.tro.antiyoy.stuff.object_pool.ObjectPoolYio;
import yio.tro.antiyoy.stuff.scroll_engine.ScrollEngineYio;

public class DiplomacyElement extends InterfaceElement {


    MenuControllerYio menuControllerYio;
    public RectangleYio position, viewPosition;
    public RectangleYio internalBackground, topCover;
    public FactorYio appearFactor;
    PointYio currentTouch, lastTouch;
    public ArrayList<DeItem> items;
    float hook;
    float itemHeight;
    ScrollEngineYio scrollEngineYio;
    ClickDetector clickDetector;
    public BitmapFont titleFont, descFont;
    DeItem clickedItem, selectedItem;
    DeIcon clickedIcon;
    boolean touched, touchedScrollArea;
    float topLabelHeight;
    ObjectPoolYio<DeItem> poolItems;
    public ArrayList<DeIcon> icons;
    private float iconTouchOffset;
    private float iconRadius;
    public final DeLabel label;


    public DiplomacyElement(MenuControllerYio menuControllerYio, int id) {
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
        label = new DeLabel(this);
        items = new ArrayList<>();
        clickedItem = null;
        internalBackground = new RectangleYio();
        topCover = new RectangleYio();
        touchedScrollArea = false;
        clickedIcon = null;
        selectedItem = null;

        initPool();
        initMetrics();
        initScrollEngine();
        initIcons();
    }


    private void initIcons() {
        icons = new ArrayList<>();

        addIcon(DeIcon.ACTION_LIKE);
        addIcon(DeIcon.ACTION_DISLIKE);
        addIcon(DeIcon.ACTION_BLACK_MARK);
        addIcon(DeIcon.ACTION_INFO);

        for (DeIcon icon : icons) {
            icon.setTouchOffset(iconTouchOffset);
            icon.setRadius(iconRadius);
        }
    }


    private void addIcon(int action) {
        DeIcon deIcon = new DeIcon(this);

        deIcon.setAction(action);

        icons.add(deIcon);
    }


    private void initPool() {
        poolItems = new ObjectPoolYio<DeItem>() {
            @Override
            public DeItem makeNewObject() {
                return new DeItem(DiplomacyElement.this);
            }
        };
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
        return items.size() * itemHeight - topLabelHeight;
    }


    private void initMetrics() {
        itemHeight = 0.2f * GraphicsYio.width;
        topLabelHeight = itemHeight / 2;
        iconRadius = 0.42f * topLabelHeight;
        iconTouchOffset = iconRadius;
    }


    private void updateItems() {
        clearItems();

        GameController gameController = getGameController();
        DiplomacyManager diplomacyManager = getDiplomacyManager(gameController);

        DiplomaticEntity mainEntity = diplomacyManager.getEntity(gameController.turn);

        for (DiplomaticEntity relationEntity : mainEntity.relations.keySet()) {
            addItem(relationEntity);
        }

        updateStatuses();
        sortItems();
    }


    private void updateStatuses() {
        GameController gameController = getGameController();
        DiplomacyManager diplomacyManager = getDiplomacyManager(gameController);

        DiplomaticEntity mainEntity = diplomacyManager.getEntity(gameController.turn);
        if (!mainEntity.isHuman()) return;

        for (DeItem item : items) {
            DiplomaticEntity relationEntity = diplomacyManager.getEntity(item.colorIndex);
            item.setStatus(convertRelationIntoStatus(mainEntity, relationEntity));
            item.setBlackMarkEnabled(mainEntity.isBlackMarkedWith(relationEntity));
            item.setDescriptionString(getItemDescription(mainEntity, relationEntity));
        }
    }


    private DiplomacyManager getDiplomacyManager(GameController gameController) {
        return gameController.fieldController.diplomacyManager;
    }


    private GameController getGameController() {
        return menuControllerYio.yioGdxGame.gameController;
    }


    private String getItemDescription(DiplomaticEntity mainEntity, DiplomaticEntity relationEntity) {
        if (!relationEntity.alive) return LanguagesManager.getInstance().getString("dead");

        switch (mainEntity.getRelation(relationEntity)) {
            default:
            case DiplomaticRelation.NEUTRAL:
                return getNeutralItemDescription(mainEntity, relationEntity);
            case DiplomaticRelation.FRIEND:
                return getFriendItemDescription(mainEntity, relationEntity);
            case DiplomaticRelation.ENEMY:
                return getEnemyItemDescription(mainEntity, relationEntity);
        }
    }


    private String getEnemyItemDescription(DiplomaticEntity mainEntity, DiplomaticEntity relationEntity) {
        LanguagesManager instance = LanguagesManager.getInstance();

        return instance.getString(getRelationStringKey(mainEntity, relationEntity));
    }


    private String getNeutralItemDescription(DiplomaticEntity mainEntity, DiplomaticEntity relationEntity) {
        LanguagesManager instance = LanguagesManager.getInstance();

        DiplomacyManager diplomacyManager = getDiplomacyManager(getGameController());
        DiplomaticContract contract = diplomacyManager.findContract(DiplomaticContract.TYPE_PIECE, mainEntity, relationEntity);
        if (contract != null) {
            return instance.getString(getRelationStringKey(mainEntity, relationEntity)) +
                    " [" + contract.getDotationsStringFromEntityPerspective(mainEntity) +
                    ", " + contract.getExpireCountDown() + "x]";
        }

        return instance.getString(getRelationStringKey(mainEntity, relationEntity));
    }


    private String getFriendItemDescription(DiplomaticEntity mainEntity, DiplomaticEntity relationEntity) {
        LanguagesManager instance = LanguagesManager.getInstance();

        GameController gameController = getGameController();
        DiplomacyManager diplomacyManager = getDiplomacyManager(gameController);
        DiplomaticContract contract = diplomacyManager.findContract(DiplomaticContract.TYPE_FRIENDSHIP, mainEntity, relationEntity);
        return instance.getString(getRelationStringKey(mainEntity, relationEntity)) +
                " [" + contract.getDotationsStringFromEntityPerspective(mainEntity) +
                ", " + contract.getExpireCountDown() + "x]";
    }


    private String getRelationStringKey(DiplomaticEntity mainEntity, DiplomaticEntity relationEntity) {
        int relation = mainEntity.getRelation(relationEntity);

        switch (relation) {
            default:
                return "-";
            case DiplomaticRelation.NEUTRAL:
                return "neutral";
            case DiplomaticRelation.FRIEND:
                return "friend";
            case DiplomaticRelation.ENEMY:
                return "enemy";
        }
    }


    private void sortItems() {
        Collections.sort(items);
    }


    private void addItem(DiplomaticEntity relationEntity) {
        DeItem deItem = poolItems.getNext();

        deItem.setColorIndex(relationEntity.color);
        deItem.setTitle(relationEntity.capitalName);

        items.add(deItem);
    }


    private int convertRelationIntoStatus(DiplomaticEntity mainEntity, DiplomaticEntity relationEntity) {
        if (!relationEntity.alive) return DeItem.STATUS_DEAD;

        int relation = mainEntity.getRelation(relationEntity);

        switch (relation) {
            default:
                Yio.printStackTrace();
                return DeItem.STATUS_DEAD;
            case DiplomaticRelation.NEUTRAL:
                return DeItem.STATUS_NEUTRAL;
            case DiplomaticRelation.FRIEND:
                return DeItem.STATUS_FRIEND;
            case DiplomaticRelation.ENEMY:
                return DeItem.STATUS_ENEMY;
        }
    }


    private void clearItems() {
        for (DeItem item : items) {
            poolItems.add(item);
        }

        items.clear();
    }


    void updateMetrics() {
        float currentY = (float) position.height - topLabelHeight - itemHeight;

        for (DeItem item : items) {
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
        scrollEngineYio.move();
        updateHook();
        moveItems();
        moveIcons();
        label.move();
        updateInternalBackgroundPosition();
        updateCover();
    }


    private void moveIcons() {
        for (DeIcon icon : icons) {
            icon.move();
        }
    }


    private void updateCover() {
        topCover.setBy(viewPosition);
        topCover.height = topLabelHeight;
        topCover.y = viewPosition.y + viewPosition.height - topCover.height;
    }


    private void updateInternalBackgroundPosition() {
        internalBackground.x = viewPosition.x;
        internalBackground.y = viewPosition.y;
        internalBackground.width = viewPosition.width;
        internalBackground.height = viewPosition.height - topLabelHeight;
    }


    private void moveFactors() {
        if (!appearFactor.hasToMove()) return;

        appearFactor.move();
    }


    private void updateHook() {
        hook = +(float) scrollEngineYio.getSlider().a;
    }


    private void moveItems() {
        for (DeItem item : items) {
            item.move();

            if (!touched) {
                item.moveSelection();
            }
        }
    }


    private void updateViewPosition() {
        viewPosition.setBy(position);

        if (appearFactor.get() < 1) {
            viewPosition.y -= (float) ((1 - appearFactor.get()) * 1.05 * position.height);
        }
    }


    @Override
    public FactorYio getFactor() {
        return appearFactor;
    }


    @Override
    public void destroy() {
        appearFactor.destroy(1, 2.2);

        onDestroy();
    }


    private void onDestroy() {
//        dropSelections();
    }


    @Override
    public void appear() {
        appearFactor.setValues(0.01, 0);
        appearFactor.appear(3, 1.25);

        onAppear();
    }


    private void onAppear() {
        label.setVisible(true);
        selectedItem = null;

        updateAll();
        dropSelections();

        scrollEngineYio.resetToBottom();
    }


    public void updateAll() {
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
            performItemClickAction();

            clickedItem = null;
            return true;
        }

        if (clickedIcon != null) {
            performIconClickAction();

            clickedIcon = null;
            return true;
        }

        return false;
    }


    private void performIconClickAction() {
        GameController gameController = getGameController();
        DiplomacyManager diplomacyManager = getDiplomacyManager(gameController);
        diplomacyManager.onUserClickedContextIcon(selectedItem.colorIndex, clickedIcon.action);
    }


    private void performItemClickAction() {

    }


    public void onRelationsChanged() {
        updateStatuses();
        showIcons();
    }


    public void onFirstPlayerTurnEnded() {
        updateStatuses();
    }


    private void resetToLabel() {
        dropSelections();
        label.setVisible(true);
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
        touched = (currentTouch.y < position.y + position.height);
        touchedScrollArea = (currentTouch.y < position.y + position.height - topLabelHeight);

        if (touched) {
            clickDetector.touchDown(currentTouch);

            if (touchedScrollArea) {
                scrollEngineYio.updateCanSoftCorrect(false);
                checkToSelectItems();
            } else {
                checkToSelectIcons();
            }
        } else {
            destroy();
        }

        return true;
    }


    private void checkToSelectIcons() {
        for (DeIcon icon : icons) {
            if (icon.isTouched(currentTouch)) {
                icon.select();
            }
        }
    }


    private void checkToSelectItems() {
        for (DeItem item : items) {
            if (item.isTouched(currentTouch)) {
                item.select();
            }
        }
    }


    @Override
    public boolean touchDrag(int screenX, int screenY, int pointer) {
        if (touched) {
            updateCurrentTouch(screenX, screenY);

            if (touchedScrollArea) {
                scrollEngineYio.setSpeed(currentTouch.y - lastTouch.y);
            }

            clickDetector.touchDrag(currentTouch);
        }

        return touched;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        updateCurrentTouch(screenX, screenY);

        if (touchedScrollArea) {
            scrollEngineYio.updateCanSoftCorrect(true);
        }

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
        if (touchedScrollArea) {
            onClickInsideScrollArea();
        } else {
            onClickIcons();
        }
    }


    private void onClickIcons() {
        for (DeIcon icon : icons) {
            if (icon.isTouched(currentTouch)) {
                onIconClicked(icon);
            }
        }
    }


    private void onIconClicked(DeIcon icon) {
        SoundControllerYio.playSound(SoundControllerYio.soundPressButton);

        clickedIcon = icon;
    }


    private void onClickInsideScrollArea() {
        if (!internalBackground.isPointInside(currentTouch, 0)) return;

        scrollEngineYio.setSpeed(0);

        for (DeItem item : items) {
            if (item.isTouched(currentTouch)) {
                onItemClicked(item);
            }
        }
    }


    private void onItemClicked(DeItem item) {
        SoundControllerYio.playSound(SoundControllerYio.soundPressButton);

        if (item.keepSelection) {
            resetToLabel();
            return;
        }

        dropSelections();
        if (item.status == DeItem.STATUS_DEAD) return;

        clickedItem = item;
        selectedItem = item;

        clickedItem.select();
        clickedItem.setKeepSelection(true);

        showIcons();
    }


    void dropSelections() {
        for (DeItem item : items) {
            item.setKeepSelection(false);
        }

        resetIconsVisibility();
    }


    private void resetIconsVisibility() {
        for (DeIcon icon : icons) {
            icon.visible = false;
        }
    }


    void showIcons() {
        if (selectedItem == null) return;
        if (appearFactor.get() == 0) return;

        label.setVisible(false);
        resetIconsVisibility();

        GameController gameController = getGameController();
        DiplomacyManager diplomacyManager = getDiplomacyManager(gameController);
        int colorIndex = selectedItem.colorIndex;
        DiplomaticEntity selectedEntity = diplomacyManager.getEntity(colorIndex);
        int relation = selectedEntity.getRelation(diplomacyManager.getEntity(gameController.turn));
        DiplomaticEntity mainEntity = diplomacyManager.getMainEntity();
        boolean blackMarked = mainEntity.isBlackMarkedWith(selectedEntity);

        switch (relation) {
            case DiplomaticRelation.NEUTRAL:
                enableIcon(DeIcon.ACTION_LIKE);
                enableIcon(DeIcon.ACTION_DISLIKE);
                if (!blackMarked) {
                    enableIcon(DeIcon.ACTION_BLACK_MARK);
                }
                enableIcon(DeIcon.ACTION_INFO);
                break;
            case DiplomaticRelation.FRIEND:
                enableIcon(DeIcon.ACTION_DISLIKE);
                enableIcon(DeIcon.ACTION_INFO);
                break;
            case DiplomaticRelation.ENEMY:
                enableIcon(DeIcon.ACTION_LIKE);
                if (!blackMarked) {
                    enableIcon(DeIcon.ACTION_BLACK_MARK);
                }
                break;
        }

        alignIcons();
        appearIcons();
    }


    private void appearIcons() {
        for (DeIcon icon : icons) {
            icon.appear();
        }
    }


    private void alignIcons() {
        int n = getNumberOfVisibleIcons();
        float iDelta = 2 * iconTouchOffset + 2 * iconRadius;
        float fullWidth = iDelta * (n - 1);
        float currentX = (float) (position.width / 2 - fullWidth / 2);

        for (DeIcon icon : icons) {
            if (!icon.visible) continue;

            icon.delta.x = currentX;
            icon.delta.y = (float) (position.height - topLabelHeight / 2);

            currentX += iDelta;
        }
    }


    int getNumberOfVisibleIcons() {
        int c = 0;

        for (DeIcon icon : icons) {
            if (icon.visible) {
                c++;
            }
        }

        return c;
    }


    void enableIcon(int action) {
        getIcon(action).visible = true;
    }


    DeIcon getIcon(int action) {
        for (DeIcon icon : icons) {
            if (icon.action == action) {
                return icon;
            }
        }

        return null;
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


    @Override
    public boolean isButton() {
        return false;
    }


    public boolean needToRenderInternalBackground() {
        return scrollEngineYio.isOverTop() || scrollEngineYio.isBelowBottom();
    }


    @Override
    public void setPosition(RectangleYio position) {
        this.position.setBy(position);

        onPositionChanged();
    }


    private void onPositionChanged() {
        updateMetrics();
        scrollEngineYio.setSlider(0, position.height - itemHeight);
        updateScrollEngineLimits();
    }


    @Override
    public MenuRender getRenderSystem() {
        return MenuRender.renderDiplomacyElement;
    }
}
