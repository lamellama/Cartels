package yio.tro.antiyoy.menu.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;

import yio.tro.antiyoy.menu.ButtonYio;
import yio.tro.antiyoy.menu.MenuControllerYio;
import yio.tro.antiyoy.menu.behaviors.Reaction;
import yio.tro.antiyoy.stuff.Fonts;

public class SceneExceptionReport extends AbstractScene{


    public SceneExceptionReport(MenuControllerYio menuControllerYio) {
        super(menuControllerYio);
    }


    public void create(Exception exception) {
        menuControllerYio.beginMenuCreation();
        menuControllerYio.getYioGdxGame().setGamePaused(true);

        int lineWidth = 44;
        int lineNumber = 25;
        ArrayList<String> text = new ArrayList<String>();
        String title = "Error : " + exception.toString();
        if (title.length() > lineWidth) title = title.substring(0, lineWidth);
        text.add(title);
        String temp;
        int start, end;
        boolean go;
        for (int i = 0; i < exception.getStackTrace().length; i++) {
            temp = exception.getStackTrace()[i].toString();
            start = 0;
            go = true;
            while (go) {
                end = start + lineWidth;
                if (end > temp.length() - 1) {
                    go = false;
                    end = temp.length() - 1;
                }
                try {
                    text.add(temp.substring(start, end));
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                start = end + 1;
                if (text.size() > lineNumber) go = false;
            }
        }

        // generate special font for this purpose
        FileHandle fontFile = Gdx.files.internal("font.otf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        int FONT_SIZE = (int) (0.021 * Gdx.graphics.getHeight());
        parameter.size = FONT_SIZE;
        parameter.characters = Fonts.getAllCharacters();
        parameter.flip = true;
        BitmapFont font = generator.generateFont(parameter);

        ButtonYio textPanel = buttonFactory.getButton(generateRectangle(0.1, 0.2, 0.8, 0.7), 6731267, null);
        if (textPanel.notRendered()) {
            textPanel.addManyLines(text);
            for (int i = 0; i < (lineNumber - text.size()); i++) textPanel.addTextLine(" ");
            menuControllerYio.getButtonRenderer().renderButton(textPanel, font, FONT_SIZE);
        }
        textPanel.setTouchable(false);

        ButtonYio okButton = buttonFactory.getButton(generateRectangle(0.1, 0.1, 0.8, 0.1), 73612321, "Ok");
        okButton.setReaction(Reaction.rbMainMenu);

        menuControllerYio.endMenuCreation();
    }


    @Override
    public void create() {

    }
}