package com.example.tree.repository;

import com.example.tree.model.TreeResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TreeRepositoryTest {

    @Autowired
    private TreeResultRepository repo;

    @Test
    void saveAndLoadTreeResult() {
        TreeResult saved = repo.save(new TreeResult("1,2,3", "{\"root\":null}"));
        assertNotNull(saved.getId());

        TreeResult loaded = repo.findById(saved.getId()).orElseThrow();
        assertEquals("1,2,3", loaded.getInputNumbers());
        assertTrue(loaded.getTreeJson().contains("root"));
    }
}