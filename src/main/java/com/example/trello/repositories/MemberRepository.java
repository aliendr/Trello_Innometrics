package com.example.trello.repositories;

import com.example.trello.entries.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,String> {
    public Member findDistinctByBoardsIsContainingAndAndTokenAndKey(String boardId, String token, String key);
    public Member findDistinctByTokenAndKey(String token, String key);
}
