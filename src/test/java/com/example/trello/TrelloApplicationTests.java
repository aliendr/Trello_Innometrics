//package com.example.trello;
//
//import com.example.trello.controllers.TrelloController;
//import com.example.trello.entries.Board;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class TrelloApplicationTests {
//
//	@Autowired
//	TrelloController trelloController;
//
//
////	@Test
////	void invalidTokenKey() {
////		String tokenTRUE = "af80fef990571cc1916e003ca274a25c56822b200a99604a47d482e8f2338c38";
////		String keyTRUE = "ffce8063806eb2065f6d792bc0793ece";
////		String keyFALSE = "1";
////
////		Exception exception = assertThrows(ResponseStatusException.class,
////				() -> trelloController.addToken(tokenTRUE,keyFALSE));
////
////		assertEquals(exception.getMessage(), "404 NOT_FOUND \"invalid token\"");
////	}
////
////	@Test
////	void validTokenKey() {
////		String tokenTRUE = "af80fef990571cc1916e003ca274a25c56822b200a99604a47d482e8f2338c38";
////		String keyTRUE = "ffce8063806eb2065f6d792bc0793ece";
////		String keyFALSE = "1";
////
////		assertDoesNotThrow(()->trelloController.addToken(tokenTRUE,keyTRUE));
////
////		List<Board> boards = trelloController.addToken(tokenTRUE,keyTRUE);
////
////		assertNotNull(boards);
////	}
////
////
////	@Test
////	void invalidBoard() {
////		String tokenTRUE = "af80fef990571cc1916e003ca274a25c56822b200a99604a47d482e8f2338c38";
////		String keyTRUE = "ffce8063806eb2065f6d792bc0793ece";
////		String keyFALSE = "1";
////		String boardFALSE = "1";
////
////		Exception exception = assertThrows(ResponseStatusException.class,
////				() -> trelloController.fetchBoard(tokenTRUE,keyTRUE,boardFALSE));
////
////		assertEquals(exception.getMessage(), "404 NOT_FOUND \"invalid board url\"");
////	}
//
////	@Test
////	void validTokenAndBoard() throws IOException {
////		String tokenTRUE = "af80fef990571cc1916e003ca274a25c56822b200a99604a47d482e8f2338c38";
////		String keyTRUE = "ffce8063806eb2065f6d792bc0793ece";
////		String keyFALSE = "1";
////		String boardTRUE = "Dq31c0pm";
////
////		assertDoesNotThrow(()->trelloController.fetchBoard(tokenTRUE,keyTRUE,boardTRUE));
////
////        Optional<Board> board = trelloController.fetchBoard(tokenTRUE,keyTRUE,boardTRUE);
////
////        assert board.isPresent();
////
////        if(board.isPresent()){
////            assertNotNull(board.get().getBoardId());
////            assertNotNull(board.get().getName());
////            assertNotNull(board.get().getBoardUrl());
////            assertEquals(board.get().getBoardUrl(),boardTRUE);
////        }
////
////
////	}
//}


