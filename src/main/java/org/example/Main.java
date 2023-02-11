package org.example;

import org.example.container.LocationContainer;
import org.example.container.TagsContainer;
import org.example.mapper.UserMapper;
import org.example.model.StackoverflowTagDto;
import org.example.model.StackoverflowUserDto;
import org.example.service.StackoverflowService;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;
import org.example.filter.CustomFilter;
import org.example.filter.TagDtoFilter;
import org.example.filter.UserDtoFilter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class Main {
    public static final String BASE_URL = "https://api.stackexchange.com/";
    public static void main(String[] args) {

        /*
        That is ALFA-Test version of API, so you can use only first 25 pages
        if you like it I'm register this api and delivered the full version
         */

        // Use the following containers as criteria params
        LocationContainer.locationsContainer.addAll(List.of("Romania", "Moldova"));
        TagsContainer.tagsContainer.addAll(List.of("java", ".net", "docker", "c#"));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        StackoverflowService stackoverflowService = retrofit.create(StackoverflowService.class);
        CustomFilter<StackoverflowUserDto> userValidator = new UserDtoFilter();
        CustomFilter<StackoverflowTagDto> tagValidator = new TagDtoFilter();
        UserMapper userMapper = new UserMapper();
        UserService userService = new UserServiceImpl(stackoverflowService, userValidator,
                tagValidator, userMapper);
        System.out.println(userService.getFilteredUsers());
    }
}