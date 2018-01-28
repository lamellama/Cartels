package yio.tro.antiyoy.gameplay;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import yio.tro.antiyoy.gameplay.rules.GameRules;
import yio.tro.antiyoy.stuff.Yio;

public class MapGeneratorGeneric extends MapGenerator {

    public MapGeneratorGeneric(GameController gameController) {
        super(gameController);
    }


    @Override
    public void generateMap(Random random, Hex[][] field) {
        setValues(random, field);

        beginGeneration();

        createLand();
//        removeSingleHoles();
        addTrees();
        while (!hasGreenProvince()) {
            genericBalance();
        }

        endGeneration();
    }


    // not actually needed right now
    private boolean hasGreenProvince() {
        for (Hex activeHex : gameController.fieldController.activeHexes) {
            if (activeHex.colorIndex == 0 && activeHex.numberOfFriendlyHexesNearby() > 2) return true;
        }

        return false;
    }


    private void genericBalance() {
        // default field
        for (Hex activeHex : gameController.fieldController.activeHexes) {
            activeHex.colorIndex = FieldController.NEUTRAL_LANDS_INDEX;
        }

        for (int i = 0; i < numberOfProvincesByLevelSize(); i++) {
            for (int colorIndex = 0; colorIndex < GameRules.colorNumber; colorIndex++) {
                Hex hex = findGoodPlaceForNewProvince();
                hex.setColorIndex(colorIndex);
                spawnProvince(hex, 2);
            }
        }

        cutProvincesToSmallSizes();
        makeSingleHexesIntoProvinces();
        giveLastPlayersSlightAdvantage();
    }


    private void makeSingleHexesIntoProvinces() {
        for (Hex activeHex : gameController.fieldController.activeHexes) {
            if (activeHex.isNeutral()) continue;
            if (activeHex.numberOfFriendlyHexesNearby() > 0) continue;
            int c = 3;
            for (int i = 0; i < 6; i++) {
                Hex adjHex = activeHex.getAdjacentHex(i);
                if (!adjHex.active || !adjHex.isNeutral()) continue;
                adjHex.colorIndex = activeHex.colorIndex;
                c--;
                if (c == 0) break;
            }
        }
    }


    @Override
    protected void giveLastPlayersSlightAdvantage() {
        switch (gameController.fieldController.levelSize) {
            default:
            case FieldController.SIZE_MEDIUM:
                giveAdvantageToPlayer(GameRules.colorNumber - 1, 0.28); // last
                giveAdvantageToPlayer(GameRules.colorNumber - 2, 0.15);

                if (GameRules.colorNumber >= 5) {
                    giveAdvantageToPlayer(GameRules.colorNumber - 3, 0.07);
                } else { // if color number == 4
                    giveAdvantageToPlayer(1, 0.03);
                    giveAdvantageToPlayer(GameRules.colorNumber - 1, 0.05);
                }

                giveDisadvantageToPlayer(0, 0.17);
                giveDisadvantageToPlayer(1, 0.1);
                break;
            case FieldController.SIZE_BIG:
                giveAdvantageToPlayer(GameRules.colorNumber - 1, 0.35); // last
                giveAdvantageToPlayer(GameRules.colorNumber - 2, 0.2);

                if (GameRules.colorNumber >= 5) {
                    giveAdvantageToPlayer(GameRules.colorNumber - 3, 0.04);
                } else { // if color number == 4
                    giveAdvantageToPlayer(1, 0.03);
                    giveAdvantageToPlayer(GameRules.colorNumber - 1, 0.05);
                }

                giveDisadvantageToPlayer(0, 0.07);
                giveDisadvantageToPlayer(1, 0.17);
                break;
        }
    }


    @Override
    protected void decreaseProvince(ArrayList<Hex> provinceList, double power) {
        int num = (int) (power * provinceList.size());
        for (int i = 0; i < num; i++) {
            Hex hex = findHexToExcludeFromProvince(provinceList);
            provinceList.remove(hex);
            hex.colorIndex = FieldController.NEUTRAL_LANDS_INDEX;
        }
    }


    @Override
    protected void spawnProvince(Hex spawnHex, int startingPotential) {
        spawnHex.genPotential = startingPotential;
        ArrayList<Hex> propagationList = new ArrayList<Hex>();
        propagationList.add(spawnHex);
        while (propagationList.size() > 0) {
            Hex hex = propagationList.get(0);
            propagationList.remove(0);
            if (random.nextInt(startingPotential) > hex.genPotential) continue;
            hex.colorIndex = spawnHex.colorIndex;
            if (hex.genPotential == 0) continue;
            for (int i = 0; i < 6; i++) {
                Hex adjHex = hex.getAdjacentHex(i);
                if (!propagationList.contains(adjHex) && adjHex.active && adjHex.colorIndex == FieldController.NEUTRAL_LANDS_INDEX) {
                    adjHex.genPotential = hex.genPotential - 1;
                    propagationList.add(adjHex);
                }
            }
        }
    }


    @Override
    protected void reduceProvinceSize(ArrayList<Hex> provinceList) {
        int provinceColor = provinceList.get(0).colorIndex;
        while (provinceList.size() > SMALL_PROVINCE_SIZE) {
            Hex hex = findHexToExcludeFromProvince(provinceList);
//            System.out.println("removed: " + hex);
            provinceList.remove(hex);
            hex.colorIndex = FieldController.NEUTRAL_LANDS_INDEX;
        }
    }


    @Override
    protected boolean activateHex(Hex hex, int color) {
        if (hex.active) return false;
        hex.active = true;
        hex.setColorIndex(FieldController.NEUTRAL_LANDS_INDEX);
        ListIterator activeIterator = gameController.fieldController.activeHexes.listIterator();
        activeIterator.add(hex);
        return true;
    }


    double distanceToClosestProvince(Hex hex) {
        double minDistance = -1, currentDistance;

        for (Hex activeHex : gameController.fieldController.activeHexes) {
            if (activeHex.isNeutral()) continue;
            currentDistance = Yio.distance(hex.index1, hex.index2, activeHex.index1, activeHex.index2);
            if (minDistance == -1 || currentDistance < minDistance) {
                minDistance = currentDistance;
            }
        }

        return minDistance;
    }


    Hex findGoodPlaceForNewProvince() {
        if (allHexesAreNeutral()) return getRandomFreeHex();

        double maxDistance = 0, currentDistance;
        Hex bestHex = null;

        for (Hex activeHex : gameController.fieldController.activeHexes) {
            if (!activeHex.isNeutral()) continue;
            currentDistance = distanceToClosestProvince(activeHex);
            if (bestHex == null || currentDistance > maxDistance) {
                bestHex = activeHex;
                maxDistance = currentDistance;
            }
        }

        return bestHex;
    }


    private boolean allHexesAreNeutral() {
        for (Hex activeHex : gameController.fieldController.activeHexes) {
            if (!activeHex.isNeutral()) return false;
        }
        return true;
    }


    protected Hex getRandomFreeHex() {
        return gameController.fieldController.activeHexes.get(random.nextInt(gameController.fieldController.activeHexes.size()));
    }


    protected int numberOfProvincesByLevelSize() {
        switch (gameController.fieldController.levelSize) {
            default:
            case FieldController.SIZE_SMALL:
                return 1;
            case FieldController.SIZE_MEDIUM:
                return 2;
            case FieldController.SIZE_BIG:
                return 2;
        }
    }
}
