package yio.tro.antiyoy.gameplay.replays.actions;

import java.util.ArrayList;

import yio.tro.antiyoy.gameplay.FieldController;
import yio.tro.antiyoy.gameplay.GameController;
import yio.tro.antiyoy.gameplay.Hex;
import yio.tro.antiyoy.gameplay.Province;
import yio.tro.antiyoy.gameplay.Unit;

public class RaUnitMoved extends RepAction{

    Hex src, dst;


    public RaUnitMoved(Hex src, Hex dst) {
        this.src = src;
        this.dst = dst;
    }


    @Override
    public void initType() {
        type = UNIT_MOVED;
    }


    @Override
    public String saveInfo() {
        return convertHexToTwoTokens(src) + convertHexToTwoTokens(dst);
    }


    @Override
    public void loadInfo(FieldController fieldController, String source) {
        ArrayList<String> strings = convertSourceStringToList(source);
        src = getHexByTwoTokens(fieldController, strings.get(0), strings.get(1));
        dst = getHexByTwoTokens(fieldController, strings.get(2), strings.get(3));
    }


    @Override
    public void perform(GameController gameController) {
        Unit unit = src.unit;
        Province provinceByHex = gameController.fieldController.getProvinceByHex(src);

        if (!dst.sameColor(src) && !dst.isNeutral() && !dst.canBeAttackedBy(unit)) {
            System.out.println();
            System.out.println("Problem in RaUnitMoved.perform(), forbidden attack");
            System.out.println("src = " + src);
            System.out.println("unit.strength = " + unit.strength);
            System.out.println("dst = " + dst);
            System.out.println("dst.getDefenseNumber() = " + dst.getDefenseNumber());
        }

        if (unit == null) {
            System.out.println();
            System.out.println("Problem in RaUnitMoved.perform(). Unit is null");
            System.out.println("src = " + src);
            System.out.println("dst = " + dst);
            return;
        }

        gameController.moveUnit(unit, dst, provinceByHex);
    }


    @Override
    public String toString() {
        return "[Unit moved from " +
                src + " to " + dst +
                "]";
    }
}
