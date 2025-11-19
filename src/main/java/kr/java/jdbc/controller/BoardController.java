package kr.java.jdbc.controller;

import kr.java.jdbc.repository.BoardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class BoardController {
    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("boards", boardRepository.findAll());
        return "index";
    }

    @PostMapping
    public String form(
            @RequestParam("content") String content) {
       boardRepository.save(content);
       return "redirect:/";
    }
}
