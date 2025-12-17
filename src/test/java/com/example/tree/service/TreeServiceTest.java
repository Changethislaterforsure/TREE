package com.example.tree.service;

import com.example.tree.model.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreeServiceTest {

    private final TreeService service = new TreeService();

    @Test
    void parseNumbers_handlesCommaAndSpace() {
        List<Integer> nums = service.parseNumbers("5, 2 9,10");
        assertEquals(List.of(5, 2, 9, 10), nums);
    }

    @Test
    void buildBst_insertsSequentially() {
        TreeNode root = service.buildBst(List.of(2, 1, 4, 3, 5));
        assertEquals(2, root.getValue());
        assertEquals(1, root.getLeft().getValue());
        assertEquals(4, root.getRight().getValue());
        assertEquals(3, root.getRight().getLeft().getValue());
        assertEquals(5, root.getRight().getRight().getValue());
    }
}