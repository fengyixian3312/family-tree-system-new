package com.familytree.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "family_member")
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String courtesy; // 字号

    private String gender; // 男/女

    private Integer generation; // 世代数 (一世, 二世, ...)

    private String birthDate; // 生

    private String deathDate; // 卒

    private String birthDeathNote; // 生卒俱失考 等备注

    @Column(name = "parent_id")
    private Long parentId; // 父亲ID

    @Column(name = "spouse_name")
    private String spouseName; // 配偶姓名

    @Column(name = "spouse_maiden_name")
    private String spouseMaidenName; // 妣/姓氏

    @Column(name = "spouse_note")
    private String spouseNote; // 配偶备注（如失记）

    @Column(name = "filial_sons")
    private String filialSons; // 孝男（儿子名）

    @Column(name = "children_count")
    private Integer childrenCount; // 生子X人

    private String notes; // 备注

    @Column(name = "relation_desc")
    private String relationDesc; // 关系描述，如"有禄公之五子也"

    @Column(name = "worshipped_by")
    private String worshippedBy; // 奉祀者

    @Column(name = "title")
    private String title; // 称谓，如 祖 謚

    @Column(name = "spirit_tablet")
    private String spiritTablet; // 神主牌位

    @Column(name = "burial_address")
    private String burialAddress; // 埋葬地址

    @Column(name = "burial_direction")
    private String burialDirection; // 埋葬向法

    // 妣的生卒
    @Column(name = "bi_birth_date")
    private String biBirthDate;

    @Column(name = "bi_death_date")
    private String biDeathDate;

    @Column(name = "bi_birth_death_note")
    private String biBirthDeathNote;

    // 妣的神主牌位
    @Column(name = "bi_spirit_tablet")
    private String biSpiritTablet;

    // 妣的埋葬地址
    @Column(name = "bi_burial_address")
    private String biBurialAddress;

    // 妣的埋葬向法
    @Column(name = "bi_burial_direction")
    private String biBurialDirection;

    // getter/setter
    public String getBiBirthDate(){return biBirthDate;}
    public void setBiBirthDate(String v){this.biBirthDate=v;}
    public String getBiDeathDate(){return biDeathDate;}
    public void setBiDeathDate(String v){this.biDeathDate=v;}
    public String getBiBirthDeathNote(){return biBirthDeathNote;}
    public void setBiBirthDeathNote(String v){this.biBirthDeathNote=v;}
    public String getBiSpiritTablet(){return biSpiritTablet;}
    public void setBiSpiritTablet(String v){this.biSpiritTablet=v;}
    public String getBiBurialAddress(){return biBurialAddress;}
    public void setBiBurialAddress(String v){this.biBurialAddress=v;}
    public String getBiBurialDirection(){return biBurialDirection;}
    public void setBiBurialDirection(String v){this.biBurialDirection=v;}

    public String getSpiritTablet() { return spiritTablet; }
    public void setSpiritTablet(String spiritTablet) { this.spiritTablet = spiritTablet; }

    public String getBurialAddress() { return burialAddress; }
    public void setBurialAddress(String burialAddress) { this.burialAddress = burialAddress; }

    public String getBurialDirection() { return burialDirection; }
    public void setBurialDirection(String burialDirection) { this.burialDirection = burialDirection; }

    // Constructors
    public FamilyMember() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCourtesy() { return courtesy; }
    public void setCourtesy(String courtesy) { this.courtesy = courtesy; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getGeneration() { return generation; }
    public void setGeneration(Integer generation) { this.generation = generation; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getDeathDate() { return deathDate; }
    public void setDeathDate(String deathDate) { this.deathDate = deathDate; }

    public String getBirthDeathNote() { return birthDeathNote; }
    public void setBirthDeathNote(String birthDeathNote) { this.birthDeathNote = birthDeathNote; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public String getSpouseName() { return spouseName; }
    public void setSpouseName(String spouseName) { this.spouseName = spouseName; }

    public String getSpouseMaidenName() { return spouseMaidenName; }
    public void setSpouseMaidenName(String spouseMaidenName) { this.spouseMaidenName = spouseMaidenName; }

    public String getSpouseNote() { return spouseNote; }
    public void setSpouseNote(String spouseNote) { this.spouseNote = spouseNote; }

    public String getFilialSons() { return filialSons; }
    public void setFilialSons(String filialSons) { this.filialSons = filialSons; }

    public Integer getChildrenCount() { return childrenCount; }
    public void setChildrenCount(Integer childrenCount) { this.childrenCount = childrenCount; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getRelationDesc() { return relationDesc; }
    public void setRelationDesc(String relationDesc) { this.relationDesc = relationDesc; }

    public String getWorshippedBy() { return worshippedBy; }
    public void setWorshippedBy(String worshippedBy) { this.worshippedBy = worshippedBy; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
