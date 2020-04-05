package com.example.trello.repositories;
import com.example.trello.entries.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action,String> {
    List<Optional<Action>> findAllByTypeOrderByDate(String type);
    List<Optional<Action>> findAllByTypeAndBoardIdOrderByDate(String type, String boardId);
    List<Optional<Action>> findAllByTypeAndBoardIdAndDateBefore(String type, String boardId,String before);
    List<Optional<Action>> findAllByTypeAndBoardIdAndDateAfter(String type, String boardId,String after);
    List<Optional<Action>> findAllByTypeAndBoardIdAndDateAfterAndDateBefore(String type, String boardId,String after,String before);


    List<Optional<Action>> findAllByDateBetweenOrderByDate(String after, String before);
    List<Optional<Action>> findAllByDateAfterOrderByDate(String after);
    List<Optional<Action>> findAllByDateBeforeOrderByDate(String before);
    Optional<Action> findByBoardIdAndActionId(String boardId, String actionId);
}
