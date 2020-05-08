package com.example.trello.repositories;

import com.example.trello.entries.Action;
import com.example.trello.entries.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,String> {
    public Optional<Board> findByTokenAndKeyAndBoardUrl(String token, String key, String url);
    public Optional<List<Board>> findAllByTokenAndKey(String token, String key);
    public Board findAllByBoardId(String boardId);

}

