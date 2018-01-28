package yio.tro.antiyoy.gameplay.fog_of_war;

import java.util.ArrayList;

import yio.tro.antiyoy.stuff.object_pool.ReusableYio;

public class FogSlice implements ReusableYio{


    public FogPoint topPoint, bottomPoint;
    public ArrayList<FogPoint> points;


    public FogSlice() {
        points = new ArrayList<>();
    }


    @Override
    public void reset() {
        topPoint = null;
        bottomPoint = null;
        points.clear();
    }


    public void setBottomPoint(FogPoint bottomPoint) {
        this.bottomPoint = bottomPoint;
    }


    public void setTopPoint(FogPoint topPoint) {
        this.topPoint = topPoint;
    }
}
