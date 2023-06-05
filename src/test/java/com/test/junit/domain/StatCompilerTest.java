package com.test.junit.domain;

import com.test.junit.controller.QuestionController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StatCompilerTest {
    @Mock private QuestionController controller;
    @InjectMocks private StatCompiler stats;

    @Before
    public void initialize() {
        stats = new StatCompiler();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void questionTextDoesStuff() {
        when(controller.find(1)).thenReturn(new BooleanQuestion("text1"));
        when(controller.find(2)).thenReturn(new BooleanQuestion("text2"));
        List<BooleanAnswer> answers = new ArrayList<>();
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(2, true));

        Map<Integer, String > questionText = stats.questionText(answers);

        Map<Integer, String> expected = new HashMap<>();
        expected.put(1, "text1");
        expected.put(2, "text2");
        assertThat(questionText).isEqualTo(expected);
    }

    @Test
    public void responsesByQuestionAnswersCountsByQuestionText() {
        // mock을 사용하기 전
        StatCompiler stats = new StatCompiler();
        List<BooleanAnswer> answers = new ArrayList<>();
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(1, false));
        answers.add(new BooleanAnswer(2, true));
        answers.add(new BooleanAnswer(2, true));
        Map<Integer, String> questions = new HashMap<>();
        questions.put(1, "Tuituion reimbursement?");
        questions.put(2, "Relocation package?");

        Map<String, Map<Boolean, AtomicInteger>> responses = stats.responsesByQuestion(answers, questions);
        assertThat(responses.get("Tuituion reimbursement?").get(Boolean.TRUE).get()).isEqualTo(3);
        assertThat(responses.get("Tuituion reimbursement?").get(Boolean.FALSE).get()).isEqualTo(1);
        assertThat(responses.get("Relocation package?").get(Boolean.TRUE).get()).isEqualTo(2);
        assertThat(responses.get("Relocation package?").get(Boolean.FALSE).get()).isEqualTo(0);
    }

}