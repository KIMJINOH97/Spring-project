package com.book.spring.controller;

import com.book.spring.controller.dto.HelloResponseDto;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest extends TestCase {
    @Autowired
    private MockMvc mvc;

    @Test
    public void hello() throws Exception{
        String hello = "hello";
        mvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(content().string(hello));
    }

    @Test
    public void hellodto() throws Exception{
        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        mvc.perform(get("/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}