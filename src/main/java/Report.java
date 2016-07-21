import Strings.Phrases;
import Strings.Symbols;

import java.util.*;

import static java.lang.Character.isDigit;

/**
 * Created by onotole on 21.07.16.
 */
public class Report {

    public static String buildReport(HashMap<PokerPlayer, String> marks, String taskName) {
        String result = taskName + ":\n\n";
        int maxMark;
        int marksCount = marks.size();
        List<String> sortedMarks = new ArrayList<>();
        List<PokerPlayer> sortedPlayers = new ArrayList<>();
        Map.Entry<PokerPlayer, String> currentEntry;

        for (int i = 0; i < marksCount; i++) {
            maxMark = findMaxMark(marks);
            currentEntry = selectMaxEntry(marks, maxMark);
            sortedMarks.add(currentEntry.getValue());
            sortedPlayers.add(currentEntry.getKey());
        }

        result += "Среднее: " + calculateMean(sortedMarks) + "\nМедиана: " + calculateMedian(sortedMarks) + "\n\n";

        for (int i = 0; i < sortedMarks.size(); i++) {
            result += sortedMarks.get(i) + " " + sortedPlayers.get(i) + "\n";
        }

        return String.format(Phrases.roundGone, result) ;
    }

    private static int findMaxMark(HashMap<PokerPlayer, String> marks) {
        int currentMark;
        int maxMark = 0;

        for (Map.Entry<PokerPlayer, String> entry : marks.entrySet()) {
            if (entry.getValue().equals(Symbols.mark_question)) {
                currentMark = 0;
            } else
            if (entry.getValue().equals(Symbols.mark_coffee)) {
                currentMark = 3;
            } else
            if (entry.getValue().equals(Symbols.mark_0)){
                currentMark = 5;
            } else {
                currentMark = Integer.parseInt(entry.getValue()) * 10;
            }
            maxMark = Math.max(maxMark, currentMark);
        }

        return maxMark;
    }

    private static Map.Entry<PokerPlayer, String> selectMaxEntry(HashMap<PokerPlayer, String> marks, int maxMark) {
        // stupid way: hashmap -> sortedMarks & sortedPlayers
        Map.Entry<PokerPlayer, String> currentEntry = null;

        if (maxMark >= 10) {
            for (Map.Entry<PokerPlayer, String> entry : marks.entrySet()) {
                if (entry.getValue().equals("" + (maxMark / 10))){
                    currentEntry = entry;
                    marks.remove(entry.getKey());
                    break;
                }
            }
        } else
        if (maxMark == 5) {
            for (Map.Entry<PokerPlayer, String> entry : marks.entrySet()) {
                if (entry.getValue().equals(Symbols.mark_0)) {
                    currentEntry = entry;
                    marks.remove(entry.getKey());
                    break;
                }
            }
        } else
        if (maxMark == 3) {
            for (Map.Entry<PokerPlayer, String> entry : marks.entrySet()) {
                if (entry.getValue().equals(Symbols.mark_coffee)) {
                    currentEntry = entry;
                    marks.remove(entry.getKey());
                    break;
                }
            }
        } else {
            for (Map.Entry<PokerPlayer, String> entry : marks.entrySet()) {
                if (entry.getValue().equals(Symbols.mark_question)){
                    currentEntry = entry;
                    marks.remove(entry.getKey());
                    break;
                }
            }
        }
        return currentEntry;
    }

    private static String calculateMean(List<String> marks) {
        int sum = 0;
        int numbersCount = 0;
        int mark;

        // extract "?", "coffee", etc
        for (String markString: marks) {
            if (isNumber(markString)) {
                mark = Integer.valueOf(markString);
                sum += mark;
                numbersCount++;
            }
        }
        if (numbersCount == 0) return "";
        return String.format("%.2f", (double) sum /numbersCount);
    }

    private static String calculateMedian(List<String> marks) {
        List<Integer> marksInteger = new ArrayList<>();
        for (String mark: marks) {
            if (isNumber(mark))
                marksInteger.add(Integer.valueOf(mark));
        }
        int middle = marksInteger.size()/2;
        if (marksInteger.size() == 0) return "";
        if (marksInteger.size()%2 == 1) {
            return marksInteger.get(middle).toString();
        } else {
            double median = (marksInteger.get(middle-1) + marksInteger.get(middle)) / 2.0;
            return String.format("%.2f", median);
        }

    }

    private static boolean isNumber(String number) {
        for (char ch: number.toCharArray()) {
            if (! isDigit(ch)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
//        PokerPlayer player1 = new PokerPlayer(new User(new JSONObject("{'id':1, 'first_name':'Vasya', 'last_name':'Pupkin', 'username': 'voen'}")), 1L);
//        PokerPlayer player2 = new PokerPlayer(new User(new JSONObject("{'id':2, 'first_name':'Vasya2', 'last_name':'Pupkin2', 'username': 'voen2'}")), 2L);
//        PokerPlayer player3 = new PokerPlayer(new User(new JSONObject("{'id':3, 'first_name':'Vasya3', 'last_name':'Pupkin3', 'username': 'voen3'}")), 3L);
//        String taskName = "task-123";
//        HashMap<PokerPlayer, String> map = new HashMap<>();
//        map.put(player1, "1");
//        map.put(player2, "2");
//        map.put(player3, "5");
//        String result = buildReport(map, taskName);
//        System.out.println(result);

//        System.out.println(calculateMean(Arrays.asList("1", "3", "asf", "18")));
//        System.out.println(calculateMedian(Arrays.asList("1", "2", "a", "5")));

        System.out.println(calculateMedian(Arrays.asList("?", "?", "3")));

    }
}
