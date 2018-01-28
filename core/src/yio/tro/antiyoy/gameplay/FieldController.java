package yio.tro.antiyoy.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;
import java.util.ListIterator;

import yio.tro.antiyoy.Settings;
import yio.tro.antiyoy.SoundControllerYio;
import yio.tro.antiyoy.YioGdxGame;
import yio.tro.antiyoy.factor_yio.FactorYio;
import yio.tro.antiyoy.gameplay.diplomacy.DiplomacyManager;
import yio.tro.antiyoy.gameplay.fog_of_war.FogOfWarManager;
import yio.tro.antiyoy.gameplay.game_view.GameView;
import yio.tro.antiyoy.gameplay.rules.GameRules;
import yio.tro.antiyoy.menu.scenes.Scenes;
import yio.tro.antiyoy.stuff.GraphicsYio;
import yio.tro.antiyoy.stuff.PointYio;
import yio.tro.antiyoy.stuff.Yio;

public class FieldController {

    public final GameController gameController;
    public static int NEUTRAL_LANDS_INDEX = 7;
    public static final int SIZE_SMALL = 1;
    public static final int SIZE_MEDIUM = 2;
    public static final int SIZE_BIG = 4;
    private final MoveZoneDetection moveZoneDetection;
    public boolean letsCheckAnimHexes;
    public float hexSize;
    public float hexStep1;
    public float hexStep2;
    public Hex field[][];
    public ArrayList<Hex> activeHexes;
    public ArrayList<Hex> selectedHexes;
    public ArrayList<Hex> animHexes;
    public int fWidth;
    public int fHeight;
    public int levelSize;
    public PointYio fieldPos;
    public float cos60;
    public float sin60;
    public Hex focusedHex;
    public Hex emptyHex;
    public Hex responseAnimHex;
    public Hex defTipHex;
    public ArrayList<Hex> solidObjects;
    public ArrayList<Hex> moveZone;
    public ArrayList<Hex> defenseTips;
    public FactorYio responseAnimFactor;
    public FactorYio moveZoneFactor;
    public FactorYio defenseTipFactor;
    public ArrayList<Province> provinces;
    public Province selectedProvince;
    public int selectedProvinceMoney;
    public long timeToCheckAnimHexes;
    public int[] playerHexCount;
    public float compensatoryOffset; // fix for widescreen
    public FogOfWarManager fogOfWarManager;
    public DiplomacyManager diplomacyManager;


    public FieldController(GameController gameController) {
        this.gameController = gameController;

        cos60 = (float) Math.cos(Math.PI / 3d);
        sin60 = (float) Math.sin(Math.PI / 3d);
        fieldPos = new PointYio();
        compensatoryOffset = 0;
        updateFieldPos();
        hexSize = 0.05f * Gdx.graphics.getWidth();
        hexStep1 = (float) Math.sqrt(3) * hexSize;
        hexStep2 = (float) Yio.distance(0, 0, 1.5 * hexSize, 0.5 * hexStep1);
        fWidth = 46;
        fHeight = 30;
        activeHexes = new ArrayList<Hex>();
        selectedHexes = new ArrayList<Hex>();
        animHexes = new ArrayList<Hex>();
        solidObjects = new ArrayList<Hex>();
        moveZone = new ArrayList<Hex>();
        field = new Hex[fWidth][fHeight];
        responseAnimFactor = new FactorYio();
        moveZoneFactor = new FactorYio();
        provinces = new ArrayList<Province>();
        emptyHex = new Hex(-1, -1, new PointYio(), this);
        emptyHex.active = false;
        defenseTipFactor = new FactorYio();
        defenseTips = new ArrayList<Hex>();
        moveZoneDetection = new MoveZoneDetection(this);
        fogOfWarManager = new FogOfWarManager(this);
        diplomacyManager = new DiplomacyManager(this);
    }


    private void updateFieldPos() {
        fieldPos.y = -0.5f * GraphicsYio.height + compensatoryOffset;
    }


    public void updateHexInsideLevelStatuses() {
        for (int i = 0; i < fWidth; i++) {
            for (int j = 0; j < fHeight; j++) {
                field[i][j].updateCanContainsObjects();
            }
        }
    }


    public void clearField() {
        gameController.selectionController.setSelectedUnit(null);
        solidObjects.clear();
        gameController.getUnitList().clear();
        clearProvincesList();
        moveZone.clear();
        clearActiveHexesList();
    }


    public void cleanOutAllHexesInField() {
        for (int i = 0; i < fWidth; i++) {
            for (int j = 0; j < fHeight; j++) {
                if (!gameController.fieldController.field[i][j].active) continue;
                gameController.cleanOutHex(gameController.fieldController.field[i][j]);
            }
        }
    }


    public void clearProvincesList() {
        provinces.clear();
    }


