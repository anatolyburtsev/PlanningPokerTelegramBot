import Strings.Symbols;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by onotole on 14.07.16.
 * допустимые символы
 */
public class Poker {
        private static Set<String> pokerKeys;

    public Poker() {
        pokerKeys = new HashSet<>();
        pokerKeys.add(Symbols.mark_0);
        pokerKeys.add(Symbols.mark_1);
        pokerKeys.add(Symbols.mark_2);
        pokerKeys.add(Symbols.mark_3);
        pokerKeys.add(Symbols.mark_4);
        pokerKeys.add(Symbols.mark_5);
        pokerKeys.add(Symbols.mark_6);
        pokerKeys.add(Symbols.mark_7);
        pokerKeys.add(Symbols.mark_8);
        pokerKeys.add(Symbols.mark_9);
        pokerKeys.add(Symbols.mark_10);
        pokerKeys.add(Symbols.mark_question);
        pokerKeys.add(Symbols.mark_coffee);
    }

    public boolean isPokerKey(String input) {
        return pokerKeys.contains(input);
    }


}
