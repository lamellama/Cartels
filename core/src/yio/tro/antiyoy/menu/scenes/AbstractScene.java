package yio.tro.antiyoy.menu.scenes;

import yio.tro.antiyoy.menu.ButtonFactory;
import yio.tro.antiyoy.menu.MenuControllerYio;
import yio.tro.antiyoy.stuff.LanguagesManager;
import yio.tro.antiyoy.stuff.RectangleYio;

public abstract class AbstractScene {


    protected final MenuControllerYio menuControllerYio;
    protected ButtonFactory buttonFactory;


    public AbstractScene(MenuControllerYio menuControllerYio) {
        this.menuControllerYio = menuControllerYio;

        buttonFactory = menuControllerYio.getButtonFactory();
    }


    public abstract void create();


    protected void destroyByIndex(int startIndex, int endIndex) {
        for (int index = startIndex; index <= endIndex; index++) {
            menuControllerYio.destroyButton(index);
        }
    }


    public RectangleYio generateRectangle(double x, double y, double width, double height) {
        return menuControllerYio.generateRectangle(x, y, width, height);
    }


    public RectangleYio generateSquare(double x, double y, double size) {
        return menuControllerYio.generateSquare(x, y, size);
    }


    public String getString(String key) {
        return LanguagesManager.getInstance().getString(key);
    }

}
