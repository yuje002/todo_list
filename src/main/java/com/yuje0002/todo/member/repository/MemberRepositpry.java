package com.yuje0002.todo.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuje0002.todo.member.entity.MemberInfoEntity;

@Repository
public interface MemberRepositpry extends JpaRepository<MemberInfoEntity, Long> {
    public Integer countByEmail(String email);
    public MemberInfoEntity findByEmailAndPwd(String id, String pwd);
}
