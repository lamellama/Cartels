package yio.tro.antiyoy.gameplay;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import yio.tro.antiyoy.YioGdxGame;
import yio.tro.antiyoy.gameplay.name_generator.CityNameGenerator;
import yio.tro.antiyoy.gameplay.rules.GameRules;
import yio.tro.antiyoy.stuff.Fonts;

/**
 * Created by ivan on 27.05.2015.
 */
public class Province {

    public static final int DEFAULT_MONEY = 10;
    public int money;
    public ArrayList<Hex> hexList, tempList;
    private GameController gameController;
    public String name;
    public float nameWidth;


    public Province(GameController gameController, ArrayList<Hex> hexList) {
        this.gameController = gameController;
        this.hexList = new ArrayList<>(hexList);
        tempList = new ArrayList<>();
        money = DEFAULT_MONEY;
    }


    void placeCapitalInRandomPlace(Random random) {
        if (GameRules.replayMode) return;

        Hex randomPlace = getFreeHex(random);
        if (randomPlace == null) randomPlace = getAnyHexExceptTowers();
        if (randomPlace == null) randomPlace = getRandomHex();

        gameController.cleanOutHex(randomPlace);
        gameController.addSolidObject(randomPlace, Obj.TOWN);
        gameController.replayManager.onCitySpawned(randomPlace);
        gameController.addAnimHex(randomPlace);
        gameController.updateCacheOnceAfterSomeTime();
        randomPlace.lastColorIndex = randomPlace.colorIndex;
        randomPlace.animFactor.setValues(0, 0);
        randomPlace.animFactor.appear(1, 2);
        updateName();
    }


    boolean hasCapital() {
        for (Hex hex : hexList) {
            if (hex.objectInside == Obj.TOWN) {
                return true;
            }
        }
        return false;
    }


    public Hex getStrongTower() {
        for (Hex hex : hexList) {
            if (hex.objectInside == Obj.STRONG_TOWER) {
                return hex;
            }
        }
        return null;
    }


    public Hex getCapital() {
        for (Hex hex : hexList)
            if (hex.objectInside == Obj.TOWN)
                return hex;
        return hexList.get(0);
    }


    private Hex getRandomHex() {
        return hexList.get(gameController.random.nextInt(hexList.size()));
    }


    private Hex getAnyHexExceptTowers() {
        tempList.clear();
        for (Hex hex : hexList) {
            if (!hex.containsTower()) {
                tempList.add(hex);
            }
        }
        if (tempList.size() == 0) return null;
        return tempList.get(YioGdxGame.random.nextInt(tempList.size()));
    }


    Province getSnapshotCopy() {
        Province copy = new Province(gameController, hexList);
        copy.money = money;
//        copy.capital = capital.getSnapshotCopy();
        return copy;
    }


    private Hex getFreeHex(Random random) {
        tempList.clear();
        for (Hex hex : hexList)
            if (hex.isFree())
                tempList.add(hex);
        if (tempList.size() == 0) return null;
        return tempList.get(random.nextInt(tempList.size()));
    }


    public int getBalance() {
        return getIncome() - getTaxes() + getDotations();
    }


    String getBalanceString() {
        int balance = getBalance();
        if (balance > 0) return "+" + balance;
        return "" + balance;
    }


    public int getIncome() {
        int income = 0;

        for (Hex hex : hexList) {
            income += gameController.ruleset.getHexIncome(hex);
        }

        return income;
    }


    int getTaxes() {
        int taxes = 0;

        for (Hex hex : hexList) {
            taxes += gameController.ruleset.getHexTax(hex);
        }

        return taxes;
    }


    public int getDotations() {
        if (!GameRules.diplomacyEnabled) return 0;

        return gameController.fieldController.diplomacyManager.getProvinceDotations(this);
    }


    public float getIncomeCoefficient() {
        int n = 0;
        int color = getColor();

        for (Province province : gameController.fieldController.provinces) {
            if (province.getColor() != color) continue;

            n++;
        }

        return 1f / (float) n;
    }


    private void clearFromHouses() {
        for (Hex hex : hexList)
            if (hex.objectInside == Obj.TOWN)
                gameController.cleanOutHex(hex);
    }


    public boolean isSelected() {
        if (hexList.size() == 0) return false;
        return hexList.get(0).isSelected();
    }


    public String getName() {
        if (name == null) {
            updateName();
        }
        return name;
    }


    public void updateName() {
        setName(CityNameGenerator.getInstance().generateName(getCapital()));
    }


    public void setName(String name) {
        this.name = name;
        nameWidth = 0.5f * YioGdxGame.getTextWidth(Fonts.microFont, name) + 0.1f * gameController.yioGdxGame.gameView.hexViewSize;
    }


    void setCapital(Hex hex) {
        clearFromHouses();
        gameController.addSolidObject(hex, Obj.TOWN);
        updateName();
    }


    boolean hasSomeoneReadyToMove() {
        for (Hex hex : hexList) {
            if (hex.containsUnit() && hex.unit.isReadyToMove()) return true;
        }
        return false;
    }


    public boolean canAiAffordUnit(int strength) {
        return canAiAffordUnit(strength, 2);
    }


    public boolean canAiAffordUnit(int strength, int turnsToSurvive) {
        if (GameRules.diplomacyEnabled) {
            if (!gameController.fieldController.diplomacyManager.isProvinceAllowedToBuildUnit(this, strength)) {
                return false;
            }
        }

        int newIncome = getBalance() - gameController.ruleset.getUnitTax(strength);
        if (money + turnsToSurvive * newIncome >= 0) return true;
        return false;
    }


    public boolean canBuildUnit(int strength) {
        if (GameRules.replayMode) return true;

        return gameController.ruleset.canBuildUnit(this, strength);
    }


    public boolean hasMoneyForTower() {
        return money >= GameRules.PRICE_TOWER;
    }


    public boolean hasMoneyForFarm() {
        return money >= getCurrentFarmPrice();
    }


    public int getCurrentFarmPrice() {
        return GameRules.PRICE_FARM + getExtraFarmCost();
    }


    public boolean hasMoneyForStrongTower() {
        return money >= GameRules.PRICE_STRONG_TOWER;
    }


    public boolean hasMoneyForTree() {
        return money >= GameRules.PRICE_TREE;
    }


    public int getExtraFarmCost() {
        int c = 0;

        for (Hex hex : hexList) {
            if (hex.objectInside == Obj.FARM) {
                c += 2;
            }
        }

        return c;
    }


    public boolean equals(Province province) {
        for (Hex hex : hexList) {
            if (!province.containsHex(hex)) return false;
        }

        for (Hex hex : province.hexList) {
            if (!containsHex(hex)) return false;
        }

        return true;
    }


    boolean containsHex(Hex hex) {
        return hexList.contains(hex);
    }


    public int getColor() {
        if (hexList.size() == 0) return -1;
        return hexList.get(0).colorIndex;
    }


    void addHex(Hex hex) {
        if (containsHex(hex)) return;
        ListIterator iterator = hexList.listIterator();
        iterator.add(hex);
    }


    void setHexList(ArrayList<Hex> list) {
        hexList = new ArrayList<Hex>(list);
    }


    void close() {
        gameController = null;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Province:");
        for (Hex hex : hexList) {
            builder.append(" ").append(hex);
        }
        builder.append("]");

        return builder.toString();
    }
}
