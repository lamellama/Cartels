package yio.tro.antiyoy.menu.diplomatic_dialogs;

import java.util.ArrayList;

import yio.tro.antiyoy.menu.MenuControllerYio;
import yio.tro.antiyoy.stuff.Fonts;
import yio.tro.antiyoy.stuff.LanguagesManager;

public class DipMessageDialog extends AbstractDiplomaticDialog {

    String sourceText, title;
    ArrayList<String> lines;


    public DipMessageDialog(MenuControllerYio menuControllerYio) {
        super(menuControllerYio);

        sourceText = null;
        title = null;
        lines = new ArrayList<>();
    }


    @Override
    protected void makeLabels() {
        LanguagesManager instance = LanguagesManager.getInstance();
        float y = (float) (position.height - topOffset);

        addLabel(instance.getString(title), Fonts.gameFont, leftOffset, y);
        y -= titleOffset;

        for (String line : lines) {
            addLabel(line, Fonts.smallerMenuFont, leftOffset, y);
            y -= lineOffset;
        }
    }


    public void setMessage(String title, String messageKey) {
        this.title = title;
        sourceText = LanguagesManager.getInstance().getString(messageKey);
        updateLines();
        updateAll();
    }


    private void updateLines() {
        convertSourceLineToList(sourceText, lines);
    }


    @Override
    protected void onYesButtonPressed() {

    }


    @Override
    protected void onNoButtonPressed() {

    }


    @Override
    public boolean areButtonsEnabled() {
        return false;
    }
}
