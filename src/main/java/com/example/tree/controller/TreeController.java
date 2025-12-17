package com.example.tree.controller;

import com.example.tree.model.TreeNode;
import com.example.tree.model.TreeResult;
import com.example.tree.repository.TreeResultRepository;
import com.example.tree.service.TreeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
public class TreeController {

    private final TreeService treeService;
    private final TreeResultRepository repository;

    public TreeController(TreeService treeService, TreeResultRepository repository) {
        this.treeService = treeService;
        this.repository = repository;
    }

    @GetMapping("/enter-numbers")
    public String enterNumbers() {
        return "EnterNumbers";
    }

    @PostMapping(value = "/process-numbers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> processNumbers(@RequestParam("numbers") String numbers) {
        List<Integer> parsed = treeService.parseNumbers(numbers);
        TreeNode root = treeService.buildBst(parsed);
        String json = treeService.toJson(root);

        repository.save(new TreeResult(numbers, json));
        return ResponseEntity.ok(json);
    }

    @GetMapping("/previous-trees")
    public String previousTrees(Model model) {
        List<TreeResult> results = repository.findAll()
                .stream()
                .sorted(Comparator.comparing(TreeResult::getCreatedAt).reversed())
                .toList();

        model.addAttribute("results", results);
        return "PreviousTree";
    }
}
