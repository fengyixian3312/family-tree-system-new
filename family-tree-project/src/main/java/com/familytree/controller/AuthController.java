package com.familytree.controller;

import com.familytree.model.User;
import com.familytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // 获取当前登录用户信息
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> info = new HashMap<>();
        info.put("username", auth.getName());
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        info.put("isAdmin", isAdmin);
        return ResponseEntity.ok(info);
    }

    // 获取所有用户（仅管理员）
    @GetMapping("/users")
    public ResponseEntity<?> listUsers(Authentication auth) {
        if (!isAdmin(auth)) return ResponseEntity.status(403).body("无权限");
        List<User> users = userService.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (User u : users) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", u.getId());
            m.put("username", u.getUsername());
            m.put("role", u.getRole());
            m.put("displayName", u.getDisplayName());
            result.add(m);
        }
        return ResponseEntity.ok(result);
    }

    // 新增用户（仅管理员）
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> body, Authentication auth) {
        if (!isAdmin(auth)) return ResponseEntity.status(403).body("无权限");
        String username = body.get("username");
        String password = body.get("password");
        String role = body.getOrDefault("role", "USER");
        String displayName = body.getOrDefault("displayName", username);
        if (userService.existsByUsername(username)) {
            return ResponseEntity.badRequest().body("用户名已存在");
        }
        userService.createUser(username, password, role, displayName);
        return ResponseEntity.ok(Map.of("message", "创建成功"));
    }

    // 删除用户（仅管理员）
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Authentication auth) {
        if (!isAdmin(auth)) return ResponseEntity.status(403).body("无权限");
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message", "删除成功"));
    }

    private boolean isAdmin(Authentication auth) {
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}