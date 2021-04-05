package com.book.spring.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class HelloResponseDto {
    private String name;
    private int amount;
}
