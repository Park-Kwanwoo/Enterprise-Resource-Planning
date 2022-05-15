package com.erp.board.controller;


import com.erp.board.controller.dto.BoardRequestDto;
import com.erp.board.controller.dto.BoardResponseDto;
import com.erp.board.domain.Board;
import com.erp.board.service.BoardService;
import com.erp.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String boardList(Model model) {

        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "/board/boardList";
    }

    @GetMapping("/{boardId}")
    public String board(@PathVariable Long boardId, Model model) {

        BoardResponseDto board = boardService.findById(boardId);
        model.addAttribute("board", board);
        return "board/board";
    }

    @GetMapping("/save")
    public String saveBoard(@ModelAttribute("board") BoardRequestDto boardRequestDto) {
        return "board/save_form";
    }

    @PostMapping("/save")
    public String saveBoard(BoardRequestDto boardRequestDto,
                            @AuthenticationPrincipal Member member,
                            RedirectAttributes redirectAttributes) {
        log.info("member = {}", member.toString());
        boardRequestDto.setMember(member);
        Long saveBoardId = boardService.save(boardRequestDto);
        redirectAttributes.addAttribute("boardId", saveBoardId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/board/{boardId}";
    }
}
