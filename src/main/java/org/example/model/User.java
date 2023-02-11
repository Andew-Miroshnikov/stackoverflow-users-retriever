package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private Long userId;
    private String displayName;
    private String location;
    private Long answerCount;
    private Long questionCount;
    private String tags;
    private String link;
    private String profileImage;
}
