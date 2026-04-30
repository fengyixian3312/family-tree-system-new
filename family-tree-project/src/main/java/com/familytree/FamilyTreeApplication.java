package com.familytree;

import com.familytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FamilyTreeApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(FamilyTreeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userService.initAdminUser();
        System.out.println("\n========================================");
        System.out.println("  家谱管理系统 已启动!");
        System.out.println("  访问地址: http://localhost:8080");
        System.out.println("  默认账号: admin / admin123");
        System.out.println("========================================\n");
    }
}