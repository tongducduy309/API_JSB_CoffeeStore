package com.api_coffee_store.api_coffee_store.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseObject {
    private int status;
    private String message;
    private Object data;


}
