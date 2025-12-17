package com.example.tree.service;

import com.example.tree.model.TreeNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreeService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Integer> parseNumbers(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException("Numbers input cannot be empty.");
        }

        String[] parts = raw.split("[,\\s]+");
        List<Integer> nums = new ArrayList<>();
        for (String p : parts) {
            if (p == null || p.trim().isEmpty()) continue;
            nums.add(Integer.parseInt(p.trim()));
        }

        if (nums.isEmpty()) {
            throw new IllegalArgumentException("No valid numbers were provided.");
        }

        return nums;
    }

    public TreeNode buildBst(List<Integer> nums) {
        TreeNode root = null;
        for (int v : nums) {
            root = insert(root, v);
        }
        return root;
    }

    public TreeNode insert(TreeNode node, int value) {
        if (node == null) return new TreeNode(value);

        if (value < node.getValue()) {
            node.setLeft(insert(node.getLeft(), value));
        } else {
            node.setRight(insert(node.getRight(), value));
        }

        return node;
    }

    public String toJson(TreeNode root) {
        try {
            return objectMapper.writeValueAsString(new RootWrapper(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize tree to JSON.", e);
        }
    }

    public record RootWrapper(TreeNode root) {}
}