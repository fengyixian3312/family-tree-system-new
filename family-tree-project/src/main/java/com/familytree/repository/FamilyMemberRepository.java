package com.familytree.repository;

import com.familytree.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    List<FamilyMember> findByParentId(Long parentId);
    List<FamilyMember> findByParentIdIsNull();
    List<FamilyMember> findByGeneration(Integer generation);
    List<FamilyMember> findByNameContaining(String name);
    List<FamilyMember> findByOrderByGenerationAscIdAsc();
}
