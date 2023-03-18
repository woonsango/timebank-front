package com.capstone.timepay.controller.board;

import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import com.capstone.timepay.service.board.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @GetMapping("/free")
    public String freeBoardList(Model model)
    {
        List<FreeBoardDTO> freeBoardDTOList = freeBoardService.freeBoardFindAll();
        model.addAttribute("freeBoardList", freeBoardDTOList);
        return "freeBoardList";
    }

    @GetMapping("/free/save")
    public String saveForm()
    {
        return "freeBoardSave";
    }

    //  보내려는 데이터를 DTO를 통해 보낸다.
    @PostMapping("/free/save")
    public String save(@ModelAttribute FreeBoardDTO freeBoardDTO)
    {
        freeBoardService.save(freeBoardDTO);
        System.out.println("freeBoardDTO = " + freeBoardDTO);
        return "redirect:/board/free";
    }
}