    public void defaultValues() {
        selectedProvince = null;
        moveZoneFactor.setValues(0, 0);
        compensatoryOffset = 0;
    }


    public void clearActiveHexesList() {
        ListIterator listIterator = activeHexes.listIterator();
        while (listIterator.hasNext()) {
            listIterator.next();
            listIterator.remove();
        }
    }


    public void createField() {
        clearField();
        updateFieldPos();
    }


    public void generateMap() {
        generateMap(GameRules.slayRules);
    }


    public void generateMap(boolean slayRules) {
        if (slayRules) {
            gameController.getMapGeneratorSlay().generateMap(gameController.getPredictableRandom(), field);
        } else {
            gameController.getMapGeneratorGeneric().generateMap(gameController.getPredictableRandom(), field);
        }

        detectProvinces();
        gameController.selectionController.deselectAll();
        detectNeutralLands();
        gameController.takeAwaySomeMoneyToAchieveBalance();
    }


    public void detectNeutralLands() {
        if (GameRules.slayRules) return;

        for (Hex activeHex : activeHexes) {
            activeHex.genFlag = false;
        }

        for (Province province : provinces) {
            for (Hex hex : province.hexList) {
                hex.genFlag = true;
            }
        }

        for (Hex activeHex : activeHexes) {
            if (activeHex.genFlag) continue;

            activeHex.setColorIndex(NEUTRAL_LANDS_INDEX);
        }
    }


    public void killUnitByStarvation(Hex hex) {
        cleanOutHex(hex);
        addSolidObject(hex, Obj.GRAVE);
        hex.animFactor.appear(1, 2);

        gameController.replayManager.onUnitDiedFromStarvation(hex);
    }


    public void killEveryoneByStarvation(Province province) {
        for (Hex hex : province.hexList) {
            if (hex.containsUnit()) {
                killUnitByStarvation(hex);
            }
        }
    }


    public void moveResponseAnimHex() {
        if (responseAnimHex != null) {
            responseAnimFactor.move();
            if (responseAnimFactor.get() < 0.01) responseAnimHex = null;
        }
    }


    public void moveAnimHexes() {
        for (Hex hex : animHexes) {
            if (!hex.selected) hex.move(); // to prevent double call of move()
            if (!letsCheckAnimHexes && hex.animFactor.get() > 0.99) {
                letsCheckAnimHexes = true;
            }

            // animation is off because it's buggy
            if (hex.animFactor.get() < 1) hex.animFactor.setValues(1, 0);
        }
    }


    public int numberOfActiveProvinces() {
        int c = 0;
        for (Province province : provinces) {
            if (province.hexList.get(0).isNeutral()) continue;
            c++;
        }
        return c;
    }


    public int[] getPlayerHexCount() {
        for (int i = 0; i < playerHexCount.length; i++) {
            playerHexCount[i] = 0;
        }

        for (Hex activeHex : activeHexes) {
            if (activeHex.isNeutral()) continue;
            if (activeHex.isInProvince() && activeHex.colorIndex >= 0 && activeHex.colorIndex < playerHexCount.length) {
                playerHexCount[activeHex.colorIndex]++;
            }
        }

        return playerHexCount;
    }


    public String getFullLevelString() {
//        detectProvinces();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getBasicInfoString());
        stringBuffer.append("/");
        stringBuffer.append(gameController.gameSaver.getActiveHexesString());
        return stringBuffer.toString();
    }


    private String getBasicInfoString() {
        StringBuilder builder = new StringBuilder();
        builder.append(GameRules.difficulty).append(" ");
        builder.append(levelSize).append(" ");
        builder.append(gameController.playersNumber).append(" ");
        builder.append(GameRules.colorNumber).append("");
        return builder.toString();
    }


    private boolean checkRefuseStatistics() {
        RefuseStatistics instance = RefuseStatistics.getInstance();

        int sum = instance.refusedEarlyGameEnd + instance.acceptedEarlyGameEnd;
        if (sum < 5) return true;

        double ratio = (double) instance.acceptedEarlyGameEnd / (double) sum;

        if (ratio < 0.1) return false;

        return true;
    }


    public int possibleWinner() {
        if (!checkRefuseStatistics()) return -1;

        int numberOfAllHexes = activeHexes.size();
//        for (Province province : provinces) {
//            if (province.hexList.size() > 0.52 * numberOfAllHexes) {
//                return province.getColor();
//            }
//        }

        int playerHexCount[] = getPlayerHexCount();
        for (int i = 0; i < playerHexCount.length; i++) {
            if (playerHexCount[i] > 0.7 * numberOfAllHexes) {
                return i;
            }
        }

        return -1;
    }


    public int numberOfProvincesWithColor(int color) {
        int count = 0;
        for (Province province : provinces) {
            if (province.getColor() == color)
                count++;
        }
        return count;
    }


