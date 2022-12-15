package com.yuje0002.todo.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuje0002.todo.member.entity.MemberImageEntity;


@Repository
public interface MemberImageRepository extends JpaRepository<MemberImageEntity, Long> {
    public List<MemberImageEntity> findByMiSeq(Long miSeq);
    public List<MemberImageEntity> findTopByUriOrderBySeqDesc(String uri);
}
