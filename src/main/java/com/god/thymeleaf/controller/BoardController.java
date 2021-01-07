package com.god.thymeleaf.controller;

import com.god.thymeleaf.repository.BoardRepository;
import com.god.thymeleaf.service.BoardService;
import com.god.thymeleaf.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.god.thymeleaf.model.Board;

import java.util.List;
import javax.validation.Valid;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String list(Model model,@PageableDefault( size=2) Pageable pageable,
                      @RequestParam(required = false, defaultValue = "") String searchText)
    {
//        Page<Board> boards=boardRepository.findAll(pageable);
        Page<Board> boards=boardRepository.findByTitleContainingOrContentContaining(searchText, searchText,pageable);

        int startPage=Math.max(1, boards.getPageable().getPageNumber()-4);
        int endPage=Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber()+4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return  "board/list";
    }

    @GetMapping("/form")
    public String getForm(Model model, @RequestParam(required = false) Long id){
        if(id==null) {
            model.addAttribute("board", new Board());
        } else {
            Board board= boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }
    @PostMapping("/form")
    public String boardSubmit(@Valid Board board, BindingResult bindingResult,
                              Authentication authentication) {

        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()) {
            return "board/form";
        }

        //두가지 방법으로 authentication 조회 가능
//      String userName=SecurityContextHolder.getContext().getAuthentication().getName(); //userName조회의 다른 방법
        String userName=authentication.getName();
//      board.setUser(userName);
        boardService.save(userName, board);
        return "redirect:/board/list";
    }

//    @PostMapping("/form")
//    public String boardSubmit(@ModelAttribute Board board, Model model) {
//        boardRepository.save(board);
//        return "redirect:/board/list";
//    }

}