    public void transformGraves() {
        for (Hex hex : activeHexes) {
            if (gameController.isCurrentTurn(hex.colorIndex) && hex.objectInside == Obj.GRAVE) {
                spawnTree(hex);
                hex.blockToTreeFromExpanding = true;
            }
        }
    }


    public void detectProvinces() {
        if (gameController.isInEditorMode()) return;

        clearProvincesList();
        MoveZoneDetection.unFlagAllHexesInArrayList(activeHexes);
        ArrayList<Hex> tempList = new ArrayList<>();
        ArrayList<Hex> propagationList = new ArrayList<>();

        for (Hex hex : activeHexes) {
            if (hex.isNeutral()) continue;
            if (hex.flag) continue;

            tempList.clear();
            propagationList.clear();
            propagationList.add(hex);
            hex.flag = true;
            propagateHex(tempList, propagationList);
            if (tempList.size() >= 2) {
                Province province = new Province(gameController, tempList);
                addProvince(province);
            }
        }

        for (Province province : provinces) {
            if (!province.hasCapital()) {
                province.placeCapitalInRandomPlace(gameController.predictableRandom);
            }
        }
    }


    private void propagateHex(ArrayList<Hex> tempList, ArrayList<Hex> propagationList) {
        Hex tempHex;
        Hex adjHex;
        while (propagationList.size() > 0) {
            tempHex = propagationList.get(0);
            tempList.add(tempHex);
            propagationList.remove(0);
            for (int i = 0; i < 6; i++) {
                adjHex = tempHex.getAdjacentHex(i);
                if (adjHex.active && adjHex.sameColor(tempHex) && !adjHex.flag) {
                    propagationList.add(adjHex);
                    adjHex.flag = true;
                }
            }
        }
    }


    public void forceAnimEndInHex(Hex hex) {
        hex.animFactor.setValues(1, 0);
    }


    public int howManyPalms() {
        int c = 0;
        for (Hex activeHex : activeHexes) {
            if (activeHex.objectInside == Obj.PALM) c++;
        }
        return c;
    }


    public void expandTrees() {
        if (GameRules.replayMode) return;

        ArrayList<Hex> newPalmsList = getNewPalmsList();
        ArrayList<Hex> newPinesList = getNewPinesList();

        for (int i = newPalmsList.size() - 1; i >= 0; i--) {
            spawnPalm(newPalmsList.get(i));
        }

        for (int i = newPinesList.size() - 1; i >= 0; i--) {
            spawnPine(newPinesList.get(i));
        }

        for (Hex activeHex : activeHexes) {
            if (activeHex.containsTree() && activeHex.blockToTreeFromExpanding) {
                activeHex.blockToTreeFromExpanding = false;
            }
        }
    }


    private ArrayList<Hex> getNewPinesList() {
        ArrayList<Hex> newPinesList = new ArrayList<Hex>();

        for (Hex hex : activeHexes) {
            if (gameController.ruleset.canSpawnPineOnHex(hex)) {
                newPinesList.add(hex);
            }
        }

        return newPinesList;
    }


    private ArrayList<Hex> getNewPalmsList() {
        ArrayList<Hex> newPalmsList = new ArrayList<Hex>();

        for (Hex hex : activeHexes) {
            if (gameController.ruleset.canSpawnPalmOnHex(hex)) {
                newPalmsList.add(hex);
            }
        }

        return newPalmsList;
    }


    private void spawnPine(Hex hex) {
        if (!hex.canContainObjects) return;

        addSolidObject(hex, Obj.PINE);
        addAnimHex(hex);
        hex.animFactor.setValues(1, 0);
        gameController.replayManager.onPineSpawned(hex);
    }


    private void spawnPalm(Hex hex) {
        if (!hex.canContainObjects) return;

        addSolidObject(hex, Obj.PALM);
        addAnimHex(hex);
        hex.animFactor.setValues(1, 0);
        gameController.replayManager.onPalmSpawned(hex);
    }


    public void createPlayerHexCount() {
        playerHexCount = new int[GameRules.colorNumber];
    }


    public void checkAnimHexes() {
        // important
        // this fucking anims hexes have to live long enough
        // if killed too fast, graphic bugs will show
        if (gameController.isSomethingMoving()) {
            timeToCheckAnimHexes = gameController.getCurrentTime() + 100;
            return;
        }
        letsCheckAnimHexes = false;
        ListIterator iterator = animHexes.listIterator();
        while (iterator.hasNext()) {
            Hex h = (Hex) iterator.next();
            if (h.animFactor.get() > 0.99 && !(h.containsUnit() && h.unit.moveFactor.get() < 1) && System.currentTimeMillis() > h.animStartTime + 250) {
                h.changingColor = false;
                iterator.remove();
            }
        }
    }


