package domain.participants.attributes;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BettingConditionTest {

    @DisplayName("배팅 금액을 받아서 생성시 초기 상태는 게임중이다.")
    @Test
    void create() {
        final BettingCondition bettingCondition = new BettingCondition(BettingAmount.of(1000));
        Assertions.assertThat(bettingCondition)
            .extracting("gameResult")
            .isEqualTo(GameResult.PLAYING);
    }

    @DisplayName("게임 결과를 업데이트 한다.")
    @Test
    void update() {
        final BettingCondition bettingCondition = new BettingCondition(BettingAmount.of(1000));
        bettingCondition.updateGameResult(GameResult.DRAW);
        Assertions.assertThat(bettingCondition)
            .extracting("gameResult")
            .isEqualTo(GameResult.DRAW);
    }

    @DisplayName("승, 무, 패가 아닌 결과로 업데이트시 오류를 던진다.")
    @Test
    void canNotUpdateToPlaying() {
        final BettingCondition bettingCondition = new BettingCondition(BettingAmount.of(1000));
        Assertions.assertThatThrownBy(() -> bettingCondition.updateGameResult(GameResult.PLAYING))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("게임 결과를 두번 업데이트시 오류를 던진다.")
    @Test
    void canNotUpdateTwice() {
        final BettingCondition bettingCondition = new BettingCondition(BettingAmount.of(1000));
        bettingCondition.updateGameResult(GameResult.DRAW);
        Assertions.assertThatThrownBy(() -> bettingCondition.updateGameResult(GameResult.WIN))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("업데이트한 게임 결과에 따른 수익을 반환한다.")
    @ParameterizedTest(name = "게임결과가 {0}일 때 수익은 {1}이다.")
    @CsvSource(value = {
        "WIN:1000",
        "DRAW:0",
        "LOSE:-1000"
    }, delimiter = ':')
    void getBenefit(final GameResult gameResult, final int expectBenefit) {
        final BettingCondition bettingCondition = new BettingCondition(BettingAmount.of(1000));
        bettingCondition.updateGameResult(gameResult);
        Assertions.assertThat(bettingCondition.getBenefit()).isEqualTo(expectBenefit);
    }
}