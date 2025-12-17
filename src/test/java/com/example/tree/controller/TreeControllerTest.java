package com.example.tree.controller;

import com.example.tree.repository.TreeResultRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TreeResultRepository repo;

    @Test
    void processNumbers_returnsJsonAndSaves() throws Exception {
        long before = repo.count();

        mockMvc.perform(post("/process-numbers").param("numbers", "2,1,4,3,5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("\"root\"")));

        long after = repo.count();
        assertEquals(before + 1, after);
    }
}