    public boolean atLeastOneUnitIsReadyToMove() {
        int size = gameController.getUnitList().size();
        for (Unit unit : gameController.getUnitList()) {
            if (unit.isReadyToMove()) return true;
        }
        return false;
    }


    public int getPredictionForWinner() {
        int numbers[] = new int[GameRules.colorNumber];
        for (Hex activeHex : activeHexes) {
            if (activeHex.isNeutral()) continue;
            numbers[activeHex.colorIndex]++;
        }

        int max = numbers[0];
        int maxIndex = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }


    public boolean areConditionsGoodForPlayer() {
        int numbers[] = new int[GameRules.colorNumber];
        for (Hex activeHex : activeHexes) {
            if (activeHex.isNeutral()) continue;
            numbers[activeHex.colorIndex]++;
        }

        int max = GameController.maxNumberFromArray(numbers);
        return max - numbers[0] < 2;
    }


    public void onEndCreation() {
        clearAnims();
        updateHexInsideLevelStatuses();

        fogOfWarManager.onEndCreation();
        diplomacyManager.onEndCreation();
    }


    public void clearAnims() {
        ListIterator iterator = animHexes.listIterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }


    public void createFieldMatrix() {
        for (int i = 0; i < fWidth; i++) {
            field[i] = new Hex[fHeight];
            for (int j = 0; j < fHeight; j++) {
                field[i][j] = new Hex(i, j, fieldPos, this);
                field[i][j].ignoreTouch = false;
            }
        }
    }


    public void marchUnitsToHex(Hex toWhere) {
        if (!gameController.selectionController.isSomethingSelected()) return;
        if (!toWhere.isSelected()) return;
        if (selectedProvince.hasSomeoneReadyToMove()) {
            gameController.takeSnapshot();
            for (Hex hex : selectedProvince.hexList) {
                if (hex.containsUnit() && hex.unit.isReadyToMove()) {
                    hex.unit.marchToHex(toWhere, selectedProvince);
                }
            }
        }
        setResponseAnimHex(toWhere);
        SoundControllerYio.playSound(SoundControllerYio.soundHoldToMarch);
    }


    public void setResponseAnimHex(Hex hex) {
        responseAnimHex = hex;
        responseAnimFactor.setValues(1, 0.07);
        responseAnimFactor.destroy(1, 2);
    }


    public void hideMoveZone() {
        moveZoneFactor.destroy(1, 5);
        gameController.selectionController.getBlackoutFactor().destroy(1, 5);
    }


    public void selectAdjacentHexes(Hex startHex) {
        setSelectedProvince(startHex);
        ListIterator listIterator = selectedHexes.listIterator();
        for (Hex hex : selectedProvince.hexList) {
            hex.select();
            if (!selectedHexes.contains(hex)) listIterator.add(hex);
        }
        showBuildOverlay();
        gameController.updateBalanceString();
    }


    private void showBuildOverlay() {
        if (Settings.fastConstruction) {
            Scenes.sceneFastConstructionPanel.create();
        } else {
            Scenes.sceneBuildButtons.create();
        }
    }


    public void setSelectedProvince(Hex hex) {
        selectedProvince = getProvinceByHex(hex);
        selectedProvinceMoney = selectedProvince.money;
        gameController.selectionController.getSelMoneyFactor().setDy(0);
        gameController.selectionController.getSelMoneyFactor().appear(3, 2);
    }


    public String getColorName(int colorIndex) {
        return gameController.yioGdxGame.menuControllerYio.getColorNameByIndex(colorIndex, "_player");
    }


    public void updateHexPositions() {
        updateFieldPos();

        for (int i = 0; i < fWidth; i++) {
            for (int j = 0; j < fHeight; j++) {
                Hex hex = field[i][j];

                hex.updatePos();
                if (hex.containsUnit()) {
                    hex.unit.updateCurrentPos();
                }
            }
        }
    }


    public Hex getHexByPos(double x, double y) {
        int j = (int) ((x - fieldPos.x) / (hexStep2 * sin60));
        int i = (int) ((y - fieldPos.y - hexStep2 * j * cos60) / hexStep1);
        if (i < 0 || i > fWidth - 1 || j < 0 || j > fHeight - 1) return null;

        Hex adjHex, resHex = field[i][j];
        x -= gameController.getYioGdxGame().gameView.hexViewSize;
        y -= gameController.getYioGdxGame().gameView.hexViewSize;

        double currentDistance, minDistance = Yio.distance(resHex.pos.x, resHex.pos.y, x, y);
        for (int k = 0; k < 6; k++) {
            adjHex = adjacentHex(field[i][j], k);
            if (adjHex == null || !adjHex.active) continue;
            currentDistance = Yio.distance(adjHex.pos.x, adjHex.pos.y, x, y);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                resHex = adjHex;
            }
        }

