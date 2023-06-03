package com.test.junit.controller;

import com.test.junit.domain.Question;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class QuestionControllerTest {

    private QuestionController controller;

    @Before
    public void create() {
        controller = new QuestionController();
        controller.deleteAll();
    }

    @After
    public void cleanup() {
        controller.deleteAll();
    }
    @Test
    public void findsPersistedQuestionById() {
        int id = controller.addBooleanQuestion("question text");

        Question question = controller.find(id);

        assertThat(question.getText()).isEqualTo("question text");
    }
    @Test
    void questionAnswersDateAdded() {
        Instant now = new Date().toInstant();
        controller.setClock(Clock.fixed(now, ZoneId.of("America/Denver")));
        int id = controller.addBooleanQuestion("text");

        Question question = controller.find(id);

        assertThat(question.getCreateTimestamp()).isEqualTo(now);
    }

    @Test
    public void answersMultiplePersistedQuestions() {
        controller.addBooleanQuestion("q1");
        controller.addBooleanQuestion("q2");
        controller.addPercentileQuestion("q3", new String[] {"a1", "a2"});

        List<Question> questions = controller.getAll();

        assertThat(questions.stream()
                .map(Question::getText)
                .collect(Collectors.toList()))
            .isEqualTo(Arrays.asList("q1", "q2", "q3"));
    }

    @Test
    public void findsMatchingEntries() {
        controller.addBooleanQuestion("alpha 1");
        controller.addBooleanQuestion("alpha 2");
        controller.addBooleanQuestion("beta 1");

        List<Question> questions = controller.findWithMatchingText("alpha");

        assertThat(questions.stream()
                    .map(Question::getText)
                    .collect(Collectors.toList())
                )
                .isEqualTo(Arrays.asList("alpha 1", "alpha 2"));
    }
}