package com.poll.model;

import javax.persistence.*;

@Entity
public class QuestionAnswer extends AbstractTimestampEntity {
    private String text;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
}
