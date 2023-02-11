package org.example.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StackoverflowApiResponseDto<T> {
    @SerializedName(value = "items")
    private List<T> items;
    @SerializedName(value = "backoff")
    private Integer backoff;
    @SerializedName(value = "has_more")
    private boolean hasMore;
    @SerializedName(value = "quota_remaining")
    private Long quotaRemaining;
}
