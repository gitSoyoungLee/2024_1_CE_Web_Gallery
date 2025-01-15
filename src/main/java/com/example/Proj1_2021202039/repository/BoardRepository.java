package com.example.Proj1_2021202039.repository;

import com.example.Proj1_2021202039.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board save(Board board);
    Optional<Board>findById(Long id);

    List<Board> findAll();
}
