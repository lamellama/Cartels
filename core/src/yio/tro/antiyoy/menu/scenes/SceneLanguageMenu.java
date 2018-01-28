package yio.tro.antiyoy.menu.scenes;

import java.util.ArrayList;

import yio.tro.antiyoy.CustomLanguageLoader;
import yio.tro.antiyoy.Settings;
import yio.tro.antiyoy.menu.Animation;
import yio.tro.antiyoy.menu.ButtonYio;
import yio.tro.antiyoy.menu.MenuControllerYio;
import yio.tro.antiyoy.menu.behaviors.Reaction;
import yio.tro.antiyoy.stuff.RectangleYio;

public class SceneLanguageMenu extends AbstractScene{


    private ArrayList<LangItem> langItems;


    public SceneLanguageMenu(MenuControllerYio menuControllerYio) {
        super(menuControllerYio);

        createLanguagesList();
    }


    private void createLanguagesList() {
        langItems = new ArrayList<>();

        addItem(332, "en_UK", "English");
        addItem(333, "ru_RU", "Russian");
        addItem(334, "uk_UA", "Ukrainian");
        addItem(335, "de_DE", "German");
        addItem(336, "cs_CZ", "Czech");
        addItem(337, "pl_PL", "Polish");
        addItem(338, "it_IT", "Italian");
        addItem(340, "fr_FR", "French");
        addItem(341, "es_MX", "Spanish");
        addItem(342, "sk_SK", "Slovak");
        addItem(343, "cn_CN", "Chinese");
        addItem(344, "tr_TR", "Turkish");
        addItem(345, "bg_BG", "Bulgarian");
    }


    @Override
    public void create() {
        menuControllerYio.beginMenuCreation();

        menuControllerYio.getYioGdxGame().beginBackgroundChange(1, false, true);

        menuControllerYio.spawnBackButton(330, Reaction.rbMoreSettings);

        double buttonHeight = 0.06;
        int langNumber = langItems.size();
        RectangleYio base = new RectangleYio(0.1, 0.5 * (0.9 - buttonHeight * langNumber), 0.8, buttonHeight * langNumber);
        double y = base.y + base.height;

        ButtonYio basePanel = buttonFactory.getButton(generateRectangle(base.x, base.y, base.width, base.height), 331, " ");
        basePanel.setTouchable(false);
        basePanel.onlyShadow = true;
        basePanel.setAnimation(Animation.FROM_CENTER);

        for (LangItem langItem : langItems) {
            y -= buttonHeight;
            ButtonYio langButton = buttonFactory.getButton(generateRectangle(base.x, y, base.width, buttonHeight), langItem.id, langItem.name);
            langButton.setReaction(Reaction.rbSetLanguage);
            langButton.setShadow(false);
            langButton.setAnimation(Animation.FROM_CENTER);
        }

        menuControllerYio.endMenuCreation();
    }


    public void onLanguageButtonPressed(ButtonYio buttonYio) {
        buttonYio.menuControllerYio.clear();

        for (LangItem langItem : langItems) {
            if (buttonYio.id == langItem.id) {
                CustomLanguageLoader.setAndSaveLanguage(langItem.key);
                break;
            }
        }

        Settings.getInstance().loadSettings();
        Scenes.createScenes(menuControllerYio);
        Scenes.sceneMainMenu.create();
    }


    void addItem(int id, String key, String name) {
        LangItem item = new LangItem();
        item.id = id;
        item.key = key;
        item.name = name;

        langItems.add(item);
    }


    class LangItem {
        int id;
        String key;
        String name;
    }
}