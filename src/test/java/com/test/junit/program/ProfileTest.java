package com.test.junit.program;

import com.test.junit.program.PercentileQuestion;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestExecutionListeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    private Profile profile;
    private BooleanQuestion question;
    private Criteria criteria;

    private Question questionReimbursesTuition;
    private Answer answerReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;

    private Question questionIsThereRelocation;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNoRelocation;

    private Question questionOnsiteDaycare;
    private Answer answerNoOnsiteDaycare;
    private Answer answerHasOnsiteDaycare;

    @Before
    public void init() {
        profile = new Profile("Bull Hockey, Inc.");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
    }

    @Before
    public void createQuestionsAndAnswers() {
        questionIsThereRelocation =
                new BooleanQuestion(1, "Relocation package?");
        answerThereIsRelocation =
                new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNoRelocation =
                new Answer(questionIsThereRelocation, Bool.FALSE);

        questionReimbursesTuition =
                new BooleanQuestion(1, "Reimburses tuition?");
        answerReimbursesTuition =
                new Answer(questionReimbursesTuition, Bool.TRUE);
        answerDoesNotReimburseTuition =
                new Answer(questionReimbursesTuition, Bool.FALSE);

        questionOnsiteDaycare =
                new BooleanQuestion(1, "Onsite daycare?");
        answerHasOnsiteDaycare =
                new Answer(questionOnsiteDaycare, Bool.TRUE);
        answerNoOnsiteDaycare =
                new Answer(questionOnsiteDaycare, Bool.FALSE);
    }


    @Test
    public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
        /**
         * 코드 수정전
        Answer profileAnswer = new Answer(question, Bool.FALSE);
        profile.add(profileAnswer);
        Answer criteriaAnswer = new Answer(question, Bool.TRUE);
        Criterion criterion = new Criterion(criteriaAnswer, Weight.MustMatch);
        criteria.add(criterion);

        boolean matches = profile.matches(criteria);

        assertFalse(matches);
        */
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));
        boolean matches = profile.getMatchSet(criteria).matches();
        assertFalse(matches);

    }

    @Test
    public void matchAnswersTrueForAnyDontCareCriteria() {
        /**
         * 코드 수정전
        Answer profileAnswer = new Answer(question, Bool.FALSE);
        profile.add(profileAnswer);
        Answer criteriaAnswer = new Answer(question, Bool.TRUE);
        Criterion criterion = new Criterion(criteriaAnswer, Weight.DontCare);
        criteria.add(criterion);

        boolean matches = profile.matches(criteria);

        assertTrue(matches);
         */
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));
        boolean matches = profile.getMatchSet(criteria).matches();
        assertFalse(matches);
    }

    @Test
    public void matches() {
        Profile profile = new Profile("Bull Hockey, Inc. ");
        Question question = new BooleanQuestion(1, "Got milk?");

        //must-match 항목이 맞지 않으면 false
        profile.add(new Answer(question, Bool.FALSE));
        Criteria criteria = new Criteria();
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));
        assertFalse(profile.getMatchSet(criteria).matches());

        // don't care 항목에 대해서는 true
        profile.add(new Answer(question, Bool.FALSE));
        criteria = new Criteria();
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));
        assertTrue(profile.getMatchSet(criteria).matches());
    }

    int[] ids(Collection<Answer> answers) {
        return answers.stream().mapToInt(a -> a.getQuestion().getId()).toArray();
    }

    @Test
    public void findsAnswersBasedOnPredicate() {
        profile.add(new Answer(new BooleanQuestion(1, "1"), Bool.FALSE));
        profile.add(new Answer(new PercentileQuestion(2, "2", new String[]{}), 0));
        profile.add(new Answer(new PercentileQuestion(3, "3", new String[]{}), 0));

        List<Answer> answers = profile.find(a -> a.getQuestion().getClass() == PercentileQuestion.class);

        assertThat(ids(answers)).isEqualTo(new int[] {2, 3});

        List<Answer> answersComplement = profile.find(a -> a.getQuestion().getClass() != PercentileQuestion.class);

        List<Answer> allAnswers = new ArrayList<>();
        allAnswers.addAll(answersComplement);
        allAnswers.addAll(answers);

        assertThat(ids(allAnswers)).isEqualTo(new int[] {1,2,3});
    }

    @Test
    public void findAnswers() {
        int dataSize = 5000;
        for (int i = 0; i < dataSize; i++) {
            profile.add(new Answer(new BooleanQuestion(i, String.valueOf(i)), Bool.FALSE));
        }
        profile.add(new Answer(new PercentileQuestion(dataSize, String.valueOf(dataSize), new String[] {}), 0));
        int numberOfTimes = 1000;
        long elapsedMs = run(numberOfTimes, () -> profile.find(a -> a.getQuestion().getClass() == PercentileQuestion.class));

        assertTrue(elapsedMs < 1000);
    }

    private long run(int times, Runnable func) {
        long start = System.nanoTime();
        for (int i = 0; i < times; i++) {
            func.run();
        }
        long stop = System.nanoTime();
        return (stop - start) / 1000000;
    }

    @Test
    public void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));
        boolean matches = profile.getMatchSet(criteria).matches();
        assertTrue(matches);
    }
}