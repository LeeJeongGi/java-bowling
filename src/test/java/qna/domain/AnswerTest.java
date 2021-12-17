package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import qna.CannotDeleteException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @ParameterizedTest
    @DisplayName("삭제 테스트")
    @MethodSource(value = "createAnswer")
    void deleteTest(Answer answer) {
        assertThat(answer.isDeleted()).isFalse();
        answer.delete();
        assertThat(answer.isDeleted()).isTrue();
    }

    private static Stream<Arguments> createAnswer() {

        return Stream.of(Arguments.of(A1), Arguments.of(A2));
    }

    @Test
    @DisplayName("작성자와 로그인 유저가 다르다면 예외 발생")
    void notEqualExceptionTest() {
        assertThatThrownBy(() ->
                A2.checkOwnerForDelete(UserTest.JAVAJIGI)).isInstanceOf(CannotDeleteException.class);
    }

}
