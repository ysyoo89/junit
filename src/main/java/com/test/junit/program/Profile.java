package com.test.junit.program;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
public class Profile {
    private AnswerCollection answers = new AnswerCollection();
    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public MatchSet getMatchSet(Criteria criteria) {
        return new MatchSet(answers, criteria);
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    @Override
    public String toString() {
        return name;
    }

}
