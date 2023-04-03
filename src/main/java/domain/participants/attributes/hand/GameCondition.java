package domain.participants.attributes;

public enum GameCondition {
    HIT,
    BLACKJACK,
    BUST;

    private static final int BLACKJACK_SCORE = 21;

    public static GameCondition find(final int score, final int cardCount) {
        if (score > BLACKJACK_SCORE) {
            return BUST;
        }
        if (score == BLACKJACK_SCORE && cardCount == 2) {
            return BLACKJACK;
        }
        return HIT;
    }
}