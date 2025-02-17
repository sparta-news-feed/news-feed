package com.newsfeed.common.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageResponse {

    private final String message;

    public static MessageResponse toDto(String message) {
        return new MessageResponse(message);
    }
}
