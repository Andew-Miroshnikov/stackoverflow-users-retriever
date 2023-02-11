package org.example.filter;

import java.util.List;

public interface CustomFilter<T> {
    List<T> filterDto(List<T> dto);
}
