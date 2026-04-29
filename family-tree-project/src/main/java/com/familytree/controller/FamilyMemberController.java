package com.familytree.controller;

import com.familytree.model.FamilyMember;
import com.familytree.service.FamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/family")
@CrossOrigin(origins = "*")
public class FamilyMemberController {

    @Autowired
    private FamilyMemberService service;

    // Initialize sample data
    @PostMapping("/init")
    public ResponseEntity<Map<String, String>> initData() {
        service.initSampleData();
        Map<String, String> resp = new HashMap<>();
        resp.put("message", "示例数据初始化成功");
        return ResponseEntity.ok(resp);
    }

    // Get full tree
    @GetMapping("/tree")
    public ResponseEntity<List<Map<String, Object>>> getTree() {
        return ResponseEntity.ok(service.getFullTree());
    }

    // Get all members (flat list)
    @GetMapping
    public ResponseEntity<List<FamilyMember>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Get member by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create member
    @PostMapping
    public ResponseEntity<FamilyMember> create(@RequestBody FamilyMember member) {
        return ResponseEntity.ok(service.save(member));
    }

    // Update member
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FamilyMember member) {
        return service.getById(id).map(existing -> {
            member.setId(id);
            return ResponseEntity.ok(service.save(member));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete member
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("message", "删除成功");
        return ResponseEntity.ok(resp);
    }

    // Search
    @GetMapping("/search")
    public ResponseEntity<List<FamilyMember>> search(@RequestParam String name) {
        return ResponseEntity.ok(service.search(name));
    }

    // Get children
    @GetMapping("/{id}/children")
    public ResponseEntity<List<FamilyMember>> getChildren(@PathVariable Long id) {
        return ResponseEntity.ok(service.getChildren(id));
    }
}
