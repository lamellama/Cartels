package yio.tro.antiyoy.gameplay.replays.actions;

import java.util.ArrayList;

import yio.tro.antiyoy.gameplay.FieldController;
import yio.tro.antiyoy.gameplay.GameController;
import yio.tro.antiyoy.gameplay.Hex;

public class RaUnitDiedFromStarvation extends RepAction{

    Hex hex;


    public RaUnitDiedFromStarvation(Hex hex) {
        this.hex = hex;
    }


    @Override
    public void initType() {
        type = UNIT_DIED_FROM_STARVATION;
    }


    @Override
    public String saveInfo() {
        return convertHexToTwoTokens(hex);
    }


    @Override
    public void loadInfo(FieldController fieldController, String source) {
        ArrayList<String> strings = convertSourceStringToList(source);
        hex = getHexByTwoTokens(fieldController, strings.get(0), strings.get(1));
    }


    @Override
    public void perform(GameController gameController) {
        gameController.fieldController.killUnitByStarvation(hex);
    }


    @Override
    public String toString() {
        return "[Unit dies from starvation:" +
                hex +
                "]";
    }
}
