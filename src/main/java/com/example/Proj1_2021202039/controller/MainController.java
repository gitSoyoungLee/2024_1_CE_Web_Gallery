package com.example.Proj1_2021202039.controller;

import com.example.Proj1_2021202039.entity.Board;
import com.example.Proj1_2021202039.repository.BoardRepository;
import com.example.Proj1_2021202039.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    //Index.html 불러오기
    @GetMapping({"/index.html","/","/Index.html"})
    public String showIndex(Model model){
        List<Board> boards=new ArrayList<>();
        boards=boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "Index";
    }

    @GetMapping({"/upload","/Upload.html"})
    public String showUploadForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            //  기존 데이터 수정인 경우
            Optional<Board> board = boardRepository.findById(id);
            model.addAttribute("board", board.orElse(new Board()));
        } else {
            // 새로운 데이터 추가인 경우
            model.addAttribute("board", new Board());
        }
        return "Upload";
    }

    //이미지 업로드(Upload.html에서 게시글 추가)
    @PostMapping("/board")
    public RedirectView createBoard(@RequestParam("title") String title, @RequestParam("contents") String contents, @RequestParam("image")MultipartFile file, @RequestParam(value = "id", required = false) Long id){
        if(file.isEmpty()){
            return new RedirectView("/upload");
        }
        try{
            //업로드된 이미지를 프로젝트 폴더(static)에 저장
            String defaultPath="src/main/resources/static/";
            byte[] bytes=file.getBytes();
            Path path= Paths.get(defaultPath+ file.getOriginalFilename());
            Files.write(path, bytes);

            //데이터베이스에 등록할 개체
            Board board;
            if(id!=null){
                // 기존 데이터 수정인 경우
                board = boardRepository.findById(id).orElse(new Board());
                board.setTitle(title);
                board.setContents(contents);
                board.setImage(file.getOriginalFilename());
                boardService.createBoard(board);    // 수정한 정보 데이터베이스에 저장
            }else {
                //새로운 데이터 추가인 경우
                board = new Board();
                board.setTitle(title);
                board.setContents(contents);
                board.setImage(file.getOriginalFilename());
                boardService.createBoard(board);    //이미지 업로드(데이터베이스 등록)
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new RedirectView("/");   //index.html로 리다이렉트
    }

    //ImageView.html로 이동
    @GetMapping("/board/{id}")
    public String showImageView(Model model, @PathVariable("id") Long id){
        Optional<Board> board=boardRepository.findById(id); // id로 객체를 찾음
        Board boardEntity=board.get();
        model.addAttribute("board",boardEntity);
        return "ImageView";
    }

    //데이터 삭제
    @PostMapping("/remove")
    public String remove(@RequestParam(value = "id") Long id){
        boardService.deleteBoard(id);
        return "redirect:/";    //삭제 후 index.html로 리다이렉트
    }
}
