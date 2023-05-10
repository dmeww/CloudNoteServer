package com.dygticky.cnoteserver.controller;

import com.dygticky.cnoteserver.model.Note;
import com.dygticky.cnoteserver.service.NoteService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ShareController {

    @Resource
    NoteService noteService;


    static Note notFoundNote = new Note();


    static {
        notFoundNote.setTitle("错误");
        notFoundNote.setContent("没有找到这篇笔记");
    }



    @GetMapping("/api/note/share/{uid}/{id}")
    public String shareNote(Model model, @PathVariable("uid") Integer uid, @PathVariable("id") Integer id) {
        System.out.println("Share " + uid + " <-> " + id);


        Note shareNote = noteService.getShareNote(uid, id);

        if (shareNote != null) {
            model.addAttribute("note", shareNote);
        } else model.addAttribute("note", notFoundNote);

        return "share";
    }


}
