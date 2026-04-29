package com.familytree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FamilyTreeApplication {
    public static void main(String[] args) {
        SpringApplication.run(FamilyTreeApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  家谱管理系统 已启动!");
        System.out.println("  访问地址: http://localhost:8080");
        System.out.println("  H2控制台: http://localhost:8080/h2-console");
        System.out.println("========================================\n");
    }
}
