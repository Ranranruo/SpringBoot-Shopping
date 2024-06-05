package com.create.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceMessage {
    private boolean result;
    private String message;

    public boolean getResult() {
        return result;
    }
}