        return resHex;
    }


    public Hex getHex(int i, int j) {
        if (i < 0 || i > fWidth - 1 || j < 0 || j > fHeight - 1) return null;

        return field[i][j];
    }


    public boolean isPointInsideLevelBoundsHorizontally(PointYio pointYio) {
        if (pointYio.x < fieldPos.x + hexSize / 2) return false;
        if (pointYio.x > fieldPos.x + gameController.boundWidth) return false;
//        if (pointYio.y < fieldPos.y) return false;
//        if (pointYio.y > fieldPos.y + gameController.boundHeight) return false;

        return true;
    }


    public boolean isPointInsideLevelBoundsWithOffset(PointYio pointYio, float offset) {
        // bigger offset -> bigger bounds
        if (pointYio.x < fieldPos.x + hexSize / 2 - offset) return false;
        if (pointYio.x > fieldPos.x + gameController.boundWidth + offset) return false;
        if (pointYio.y < hexSize / 2 - offset) return false;
        if (pointYio.y > gameController.boundHeight + offset) return false;

        return true;
    }


    public Hex adjacentHex(int i, int j, int direction) {
        switch (direction) {
            case 0:
                if (i >= fWidth - 1) return emptyHex;
                return field[i + 1][j];
            case 1:
                if (j >= fHeight - 1) return emptyHex;
                return field[i][j + 1];
            case 2:
                if (i <= 0 || j >= fHeight - 1) return emptyHex;
                return field[i - 1][j + 1];
            case 3:
                if (i <= 0) return emptyHex;
                return field[i - 1][j];
            case 4:
                if (j <= 0) return emptyHex;
                return field[i][j - 1];
            case 5:
                if (i >= fWidth - 1 || j <= 0) return emptyHex;
                return field[i + 1][j - 1];
            default:
                return emptyHex;
        }
    }


    public void spawnTree(Hex hex) {
        if (!hex.active) return;
        if (hex.isNearWater()) addSolidObject(hex, Obj.PALM);
        else addSolidObject(hex, Obj.PINE);
    }


    public void addSolidObject(Hex hex, int type) {
        if (hex == null || !hex.active) return;
        if (hex.objectInside == type) return;
        if (!hex.canContainObjects) return;

        if (solidObjects.contains(hex)) {
            cleanOutHex(hex);
        }

        hex.setObjectInside(type);
        solidObjects.listIterator().add(hex);
    }


    public void cleanOutHex(Hex hex) {
        if (hex.containsUnit()) {
            gameController.getMatchStatistics().unitWasKilled();
            gameController.getUnitList().remove(hex.unit);
            hex.unit = null;
        }
        hex.setObjectInside(0);
        addAnimHex(hex);
        ListIterator iterator = solidObjects.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next() == hex) {
                iterator.remove();
                return;
            }
        }
    }


    public void destroyBuildingsOnHex(Hex hex) {
        boolean hadHouse = (hex.objectInside == Obj.TOWN);
        if (hex.containsBuilding()) cleanOutHex(hex);
//        if (hex.containsUnit()) killUnitOnHex(hex);
        if (hadHouse) {
            spawnTree(hex);
        }
    }


    public boolean buildUnit(Province province, Hex hex, int strength) {
        if (province == null || hex == null) return false;

        if (province.canBuildUnit(strength)) {
            // check if can build unit
            if (hex.sameColor(province) && hex.containsUnit() && !gameController.canMergeUnits(strength, hex.unit.strength))
                return false;

            gameController.takeSnapshot();
            province.money -= GameRules.PRICE_UNIT * strength;
            gameController.getMatchStatistics().moneyWereSpent(GameRules.PRICE_UNIT * strength);
            gameController.replayManager.onUnitBuilt(province, hex, strength);
            updateSelectedProvinceMoney();

            if (hex.sameColor(province)) { // build unit peacefully inside province
                if (hex.containsUnit()) { // merge units
                    Unit bUnit = new Unit(gameController, hex, strength);
                    bUnit.setReadyToMove(true);
                    gameController.matchStatistics.unitsDied++;
                    gameController.mergeUnits(hex, bUnit, hex.unit);
                } else {
                    addUnit(hex, strength);
                }
            } else { // attack on other province
                setHexColor(hex, province.getColor()); // must be called before object in hex destroyed
                addUnit(hex, strength);
                hex.unit.setReadyToMove(false);
                hex.unit.stopJumping();
                province.addHex(hex);
                addAnimHex(hex);
                gameController.updateCacheOnceAfterSomeTime();
            }
            gameController.updateBalanceString();
            return true;
        }

        // can't build unit
        if (gameController.isPlayerTurn()) {
            gameController.tickleMoneySign();
        }

        return false;
    }


    public boolean buildTower(Province province, Hex hex) {
        if (province == null) return false;
        if (province.hasMoneyForTower()) {
            gameController.takeSnapshot();
            gameController.replayManager.onTowerBuilt(hex, false);
            addSolidObject(hex, Obj.TOWER);
            addAnimHex(hex);
            province.money -= GameRules.PRICE_TOWER;
            gameController.getMatchStatistics().moneyWereSpent(GameRules.PRICE_TOWER);
            updateSelectedProvinceMoney();
            gameController.updateCacheOnceAfterSomeTime();
            return true;
        }

        // can't build tower
        if (gameController.isPlayerTurn()) gameController.tickleMoneySign();
        return false;
    }


    public boolean buildStrongTower(Province province, Hex hex) {
        if (province == null) return false;

        if (province.hasMoneyForStrongTower()) {
            gameController.takeSnapshot();
            gameController.replayManager.onTowerBuilt(hex, true);
            addSolidObject(hex, Obj.STRONG_TOWER);
            addAnimHex(hex);
            province.money -= GameRules.PRICE_STRONG_TOWER;
            gameController.getMatchStatistics().moneyWereSpent(GameRules.PRICE_STRONG_TOWER);
            updateSelectedProvinceMoney();
            gameController.updateCacheOnceAfterSomeTime();
            return true;
        }

        // can't build tower
        if (gameController.isPlayerTurn()) gameController.tickleMoneySign();
        return false;
    }


    public boolean buildFarm(Province province, Hex hex) {
        if (province == null) return false;
        if (!hex.hasThisObjectNearby(Obj.TOWN) && !hex.hasThisObjectNearby(Obj.FARM)) {
            return false;
        }

        if (province.hasMoneyForFarm()) {
            gameController.takeSnapshot();
            gameController.replayManager.onFarmBuilt(hex);
            province.money -= province.getCurrentFarmPrice();
            gameController.getMatchStatistics().moneyWereSpent(province.getCurrentFarmPrice());
            addSolidObject(hex, Obj.FARM);
            addAnimHex(hex);
            updateSelectedProvinceMoney();
            gameController.updateCacheOnceAfterSomeTime();
            return true;
        }

        // can't build farm
        if (gameController.isPlayerTurn()) gameController.tickleMoneySign();
        return false;
    }


    public boolean buildTree(Province province, Hex hex) {
        if (province == null) return false;
        if (province.hasMoneyForTree()) {
            gameController.takeSnapshot();
            spawnTree(hex);
            addAnimHex(hex);
            province.money -= GameRules.PRICE_TREE;
            gameController.getMatchStatistics().moneyWereSpent(GameRules.PRICE_TREE);
            updateSelectedProvinceMoney();
            gameController.updateCacheOnceAfterSomeTime();
            return true;
        }

        // can't build tree
        if (gameController.isPlayerTurn()) gameController.tickleMoneySign();
        return false;
    }


    public void updateSelectedProvinceMoney() {
        if (selectedProvince != null)
            selectedProvinceMoney = selectedProvince.money;
        else selectedProvinceMoney = -1;
        gameController.updateBalanceString();
    }


    public Unit addUnit(Hex hex, int strength) {
        if (hex == null) return null;
        if (hex.containsObject()) {
            gameController.ruleset.onUnitAdd(hex);
            cleanOutHex(hex);
            gameController.updateCacheOnceAfterSomeTime();
            hex.addUnit(strength);
        } else {
            hex.addUnit(strength);
            if (gameController.isCurrentTurn(hex.colorIndex)) {
                hex.unit.setReadyToMove(true);
                hex.unit.startJumping();
            }
        }
        return hex.unit;
    }


    public void addProvince(Province province) {
        if (provinces.contains(province)) return;
        if (containsEqualProvince(province)) {
            System.out.println("Problem in FieldController.addProvince()");
            Yio.printStackTrace();
            return;
        }

        provinces.add(province);
    }


    public boolean containsEqualProvince(Province province) {
        for (Province p : provinces) {
            if (p.equals(province)) {
                return true;
            }
        }

        return false;
    }


    public Hex adjacentHex(Hex hex, int direction) {
        return adjacentHex(hex.index1, hex.index2, direction);
    }


    public boolean hexHasSelectedNearby(Hex hex) {
        for (int i = 0; i < 6; i++)
            if (hex.getAdjacentHex(i).selected) return true;
        return false;
    }


    public static float distanceBetweenHexes(Hex one, Hex two) {
        PointYio pOne = one.getPos();
        PointYio pTwo = two.getPos();
        return (float) pOne.distanceTo(pTwo);
    }


    public void detectAndShowMoveZoneForBuildingUnit(int strength) {
//        if (selectedHexes.size() == 0) {
//            YioGdxGame.say("detected bug #3128739172, GameController.detectAndShowMoveZoneForBuildingUnit()");
//            return;
//        }
        detectAndShowMoveZone(selectedHexes.get(0), strength);
    }


    public boolean isSomethingSelected() {
        return selectedHexes.size() > 0;
    }


    public void giveMoneyToPlayerProvinces(int amount) {
        for (Province province : provinces) {
            if (province.getColor() == 0) {
                province.money += amount;
            }
        }
    }


    public void detectAndShowMoveZoneForFarm() {
        moveZone = moveZoneDetection.detectMoveZoneForFarm();
        checkToForceMoveZoneAnims();
        moveZoneFactor.setValues(0, 0);
        moveZoneFactor.appear(3, 1.5);
        gameController.selectionController.getBlackoutFactor().appear(3, 1.5);
    }


    public ArrayList<Hex> detectMoveZoneForFarm() {

        return moveZoneDetection.detectMoveZoneForFarm();
    }


    public ArrayList<Hex> detectMoveZone(Hex startHex, int strength) {
        return moveZoneDetection.detectMoveZone(startHex, strength);
    }


    public ArrayList<Hex> detectMoveZone(Hex startHex, int strength, int moveLimit) {
        return moveZoneDetection.detectMoveZone(startHex, strength, moveLimit);
    }


    public void detectAndShowMoveZone(Hex startHex, int strength) {
        detectAndShowMoveZone(startHex, strength, 9001); // move limit is almost infinite
    }


    public void detectAndShowMoveZone(Hex startHex, int strength, int moveLimit) {
        moveZone = moveZoneDetection.detectMoveZone(startHex, strength, moveLimit);
        checkToForceMoveZoneAnims();
        moveZoneFactor.setValues(0, 0);
        moveZoneFactor.appear(3, 1.5);
        gameController.selectionController.getBlackoutFactor().appear(3, 1.5);
    }


    public void checkToForceMoveZoneAnims() {
        if (moveZone.get(0).selectionFactor.get() < 1) {
            for (Hex hex : moveZone) {
                hex.animFactor.setValues(1, 0);
            }
        }
    }


    public void clearMoveZone() {
        for (int i = moveZone.size() - 1; i >= 0; i--)
            moveZone.get(i).inMoveZone = false;
        moveZone.clear();
    }


    public boolean hexHasNeighbourWithColor(Hex hex, int color) {
        Hex neighbour;
        for (int i = 0; i < 6; i++) {
            neighbour = hex.getAdjacentHex(i);
            if (neighbour != null && neighbour.active && neighbour.sameColor(color)) return true;
        }
        return false;
    }


    public void addAnimHex(Hex hex) {
        if (animHexes.contains(hex)) return;

        animHexes.listIterator().add(hex);

        hex.animFactor.setValues(0, 0);
        hex.animFactor.appear(1, 1);
        hex.animStartTime = System.currentTimeMillis();

        gameController.updateCacheOnceAfterSomeTime();
    }


    public Province findProvinceCopy(Province src) {
        Province result;
        for (Hex hex : src.hexList) {
            result = getProvinceByHex(hex);
            if (result == null) continue;
            return result;
        }
        return null;
    }


    public Province getProvinceByHex(Hex hex) {
        for (Province province : provinces) {
            if (province.containsHex(hex)) {
                return province;
            }
        }

        return null;
    }


    public Hex getRandomActivehex() {
        int index = YioGdxGame.random.nextInt(activeHexes.size());
        return activeHexes.get(index);
    }


    public Province getMaxProvinceFromList(ArrayList<Province> list) {
        if (list.size() == 0) return null;
        Province max, temp;
        max = list.get(0);
        for (int k = list.size() - 1; k >= 0; k--) {
            temp = list.get(k);
            if (temp.hexList.size() > max.hexList.size()) max = temp;
        }
        return max;
    }


    public void splitProvince(Hex hex, int color) {
        Province oldProvince = getProvinceByHex(hex);
        if (oldProvince == null) return;
        MoveZoneDetection.unFlagAllHexesInArrayList(oldProvince.hexList);
        ArrayList<Hex> tempList = new ArrayList<Hex>();
        ArrayList<Hex> propagationList = new ArrayList<Hex>();
        ArrayList<Province> provincesAdded = new ArrayList<Province>();
        Hex startHex, tempHex, adjHex;
        hex.flag = true;
        gameController.getPredictableRandom().setSeed(hex.index1 + hex.index2);
        for (int k = 0; k < 6; k++) {
            startHex = hex.getAdjacentHex(k);
            if (!startHex.active || startHex.colorIndex != color || startHex.flag) continue;
            tempList.clear();
            propagationList.clear();
            propagationList.add(startHex);
            startHex.flag = true;
            while (propagationList.size() > 0) {
                tempHex = propagationList.get(0);
                tempList.add(tempHex);
                propagationList.remove(0);
                for (int i = 0; i < 6; i++) {
                    adjHex = tempHex.getAdjacentHex(i);
                    if (adjHex.active && adjHex.sameColor(tempHex) && !adjHex.flag) {
                        propagationList.add(adjHex);
                        adjHex.flag = true;
                    }
                }
            }
            if (tempList.size() >= 2) {
                Province province = new Province(gameController, tempList);
                province.money = 0;
                if (!province.hasCapital()) {
                    province.placeCapitalInRandomPlace(gameController.getPredictableRandom());
                }
                addProvince(province);
                provincesAdded.add(province);
            } else {
                destroyBuildingsOnHex(startHex);
            }
        }
        if (provincesAdded.size() > 0 && !(hex.objectInside == Obj.TOWN)) {
            getMaxProvinceFromList(provincesAdded).money = oldProvince.money;
        }
        provinces.remove(oldProvince);
        diplomacyManager.updateEntityAliveStatus(color);
    }


    public void checkToUniteProvinces(Hex hex) {
        ArrayList<Province> adjacentProvinces = new ArrayList<Province>();
        Province p;
        for (int i = 0; i < 6; i++) {
            p = getProvinceByHex(hex.getAdjacentHex(i));
            if (p != null && hex.sameColor(p) && !adjacentProvinces.contains(p)) adjacentProvinces.add(p);
        }
        if (adjacentProvinces.size() >= 2) {
            int sum = 0;
            Hex capital = getMaxProvinceFromList(adjacentProvinces).getCapital();
            ArrayList<Hex> hexArrayList = new ArrayList<Hex>();
//            YioGdxGame.say("uniting provinces: " + adjacentProvinces.size());
            for (Province province : adjacentProvinces) {
                sum += province.money;
                hexArrayList.addAll(province.hexList);
                provinces.remove(province);
            }
            Province unitedProvince = new Province(gameController, hexArrayList);
            unitedProvince.money = sum;
            unitedProvince.setCapital(capital);
            addProvince(unitedProvince);
        }
    }


    public void joinHexToAdjacentProvince(Hex hex) {
        Province p;
        for (int i = 0; i < 6; i++) {
            p = getProvinceByHex(hex.getAdjacentHex(i));
            if (p != null && hex.sameColor(p)) {
                p.addHex(hex);
                Hex h;
                for (int j = 0; j < 6; j++) {
                    h = adjacentHex(hex, j);
                    if (h.active && h.sameColor(hex) && getProvinceByHex(h) == null) p.addHex(h);
                }
                return;
            }
        }
    }


    public void setLevelSize(int levelSize) {
        this.levelSize = levelSize;
    }


    public void updatePointByHexIndexes(PointYio pointYio, int index1, int index2) {
        pointYio.x = fieldPos.x + hexStep2 * index2 * sin60;
        pointYio.y = fieldPos.y + hexStep1 * index1 + hexStep2 * index2 * cos60;
    }


    public void setHexColor(Hex hex, int color) {
        cleanOutHex(hex);
        int oldColor = hex.colorIndex;
        hex.setColorIndex(color);
        splitProvince(hex, oldColor);
        checkToUniteProvinces(hex);
        joinHexToAdjacentProvince(hex);
        ListIterator animIterator = animHexes.listIterator();
        for (int i = 0; i < 6; i++) {
            Hex h = hex.getAdjacentHex(i);
            if (h != null && h.active && h.sameColor(hex)) {
                if (!animHexes.contains(h)) animIterator.add(h);
                if (!h.changingColor) h.animFactor.setValues(1, 0);
            }
        }
        hex.changingColor = true;
        if (!animHexes.contains(hex)) animIterator.add(hex);
        hex.animFactor.setValues(0, 0);
        hex.animFactor.appear(1, 1);
        if (!gameController.isPlayerTurn()) forceAnimEndInHex(hex);
    }


    public void updateFocusedHex() {
        updateFocusedHex(gameController.touchPoint.x, gameController.touchPoint.y);
    }


    public void updateFocusedHex(float screenX, float screenY) {
        OrthographicCamera orthoCam = gameController.cameraController.orthoCam;
        SelectionController selectionController = gameController.selectionController;

        selectionController.selectX = (screenX - 0.5f * GraphicsYio.width) * orthoCam.zoom + orthoCam.position.x;
        selectionController.selectY = (screenY - 0.5f * GraphicsYio.height) * orthoCam.zoom + orthoCam.position.y;

        GameView gameView = gameController.getYioGdxGame().gameView;
        float x = selectionController.selectX + gameView.hexViewSize;
        float y = selectionController.selectY + gameView.hexViewSize;

        focusedHex = getHexByPos(x, y);

    }


}