package yio.tro.antiyoy.gameplay.replays.actions;

import java.util.ArrayList;
import java.util.StringTokenizer;

import yio.tro.antiyoy.gameplay.FieldController;
import yio.tro.antiyoy.gameplay.GameController;
import yio.tro.antiyoy.gameplay.Hex;

public abstract class RepAction {

    public static final int UNIT_BUILT = 0;
    public static final int UNIT_MOVED = 1;
    public static final int TOWER_BUILT = 2;
    public static final int FARM_BUILT = 3;
    public static final int PALM_SPAWNED = 4;
    public static final int PINE_SPAWNED = 5;
    public static final int TURN_ENDED = 6;
    public static final int CITY_SPAWNED = 7;
    public static final int UNIT_DIED_FROM_STARVATION = 8;

    public int type;


    public abstract void initType();


    public abstract String saveInfo();


    public abstract void loadInfo(FieldController fieldController, String source);


    public abstract void perform(GameController gameController);


    protected ArrayList<String> convertSourceStringToList(String source) {
        ArrayList<String> list = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(source, " ");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            list.add(token);
        }

        return list;
    }


    protected Hex getHexByTwoTokens(FieldController fieldController, String one, String two) {
        int index1 = Integer.valueOf(one);
        int index2 = Integer.valueOf(two);

        return fieldController.field[index1][index2];
    }


    protected String convertHexToTwoTokens(Hex hex) {
        return hex.index1 + " " + hex.index2 + " ";
    }
}
