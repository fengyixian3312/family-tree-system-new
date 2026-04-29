package com.familytree.service;

import com.familytree.model.FamilyMember;
import com.familytree.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FamilyMemberService {

    @Autowired
    private FamilyMemberRepository repository;

    public List<FamilyMember> getAll() {
        return repository.findByOrderByGenerationAscIdAsc();
    }

    public Optional<FamilyMember> getById(Long id) {
        return repository.findById(id);
    }

    public FamilyMember save(FamilyMember member) {
        return repository.save(member);
    }

    public void delete(Long id) {
        // Also update children to remove parent reference
        List<FamilyMember> children = repository.findByParentId(id);
        for (FamilyMember child : children) {
            child.setParentId(null);
            repository.save(child);
        }
        repository.deleteById(id);
    }

    public List<FamilyMember> getRoots() {
        return repository.findByParentIdIsNull();
    }

    public List<FamilyMember> getChildren(Long parentId) {
        return repository.findByParentId(parentId);
    }

    public List<FamilyMember> search(String name) {
        return repository.findByNameContaining(name);
    }

    public Map<String, Object> getTreeNode(FamilyMember member) {
        Map<String, Object> node = new LinkedHashMap<>();
        node.put("id", member.getId());
        node.put("name", member.getName());
        node.put("generation", member.getGeneration());
        node.put("gender", member.getGender());
        node.put("title", member.getTitle());
        node.put("birthDate", member.getBirthDate());
        node.put("deathDate", member.getDeathDate());
        node.put("birthDeathNote", member.getBirthDeathNote());
        node.put("relationDesc", member.getRelationDesc());
        node.put("spouseName", member.getSpouseName());
        node.put("spouseMaidenName", member.getSpouseMaidenName());
        node.put("spouseNote", member.getSpouseNote());
        node.put("filialSons", member.getFilialSons());
        node.put("childrenCount", member.getChildrenCount());
        node.put("worshippedBy", member.getWorshippedBy());
        node.put("notes", member.getNotes());
        node.put("parentId", member.getParentId());
        node.put("courtesy", member.getCourtesy());

        List<FamilyMember> children = repository.findByParentId(member.getId());
        List<Map<String, Object>> childNodes = new ArrayList<>();
        for (FamilyMember child : children) {
            childNodes.add(getTreeNode(child));
        }
        node.put("children", childNodes);

        return node;
    }

    public List<Map<String, Object>> getFullTree() {
        List<FamilyMember> roots = getRoots();
        List<Map<String, Object>> tree = new ArrayList<>();
        for (FamilyMember root : roots) {
            tree.add(getTreeNode(root));
        }
        return tree;
    }

    public boolean hasData() {
        return repository.count() > 0;
    }

    public void initSampleData() {
        if (hasData()) return;

        // 一世祖
        FamilyMember gen1 = new FamilyMember();
        gen1.setName("有禄");
        gen1.setGender("男");
        gen1.setGeneration(1);
        gen1.setTitle("祖 諱");
        gen1.setCourtesy("有禄");
        gen1.setBirthDeathNote("生卒俱失考");
        gen1.setNotes("始祖");
        gen1.setWorshippedBy("子孫奉祀");
        gen1 = repository.save(gen1);

        // 二世祖 仁（有禄公之五子）
        FamilyMember gen2a = new FamilyMember();
        gen2a.setName("仁");
        gen2a.setGender("男");
        gen2a.setGeneration(2);
        gen2a.setTitle("祖 諱");
        gen2a.setParentId(gen1.getId());
        gen2a.setRelationDesc("有禄公之五子也");
        gen2a.setBirthDeathNote("生卒俱失考");
        gen2a.setSpouseName("失記");
        gen2a.setSpouseMaidenName("妣");
        gen2a.setSpouseNote("失記");
        gen2a.setFilialSons("鐘");
        gen2a.setWorshippedBy("孝男鐘奉祀");
        gen2a = repository.save(gen2a);

        // 二世其他兄弟
        String[] siblings = {"義", "禮", "智", "信"};
        for (int i = 0; i < siblings.length; i++) {
            FamilyMember sib = new FamilyMember();
            sib.setName(siblings[i]);
            sib.setGender("男");
            sib.setGeneration(2);
            sib.setTitle("祖 諱");
            sib.setParentId(gen1.getId());
            sib.setRelationDesc("有禄公之" + toChineseNum(i+1) + "子也");
            sib.setBirthDeathNote("生卒俱失考");
            sib = repository.save(sib);
        }

        // 三世祖 鐘（仁公之子）
        FamilyMember gen3 = new FamilyMember();
        gen3.setName("鐘");
        gen3.setGender("男");
        gen3.setGeneration(3);
        gen3.setTitle("祖 諱");
        gen3.setParentId(gen2a.getId());
        gen3.setRelationDesc("仁公之子也");
        gen3.setBirthDeathNote("生卒俱失考");
        gen3.setSpouseName("失記");
        gen3.setSpouseMaidenName("妣");
        gen3.setSpouseNote("失記");
        gen3.setFilialSons("法印");
        gen3.setChildrenCount(1);
        gen3.setWorshippedBy("孝男法印奉祀");
        gen3 = repository.save(gen3);

        // 四世祖 芍（鐘公之子）
        FamilyMember gen4 = new FamilyMember();
        gen4.setName("芍");
        gen4.setGender("男");
        gen4.setGeneration(4);
        gen4.setTitle("祖 諱");
        gen4.setParentId(gen3.getId());
        gen4.setRelationDesc("鐘公之子也");
        gen4.setBirthDeathNote("生卒俱失考");
        gen4.setSpouseName("失記");
        gen4.setSpouseMaidenName("妣");
        gen4.setSpouseNote("失記");
        gen4.setChildrenCount(1);
        gen4.setWorshippedBy("孝男法印奉祀");
        gen4 = repository.save(gen4);

        // 五世
        FamilyMember gen5 = new FamilyMember();
        gen5.setName("法印");
        gen5.setGender("男");
        gen5.setGeneration(5);
        gen5.setTitle("祖 諱");
        gen5.setParentId(gen4.getId());
        gen5.setRelationDesc("芍公之子也");
        gen5.setBirthDate("清道光年間");
        gen5.setDeathDate("不詳");
        gen5.setSpouseName("陳氏");
        gen5.setSpouseMaidenName("妣");
        gen5.setChildrenCount(3);
        gen5.setWorshippedBy("子孫奉祀");
        gen5 = repository.save(gen5);

        // 六世
        String[] gen6names = {"明德", "明志", "明仁"};
        for (String n : gen6names) {
            FamilyMember m = new FamilyMember();
            m.setName(n);
            m.setGender("男");
            m.setGeneration(6);
            m.setTitle("公 諱");
            m.setParentId(gen5.getId());
            m.setRelationDesc("法印公之子也");
            m.setBirthDate("清咸豐年間");
            repository.save(m);
        }
    }

    private String toChineseNum(int n) {
        String[] nums = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        return n <= 10 ? nums[n-1] : String.valueOf(n);
    }
}
