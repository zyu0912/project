package org.game.service;

import org.game.dto.GameBoard;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GameServiceTest {
	private GameService service = new GameService();
	
	@Test
	public void invalidGameBoardTest() throws JsonProcessingException {
		GameBoard board = new GameBoard();
		
		// invalid string "a" in game board
		String[][] array = new String[][]{{"a", "", ""}, {"", "", ""}, {"", "", ""}};
		board.setBoard(array);
		service.playGame(board);
		Assert.assertEquals("invalid game board, please input again.", board.getMessage());
		
		// not 3 * 3 game board
		array = new String[][]{{"", "", ""}, {"", "", ""}};
		board.setBoard(array);
		service.playGame(board);
		Assert.assertEquals("invalid game board, please input again.", board.getMessage());
	}
	
	@Test
	public void winGameBoardTest() throws JsonProcessingException {
		GameBoard board = new GameBoard();
		
		String[][] array = new String[][]{{"x", "o", ""}, {"x", "", "o"}, {"x", "", ""}};
		board.setBoard(array);
		service.playGame(board);
		Assert.assertEquals("player x won.", board.getMessage());
		
		array = new String[][]{{"", "x", ""}, {"o", "o", "o"}, {"x", "", ""}};
		board.setBoard(array);
		service.playGame(board);
		Assert.assertEquals("player o won.", board.getMessage());
	}
	
	@Test
	public void drawGameTest() throws JsonProcessingException {
		GameBoard board = new GameBoard();
		
		String[][] array = new String[][]{{"x", "o", "x"}, {"x", "o", "o"}, {"o", "x", "o"}};
		board.setBoard(array);
		service.playGame(board);
		Assert.assertEquals("draw game.", board.getMessage());
	}
}
