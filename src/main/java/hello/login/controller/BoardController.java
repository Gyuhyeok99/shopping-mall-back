package hello.login.controller;

import hello.login.domain.Board;
import hello.login.repository.BoardRepository;
import hello.login.domain.Member;
import hello.login.web.SessionConst;
import hello.login.web.border.BoardForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;

    @GetMapping
    public String boards(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    @GetMapping("/{boardId}")
    public String board(@PathVariable long boardId, Model model)  {
        Board board = boardRepository.findById(boardId);
        //조회수 1 증가
        board.setView(board.getView() + 1);
        model.addAttribute("board", board);
        return "board/board";
    }

    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("board",new Board());
        return "board/addBoard";
    }

    @PostMapping("/add")
    public String addBoard(@Validated @ModelAttribute("board") BoardForm form, BindingResult bindingResult
            , RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult );
            return "board/addBoard";
        }
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setAuthor(member.getName());
        board.setCreateDateTime(LocalDateTime.now());

        Board saveBoard = boardRepository.save(board);
        redirectAttributes.addAttribute("boardId", saveBoard.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/board/{boardId}";
    }

    @GetMapping("/{boardId}/edit")
    public String editBoard(@PathVariable Long boardId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Board board = boardRepository.findById(boardId);
        model.addAttribute("board", board);



        //로그인한 사람과 작성자가 다르다면
        if (!(board.getAuthor().equals(member.getName()))) {
            return "redirect:/board/{boardId}";
        }

        return "board/editBoard";
    }

    @PostMapping("/{boardId}/edit")
    public String edit(@PathVariable Long boardId,
                       @Validated @ModelAttribute("board") BoardForm form, BindingResult bindingResult) {

        //예외 발생하면 수정폼으로 돌아가기
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "board/editBoard";
        }

        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setCreateDateTime(LocalDateTime.now());

        boardRepository.update(boardId, board);
        return "redirect:/board/{boardId}";
    }

}
