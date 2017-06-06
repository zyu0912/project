package org.game.controller;

import org.game.dto.GameBoard;
import org.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class GameController {
	@Autowired
	private GameService service;
	
	/*
	 * the tic-tac-toe REST API is very simple, input parameter is 3 * 3 game board
	 * with any cell contains "x", "o" or "", then API will return next move game board. 
	 * 
	 * player is use "x" and computer use "o"
	 * 
	 * you can pass any status game board, even a win game board or full game board
	 * game board example is {"board":[["x","o",""],["o","x","o"],["","","x"]]}
	 */
	@RequestMapping(value = "/game/play", method = RequestMethod.POST)
	@ResponseBody()
	public String playGame(@RequestBody GameBoard board) throws JsonProcessingException {
		
		return service.playGame(board);
	}
}
