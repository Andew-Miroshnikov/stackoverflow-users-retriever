package org.example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.example.mapper.UserMapper;
import org.example.model.StackoverflowApiResponseDto;
import org.example.model.StackoverflowTagDto;
import org.example.model.StackoverflowUserDto;
import org.example.model.User;
import org.example.filter.CustomFilter;
import retrofit2.Call;
import retrofit2.Response;

public class UserServiceImpl implements UserService {
    private static final int START_PAGE = 1;
    private static final int AMOUNT_MILLISECONDS_IN_SECOND = 1000;
    private static final int DEFAULT_BACKOFF = 50;
    private final StackoverflowService stackoverflowService;
    private final CustomFilter<StackoverflowUserDto> userFilter;
    private final CustomFilter<StackoverflowTagDto> tagFilter;

    private final UserMapper userMapper;

    public UserServiceImpl(StackoverflowService stackoverflowService,
                           CustomFilter<StackoverflowUserDto> userFilter,
                           CustomFilter<StackoverflowTagDto> tagFilter,
                           UserMapper userMapper) {
        this.stackoverflowService = stackoverflowService;
        this.userFilter = userFilter;
        this.tagFilter = tagFilter;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getFilteredUsers() {
        List<User> users = new ArrayList<>();
        List<StackoverflowUserDto> stackoverflowUserDto = retrieveUsers(START_PAGE);
        for (StackoverflowUserDto userDto : stackoverflowUserDto) {
            List<StackoverflowTagDto> tagsDto = retrieveTagsByUserId(userDto.getUserId(), START_PAGE);
            if (!tagFilter.filterDto(tagsDto).isEmpty()) {
                users.add(userMapper.fromDto(userDto, tagsDto));
            }
        }
        return users;
    }

    public List<StackoverflowUserDto> retrieveUsers(int page) {
        List<StackoverflowUserDto> dtoFromAllPages = new ArrayList<>();
        StackoverflowApiResponseDto<StackoverflowUserDto> body;
        do {
            Response<StackoverflowApiResponseDto<StackoverflowUserDto>> userResponse =
                    receiveUserResponse(page);
            body = userResponse.body();
            if (body == null) {
                System.out.println("Can't execute users response from retriever on page "
                        + page + System.lineSeparator());
                return dtoFromAllPages;
            }
            List<StackoverflowUserDto> items = body.getItems();
            List<StackoverflowUserDto> stackoverflowUserDto = userFilter.filterDto(items);
            dtoFromAllPages.addAll(stackoverflowUserDto);
            page++;
            Integer backoff = body.getBackoff();
            waitForBackoff(backoff == null ? DEFAULT_BACKOFF : backoff * AMOUNT_MILLISECONDS_IN_SECOND);
        } while (body.isHasMore() && body.getQuotaRemaining() > 1);
        return dtoFromAllPages;
    }

    public List<StackoverflowTagDto> retrieveTagsByUserId(Long id, int page) {
        List<StackoverflowTagDto> dtoFromAllPages = new ArrayList<>();
        StackoverflowApiResponseDto<StackoverflowTagDto> body;
        do {
            Response<StackoverflowApiResponseDto<StackoverflowTagDto>> executeResponse =
                    receiveTagResponse(id, page);
            body = executeResponse.body();
            if (body == null) {
                System.out.println("Can't execute tags response from retriever on page "
                        + page + System.lineSeparator());
                return dtoFromAllPages;
            }
            List<StackoverflowTagDto> items = body.getItems();
            dtoFromAllPages.addAll(items);
            page++;
            Integer backoff = body.getBackoff();
            waitForBackoff(backoff == null ? DEFAULT_BACKOFF : backoff * AMOUNT_MILLISECONDS_IN_SECOND);
        } while (body.isHasMore() && body.getQuotaRemaining() > 1);
        return dtoFromAllPages;
    }

    public Response<StackoverflowApiResponseDto<StackoverflowTagDto>> receiveTagResponse(Long id,
                                                                                         int page) {
        Call<StackoverflowApiResponseDto<StackoverflowTagDto>> tagsByUserId =
                stackoverflowService.getTagsByUserId(id, page);
        try {
            return tagsByUserId.execute();
        } catch (IOException e) {
            throw new RuntimeException("Response execution goes wrong on page " + page, e);
        }
    }

    public Response<StackoverflowApiResponseDto<StackoverflowUserDto>> receiveUserResponse(int page) {
        Call<StackoverflowApiResponseDto<StackoverflowUserDto>> users =
                stackoverflowService.getUsers(page);
        try {
            return users.execute();
        } catch (IOException e) {
            throw new RuntimeException("Response execution goes wrong on page " + page, e);
        }
    }

    public void waitForBackoff(int backoff) {
        try {
            Thread.sleep(backoff);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
