package org.example.filter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.example.container.LocationContainer;
import org.example.model.StackoverflowUserDto;

public class UserDtoFilter implements CustomFilter<StackoverflowUserDto> {
    @Override
    public List<StackoverflowUserDto> filterDto(List<StackoverflowUserDto> dto) {
        Predicate<StackoverflowUserDto> predicate = userDto -> userDto.getLocation() != null
                && userDto.getAnswerCount() != null
                && LocationContainer.locationsContainer.stream()
                .anyMatch(country -> userDto.getLocation().contains(country))
                && userDto.getAnswerCount() >= 1;
        return dto.stream().
                filter(predicate)
                .collect(Collectors.toList());
    }
}
