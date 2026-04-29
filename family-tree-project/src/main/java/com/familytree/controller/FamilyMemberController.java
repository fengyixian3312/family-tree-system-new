package com.familytree.controller;

import com.familytree.model.FamilyMember;
import com.familytree.service.FamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/family")
@CrossOrigin(origins = "*")
public class FamilyMemberController {

    @Autowired
    private FamilyMemberService service;

    private boolean isAdmin(Authentication auth) {
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @PostMapping("/init")
    public ResponseEntity<?> initData(Authentication auth) {
        if (!isAdmin(auth)) return ResponseEntity.status(403).body("无权限");
        service.initSampleData();
        return ResponseEntity.ok(Map.of("message", "示例数据初始化成功"));
    }

    @GetMapping("/tree")
    public ResponseEntity<List<Map<String, Object>>> getTree() {
        return ResponseEntity.ok(service.getFullTree());
    }

    @GetMapping
    public ResponseEntity<List<FamilyMember>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FamilyMember member, Authentication auth) {
        if (!isAdmin(auth)) return ResponseEntity.status(403).body("无权限");
        return ResponseEntity.ok(service.save(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FamilyMember member, Authentication auth) {
        if (!isAdmin(auth)) return ResponseEntity.status(403).body("无权限");
        return service.getById(id).map(e -> {
            member.setId(id);
            return ResponseEntity.ok(service.save(member));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        if (!isAdmin(auth)) return ResponseEntity.status(403).body("无权限");
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "删除成功"));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FamilyMember>> search(@RequestParam String name) {
        return ResponseEntity.ok(service.search(name));
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<List<FamilyMember>> getChildren(@PathVariable Long id) {
        return ResponseEntity.ok(service.getChildren(id));
    }
}