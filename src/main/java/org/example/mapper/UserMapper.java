package org.example.mapper;

import org.example.model.StackoverflowTagDto;
import org.example.model.StackoverflowUserDto;
import org.example.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public User fromDto(StackoverflowUserDto userDto, List<StackoverflowTagDto> tagDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setDisplayName(userDto.getDisplayName());
        user.setLocation(userDto.getLocation());
        user.setAnswerCount(userDto.getAnswerCount());
        user.setQuestionCount(userDto.getQuestionCount());
        String tags = tagDto.stream()
                .map(StackoverflowTagDto::getName)
                .collect(Collectors.joining(","));
        user.setTags(tags);
        user.setLink(userDto.getLink());
        user.setProfileImage(userDto.getProfileImage());
        return user;
    }
}
