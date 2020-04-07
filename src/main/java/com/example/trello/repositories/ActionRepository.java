package com.example.trello.repositories;
import com.example.trello.entries.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action,String> {
    List<Optional<Action>> findAllByTypeAndBoardIdOrderByDate(String type, String boardId);
    List<Optional<Action>> findAllByTypeAndBoardIdAndDateBeforeOrderByDate(String type, String boardId,String before);
    List<Optional<Action>> findAllByTypeAndBoardIdAndDateAfterOrderByDate(String type, String boardId,String after);
    List<Optional<Action>> findAllByTypeAndBoardIdAndDateAfterAndDateBeforeOrderByDate(String type, String boardId,String after,String before);
    List<Optional<Action>> findAllByBoardIdAndDateBeforeOrderByDate(String boardId,String before);
    List<Optional<Action>> findAllByBoardIdAndDateAfterOrderByDate(String boardId, String after);
    List<Optional<Action>> findAllByBoardIdAndDateBetweenOrderByDate(String boardId, String after, String before);
    Optional<Action> findByBoardIdAndActionId(String boardId, String actionId);

    List<Optional<Action>> findAllByBoardId(String boardId);
}
