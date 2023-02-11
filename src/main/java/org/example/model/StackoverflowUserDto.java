package org.example.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class StackoverflowUserDto {
    @SerializedName(value = "user_id")
    private Long userId;
    @SerializedName(value = "display_name")
    private String displayName;
    @SerializedName(value = "location")
    private String location;
    @SerializedName(value = "answer_count")
    private Long answerCount;
    @SerializedName(value = "question_count")
    private Long questionCount;
    @SerializedName(value = "link")
    private String link;
    @SerializedName(value = "profile_image")
    private String profileImage;
}
