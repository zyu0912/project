package org.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.game.dto.GameBoard;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GameService {
	private ObjectMapper mapper;
	
	public GameService() {
		mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}
	
	// 5 steps for game logic
	public String playGame(GameBoard board) throws JsonProcessingException {
		// step 1: validate input game board
		if(!isValidGame(board)) {
			board.setMessage("invalid game board, please input again.");
			return mapper.writeValueAsString(board);
		}
		
		// step 2: validate this game is win or not
		String player = isWin(board);
		if(player != null) {
			board.setMessage("player " + player + " won.");
			return mapper.writeValueAsString(board);
		}
		
		// step 3: find all possible next steps, if no further step, draw game
		List<int[]> list = nextStep(board);
		if(list.isEmpty()) {
			board.setMessage("draw game.");
			return mapper.writeValueAsString(board);
		}
		
		// step 4: randomly choose one step
		// TODO, AI implementation should be here, now is just random play
		int index = new Random().nextInt(list.size());
		board.getBoard()[list.get(index)[0]][list.get(index)[1]] = "o";
		
		// step 5: after computer select next step, validate game win or not
		player = isWin(board);
		if(player != null) {
			board.setMessage("player " + player + " won.");
			return mapper.writeValueAsString(board);
		}
		
		board.setMessage("please input next move.");
		return mapper.writeValueAsString(board);
	}
	
	// go through all cells in game board, return all empty cells for next step
	private List<int[]> nextStep(GameBoard board) {
		List<int[]> list = new ArrayList<>();
		int row = board.getBoard().length;
		int col = board.getBoard()[0].length;
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				if(board.getBoard()[i][j].equals("")) {
					list.add(new int[]{i, j});
				}
			}
		}
		
		return list;
	}
	
	/*
	 * valid game board is that: 3 * 3 matrix, each cell contains "x", "o", or ""
	 * 
	 * TODO, if in future we need to expand the rule, like m * n or using other mark, 
	 * the implementation is here
	 */
	private boolean isValidGame(GameBoard board) {
		if(board == null) {
			return false;
		}
		
		int row = board.getBoard().length;
		if(row != 3) {
			return false;
		}
		
		int col = board.getBoard()[0].length;
		if(col != 3) {
			return false;
		}
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				String cell = board.getBoard()[i][j];
				if(cell == null) {
					return false;
				}
				
				if(cell.equals("") || cell.equals("x") || cell.equals("o")) {
					continue;
				}
				
				return false;
			}
		}
		
		return true;
	}
	
	/*
	 * in 3 * 3 game board, there are only 8 win patterns, therefore we don`t need any
	 * logic, just find out if game board match these patterns or not
	 * 
	 * TODO, if we expand game to m * n matrix and k cells in a line is win, we need to 
	 * overload this method isWin(GameBoard board, int row, int col), row and col is current
	 * move position, because game status from playing to win only depends on current move
	 */
	private String isWin(GameBoard board) {
		String cell = board.getBoard()[1][1];
		if(!cell.equals("")) {
			if(board.getBoard()[0][0].equals(cell) && board.getBoard()[2][2].equals(cell) || 
					board.getBoard()[0][1].equals(cell) && board.getBoard()[2][1].equals(cell) || 
					board.getBoard()[0][2].equals(cell) && board.getBoard()[2][0].equals(cell) || 
					board.getBoard()[1][0].equals(cell) && board.getBoard()[1][2].equals(cell)) {
				return cell;
			}
		}
		
		String left = board.getBoard()[0][0];
		if(!left.equals("")) {
			if(board.getBoard()[0][1].equals(left) && board.getBoard()[0][2].equals(left) || 
					board.getBoard()[1][0].equals(left) && board.getBoard()[2][0].equals(left)) {
				return left;
			}
		}
		
		String right = board.getBoard()[2][2];
		if(!right.equals("")) {
			if(board.getBoard()[2][0].equals(right) && board.getBoard()[2][1].equals(right) || 
					board.getBoard()[0][2].equals(right) && board.getBoard()[1][2].equals(right)) {
				return right;
			}
		}
		
		return null;
	}
	
}
