package com.test.junit.domain;

class BooleanAnswer {
    private int questionId;
    private boolean value;

    BooleanAnswer(int questionId, boolean value) {
        this.questionId = questionId;
        this.value = value;
    }

    boolean getValue() {
        return value;
    }

    int getQuestionId() {
        return questionId;
    }
}
