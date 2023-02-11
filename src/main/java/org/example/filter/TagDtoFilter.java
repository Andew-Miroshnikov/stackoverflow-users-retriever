package org.example.filter;

import java.util.Collections;
import java.util.List;
import org.example.container.TagsContainer;
import org.example.model.StackoverflowTagDto;

public class TagDtoFilter implements CustomFilter<StackoverflowTagDto> {
    @Override
    public List<StackoverflowTagDto> filterDto(List<StackoverflowTagDto> dto) {
        return dto.stream()
                .anyMatch(tag -> TagsContainer.tagsContainer.contains(tag.getName())) ? dto : Collections.emptyList();
    }


}
