package org.gigachattest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Communication {
    private String role;
    private String content;

    public Communication(String role, String content) {
        this.role = role;
        this.content = content;
    }
}