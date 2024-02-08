package org.gigachattest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// todo / use this
@Getter
@Setter
@RequiredArgsConstructor
class GigaChatDto {
    private String role;
    private String content;
}