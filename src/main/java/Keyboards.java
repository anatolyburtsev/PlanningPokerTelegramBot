import Strings.Buttons;
import Strings.Symbols;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onotole on 12.07.16.
 */
public class Keyboards {

    private ReplyKeyboardMarkup mainPlanningPokerKeyboard;
    private ReplyKeyboardMarkup initialPlanningPokerKeyboard;
    private ReplyKeyboardMarkup whoReadyStartKeyboard;
    private ReplyKeyboardMarkup newTaskFinishKeyboard;
    private ReplyKeyboardMarkup whoNotAnsweredKeyboard;
    private ReplyKeyboardMarkup emptyKeyboard;

    public ReplyKeyboardMarkup getEmptyKeyboard() {
        return emptyKeyboard;
    }

    public ReplyKeyboardMarkup getWhoNotAnsweredKeyboard() {
        return whoNotAnsweredKeyboard;
    }

    public ReplyKeyboardMarkup getInitialPlanningPokerKeyboard() {
        return initialPlanningPokerKeyboard;
    }

    public ReplyKeyboardMarkup getWhoReadyStartKeyboard() {
        return whoReadyStartKeyboard;
    }

    public ReplyKeyboardMarkup getNewTaskFinishKeyboard() {
        return newTaskFinishKeyboard;
    }

    private List<KeyboardRow> keyboardPlanningPoker;

    public Keyboards() {
        initMainPlanningPokerKeyboard();
        initInitialPlanningPokerKeyboard();
        initWhoReadyStartKeyboard();
        initNewTaskFinishKeyboard();
        initWhoNotAnsweredKeyboard();
        initEmptyKeyboard();
    }

    public ReplyKeyboardMarkup getMainPlanningPokerKeyboard() {
        return mainPlanningPokerKeyboard;
    }

    private void initMainPlanningPokerKeyboard() {
        keyboardPlanningPoker = new ArrayList<>();
        KeyboardRow keyboardRow1Line = new KeyboardRow();
        KeyboardRow keyboardRow2Line = new KeyboardRow();
        keyboardRow1Line.add(Symbols.mark_1);
        keyboardRow1Line.add(Symbols.mark_2);
        keyboardRow1Line.add(Symbols.mark_3);
        keyboardRow1Line.add(Symbols.mark_4);
        keyboardRow1Line.add(Symbols.mark_5);
        keyboardRow2Line.add(Symbols.mark_6);
        keyboardRow2Line.add(Symbols.mark_7);
        keyboardRow2Line.add(Symbols.mark_8);
        keyboardRow2Line.add(Symbols.mark_question);
        keyboardRow2Line.add(Symbols.mark_coffee);
        keyboardPlanningPoker.add(keyboardRow1Line);
        keyboardPlanningPoker.add(keyboardRow2Line);

        mainPlanningPokerKeyboard = new ReplyKeyboardMarkup().setKeyboard(keyboardPlanningPoker).setResizeKeyboard(true);
    }

    private void initInitialPlanningPokerKeyboard() {
        keyboardPlanningPoker = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Buttons.initPoker);
        keyboardPlanningPoker.add(keyboardRow);
        initialPlanningPokerKeyboard = new ReplyKeyboardMarkup().setKeyboard(keyboardPlanningPoker).setResizeKeyboard(true);
    }

    private void initWhoReadyStartKeyboard() {
        keyboardPlanningPoker = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Buttons.whoAreReady);
        keyboardRow.add(Buttons.startPoker);
        keyboardPlanningPoker.add(keyboardRow);
        whoReadyStartKeyboard = new ReplyKeyboardMarkup().setKeyboard(keyboardPlanningPoker).setResizeKeyboard(true);
    }

    private void initNewTaskFinishKeyboard() {
        keyboardPlanningPoker = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Buttons.newTask);
        keyboardRow.add(Buttons.finishPoker);
        keyboardPlanningPoker.add(keyboardRow);
        newTaskFinishKeyboard = new ReplyKeyboardMarkup().setKeyboard(keyboardPlanningPoker).setResizeKeyboard(true);
    }

    private void initWhoNotAnsweredKeyboard() {
        keyboardPlanningPoker = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Buttons.whoNotAnswered);
        keyboardPlanningPoker.add(keyboardRow);
        whoNotAnsweredKeyboard = new ReplyKeyboardMarkup().setKeyboard(keyboardPlanningPoker).setResizeKeyboard(true);
    }

    private void initEmptyKeyboard() {
        keyboardPlanningPoker = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(" ");
        keyboardPlanningPoker.add(keyboardRow);
        emptyKeyboard = new ReplyKeyboardMarkup().setKeyboard(keyboardPlanningPoker).setResizeKeyboard(true);
    }

}
