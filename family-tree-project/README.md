# 家譜管理系統 - Family Tree Management System

## 系統需求
- Java 17+ (JDK)
- Maven 3.6+
- 或直接使用 Spring Boot Maven Plugin

## 啟動方式

### 方式一：Maven 直接啟動
```bash
cd family-tree
mvn spring-boot:run
```

### 方式二：打包後啟動
```bash
cd family-tree
mvn clean package
java -jar target/family-tree-1.0.0.jar
```

## 訪問地址
- 主頁面：http://localhost:8080
- H2資料庫控制台：http://localhost:8080/h2-console
  - JDBC URL: jdbc:h2:file:./familytree-db
  - Username: sa
  - Password: (空)

## 功能說明
1. **譜牒檢視** - 仿古譜牒樣式，直書顯示每位族員信息
2. **世系圖** - 樹狀圖顯示傳承關係  
3. **族員列表** - 表格形式管理所有族員
4. **新增/編輯** - 完整的族員信息錄入
5. **搜尋篩選** - 按名字搜尋，按世代篩選
6. **數據持久化** - H2嵌入式資料庫，重啟後數據保留

## API 接口
- GET  /api/family        - 所有族員
- GET  /api/family/tree   - 樹狀結構數據
- POST /api/family        - 新增族員
- PUT  /api/family/{id}   - 更新族員
- DELETE /api/family/{id} - 刪除族員
- GET  /api/family/search?name=xxx - 搜尋
- POST /api/family/init   - 載入示例數據

## 項目結構
```
family-tree/
├── pom.xml
├── src/main/java/com/familytree/
│   ├── FamilyTreeApplication.java   (主入口)
│   ├── model/FamilyMember.java      (實體類)
│   ├── repository/FamilyMemberRepository.java
│   ├── service/FamilyMemberService.java
│   └── controller/FamilyMemberController.java
└── src/main/resources/
    ├── application.properties
    └── static/index.html            (前端頁面)
```
