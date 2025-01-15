package com.example.Proj1_2021202039.service;

import com.example.Proj1_2021202039.entity.Board;
import com.example.Proj1_2021202039.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public List<Board> listBoards(){return boardRepository.findAll();}

    public Board createBoard(Board board){
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id){
        boardRepository.deleteById(id);
    }

}
