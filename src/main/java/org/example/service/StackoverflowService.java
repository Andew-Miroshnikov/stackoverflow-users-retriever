package org.example.service;

import org.example.model.StackoverflowApiResponseDto;
import org.example.model.StackoverflowTagDto;
import org.example.model.StackoverflowUserDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StackoverflowService {
    @GET("/2.3/users?pagesize=100&order=asc"
            + "&min=223&sort=reputation&site=stackoverflow&filter=!LnNkvq16GJbDtE35hbYQGk")
    Call<StackoverflowApiResponseDto<StackoverflowUserDto>> getUsers(@Query("page") int page);

    @GET("/2.3/users/{ids}/tags?pagesize=100&order=desc"
            + "&sort=name&site=stackoverflow&filter=!-.DiW2eRF3oO")
    Call<StackoverflowApiResponseDto<StackoverflowTagDto>> getTagsByUserId(@Path("ids") Long id,
                                                                           @Query("page") int page);
}
