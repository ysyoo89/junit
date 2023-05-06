package com.test.junit.program;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestExecutionListeners;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    private Profile profile;
    private BooleanQuestion question;
    private Criteria criteria;

    @Before
    public void init() {
        profile = new Profile("Bull Hockey, Inc.");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
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
        boolean matches = profile.matches(criteria);
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
        boolean matches = profile.matches(criteria);
        assertFalse(matches);
    }
}