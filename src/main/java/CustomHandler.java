import Strings.Buttons;
import Strings.Phrases;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by onotole on 10.07.16.
 */
public class CustomHandler extends TelegramLongPollingBot {
    private Keyboards keyboards;
    private UserLists users;
    private boolean isWaitingForNewTask = false;
    private Poker poker;
    private HashMap<PokerPlayer, String> marks;
    private int marksCount = 0;
    private String taskName = "";
    private Boolean isPlayingStageNow = false;
    private String summary = "";

    public CustomHandler() {
        keyboards = new Keyboards();
        users = new UserLists();
        poker = new Poker();
        initMarks();
    }

    public void onUpdateReceived(Update update) {

        if(update.hasMessage()){
            Message message = update.getMessage();

            if(message.hasText()){
                PokerPlayer pokerPlayer = new PokerPlayer(message.getFrom(), message.getChatId());
                // не пускаем лишних
                if (!users.isAllowedUser(pokerPlayer.getUsername())) {
                    writeToUser(pokerPlayer, Phrases.accessDenied, keyboards.getInitialPlanningPokerKeyboard());
                } else

                // стартовое приветствие
                if (message.getText().equals("/start")){
                    writeToUser(pokerPlayer, Phrases.welcomePhrase, keyboards.getInitialPlanningPokerKeyboard());
                } else

                // после окончания покера стираем все данные
                if (message.getText().equals(Buttons.finishPoker)) {
                    writeToAllCurrentUser(String.format(Phrases.finishPhrase, summary), keyboards.getInitialPlanningPokerKeyboard());
                    summary = "";
                    users.init();
                } else

                // инициализируем команду
                if(message.getText().equals(Buttons.initPoker)) {
                    users.addCurrentUser(pokerPlayer);
                    if (users.getMainUser() == null) {
                        users.setMainUser(pokerPlayer);
                        writeToUser(pokerPlayer, Phrases.waitingOthers, keyboards.getWhoReadyStartKeyboard());
                    } else {
                        writeToAllCurrentUser(pokerPlayer + Phrases.attachedToPoker, null);

                        // на случай, если участник подключился в процессе голосования, показываем ему клавиатуру с голосованием. Иначе все будут его ждать
                        if (isPlayingStageNow) {
                            writeToUser(pokerPlayer,
                                    String.format(Phrases.successAttachShowMainUser, users.getMainUser()),
                                    keyboards.getMainPlanningPokerKeyboard());

                            writeToUser(pokerPlayer,
                                    String.format(Phrases.startPokerWithNewTask, taskName),
                                    keyboards.getMainPlanningPokerKeyboard());
                        } else {
                            writeToUser(pokerPlayer,
                                    String.format(Phrases.successAttachShowMainUser, users.getMainUser()),
                                    keyboards.getWhoReadyStartKeyboard());
                        }
                    }
                } else

                // Кто готов?
                if (message.getText().equals(Buttons.whoAreReady)) {
                    String readyUsers = users.getCurrentUsersString();
                    writeToUser(pokerPlayer, readyUsers, keyboards.getWhoReadyStartKeyboard());
                } else

                // Начали покер, всем прилетел список участников и количество людей
                if (message.getText().equals(Buttons.startPoker)) {
                    if (! pokerPlayer.getUsername().equals(users.getMainUser().getUsername())) {
                        writeToUser(pokerPlayer, String.format(Phrases.onlySuperCouldStartPoker, users.getMainUser()),
                                keyboards.getWhoReadyStartKeyboard());
                    } else {
                        writeToAllCurrentUser(String.format(Phrases.startPokerPhrase,
                                users.getCurrentPokerPlayers().size(), users.getCurrentUsersString()),
                                keyboards.getEmptyKeyboard());
                        writeToUser(pokerPlayer, Phrases.newTaskOrFinish, keyboards.getNewTaskFinishKeyboard());
                        initMarks();
                    }
                } else


                // начинаем покер по новому таску
                if (message.getText().equals(Buttons.newTask)) {
                    isWaitingForNewTask = true;
                    writeToUser(pokerPlayer, Phrases.writeNewTaskAndStart, keyboards.getEmptyKeyboard());
                } else

                if (isWaitingForNewTask) {
                    initMarks();
                    isWaitingForNewTask = false;
                    taskName = message.getText();
                    writeToAllCurrentUser(String.format(Phrases.startPokerWithNewTask, taskName),
                            keyboards.getMainPlanningPokerKeyboard());
                    isPlayingStageNow = true;
                } else

                // непосредственно игра в покер (оценка тикетов)
                if (poker.isPokerKey(message.getText())) {
                    marks.put(pokerPlayer, message.getText());
                    marksCount++;
                    if (marksCount < users.getCurrentPokerPlayers().size()) {
                        writeToUser(pokerPlayer, Phrases.gotMark, keyboards.getWhoNotAnsweredKeyboard());
                    } else { // все ответили
                        String thisRoundReport = Report.buildReport(marks, taskName);
                        summary += thisRoundReport + "\n\n";
                        writeToAllCurrentUser(thisRoundReport, keyboards.getEmptyKeyboard());
                        writeToUser(users.getMainUser(), Phrases.newTaskOrFinish, keyboards.getNewTaskFinishKeyboard());
                        isPlayingStageNow = false;
                    }
                } else

                // показываем кто еще не ответил
                if (message.getText().equals(Buttons.whoNotAnswered)) {
                    Set<PokerPlayer> notVotedYetPlayers = new HashSet<>();
                    for (PokerPlayer player: users.getCurrentPokerPlayers()) {
                        if (! marks.containsKey(player)) {
                            notVotedYetPlayers.add(player);
                        }
                    }

                    String result = "";
                    for (PokerPlayer pokerPlayer1 : notVotedYetPlayers) {
                        result = result + pokerPlayer1 + "\n";
                    }
                    writeToUser(pokerPlayer, result, keyboards.getWhoNotAnsweredKeyboard());

                // default
                } else {
                    if (isPlayingStageNow) {
                        writeToUser(pokerPlayer, Phrases.incorrectMark, keyboards.getMainPlanningPokerKeyboard());
                    } else {
                        writeToUser(pokerPlayer, Phrases.welcomePhrase, keyboards.getInitialPlanningPokerKeyboard());
                    }
                }
            }
        }
    }

    public String getBotUsername() {
        return BotConfig.USERNAMEMYPROJECT;
    }

    public String getBotToken() {
        return BotConfig.TOKENMYPROJECT;
    }


    private void writeToAllCurrentUser(String text, ReplyKeyboardMarkup keyboardMarkup) {
        for (PokerPlayer pokerPlayer : users.getCurrentPokerPlayers()) {
            SendMessage sendMessageRequest = new SendMessage();
            sendMessageRequest.setChatId(pokerPlayer.getChatId());
            sendMessageRequest.setText(text);
            sendMessageRequest.setReplyMarkup(keyboardMarkup);
            try {
                sendMessage(sendMessageRequest);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToUser(PokerPlayer pokerPlayer, String text, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(pokerPlayer.getChatId());
        sendMessageRequest.setText(text);
        if (keyboardMarkup != null)
            sendMessageRequest.setReplyMarkup(keyboardMarkup);
        try {
            sendMessage(sendMessageRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void initMarks() {
        marksCount = 0;
        marks = new HashMap<>();
    }

}
