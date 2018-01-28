package yio.tro.antiyoy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.ListIterator;

import yio.tro.antiyoy.factor_yio.FactorYio;
import yio.tro.antiyoy.stuff.GraphicsYio;
import yio.tro.antiyoy.stuff.Yio;

public class SplatController {

    private final YioGdxGame yioGdxGame;
    TextureRegion splatTexture;
    FactorYio splatTransparencyFactor;
    ArrayList<Splat> splats;
    long timeToSpawnNextSplat;
    float splatSize;
    int currentSplatIndex;
    long timeToHideSplats;
    boolean needToHideSplats;


    public SplatController(YioGdxGame yioGdxGame) {
        this.yioGdxGame = yioGdxGame;
    }


    void initSplats() {
        splats = new ArrayList<Splat>();
        splatSize = 0.15f * Gdx.graphics.getWidth();
        ListIterator iterator = splats.listIterator();
        for (int i = 0; i < 100; i++) {
            float sx, sy, sr;
            sx = YioGdxGame.random.nextFloat() * GraphicsYio.width;
            sr = 0.03f * YioGdxGame.random.nextFloat() * GraphicsYio.height + 0.02f * GraphicsYio.height;
            sy = YioGdxGame.random.nextFloat() * GraphicsYio.height;
            float dx, dy;
            dx = 0.02f * splatSize * YioGdxGame.random.nextFloat() - 0.01f * splatSize;
            dy = 0.01f * splatSize;
            Splat splat = new Splat(null, sx, sy);
            if (YioGdxGame.random.nextDouble() < 0.6 || Yio.distance(GraphicsYio.width / 2, GraphicsYio.height / 2, sx, sy) > 0.6f * GraphicsYio.width)
                splat.y = 2 * GraphicsYio.height; // hide splat
            splat.setSpeed(dx, dy);
            splat.setRadius(sr);
            iterator.add(splat);
        }
    }


    void moveSplats() {
        splatTransparencyFactor.move();

        if (needToHideSplats && System.currentTimeMillis() > timeToHideSplats) {
            needToHideSplats = false;
        }

        if (!yioGdxGame.gameView.coversAllScreen()) {
            if (System.currentTimeMillis() > timeToSpawnNextSplat) {
                timeToSpawnNextSplat = System.currentTimeMillis() + 300 + YioGdxGame.random.nextInt(100);
                float sx, sy, sr;
                sx = YioGdxGame.random.nextFloat() * GraphicsYio.width;
                sr = 0.03f * YioGdxGame.random.nextFloat() * GraphicsYio.height + 0.02f * GraphicsYio.height;
                sy = -sr;
                int c = 0, size = splats.size();
                Splat splat = null;
                while (c < size) {
                    c++;
                    splat = splats.get(currentSplatIndex);
                    currentSplatIndex++;
                    if (currentSplatIndex >= size) currentSplatIndex = 0;
                    if (!splat.isVisible()) {
                        float dx, dy;
                        dx = 0.02f * splatSize * YioGdxGame.random.nextFloat() - 0.01f * splatSize;
                        dy = 0.01f * splatSize;
                        splat.set(sx, sy);
                        splat.setSpeed(dx, dy);
                        splat.setRadius(sr);
                        break;
                    }
                }
            }
            for (Splat splat : splats) {
                splat.move();
            }
        }
    }


    void renderSplats(Color c) {
        if (splatTransparencyFactor.get() == 1) {
            yioGdxGame.batch.setColor(c.r, c.g, c.b, splatTransparencyFactor.get());
            for (Splat splat : splats) {
                yioGdxGame.batch.draw(splatTexture, splat.x - splat.r / 2, splat.y - splat.r / 2, splat.r, splat.r);
            }
        } else if (splatTransparencyFactor.get() > 0) {
            yioGdxGame.batch.setColor(c.r, c.g, c.b, splatTransparencyFactor.get());
            float a, d;
            for (Splat splat : splats) {
                a = (float) Yio.angle(GraphicsYio.width / 2, GraphicsYio.height / 2, splat.x, splat.y);
                d = (float) Yio.distance(GraphicsYio.width / 2, GraphicsYio.height / 2, splat.x, splat.y);
                d = 0.5f * GraphicsYio.height - d;
                d *= 1 - splatTransparencyFactor.get();
                yioGdxGame.batch.draw(splatTexture, splat.x - splat.r / 2 + d * (float) Math.cos(a), splat.y - splat.r / 2 + d * (float) Math.sin(a), splat.r, splat.r);
            }
        }
    }


    void hideSplats() {
        needToHideSplats = true;
        timeToHideSplats = System.currentTimeMillis() + 350;
        splatTransparencyFactor.setDy(0);
        splatTransparencyFactor.destroy(0, 1);
    }


    void revealSplats() {
        needToHideSplats = false;
        splatTransparencyFactor.setDy(0);
        splatTransparencyFactor.appear(0, 0.5);
    }
}