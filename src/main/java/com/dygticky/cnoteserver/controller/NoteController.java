package com.dygticky.cnoteserver.controller;


import com.dygticky.cnoteserver.model.Note;
import com.dygticky.cnoteserver.model.Resp;
import com.dygticky.cnoteserver.model.RespCode;
import com.dygticky.cnoteserver.model.SyncRequest;
import com.dygticky.cnoteserver.service.NoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Resource
    NoteService noteService;

    @Resource
    ObjectMapper mapper;

    @PostMapping("/index")
    public Resp getAllIndexes(@RequestBody SyncRequest sync) {
        if (sync.getUid() == null) {
            return Resp.builder()
                    .code(RespCode.FAIL_CLIENT.getValue())
                    .msg("获取失败,请携带用户ID")
                    .data(null)
                    .build();
        }

        List<Note> fullIndex = noteService.getFullIndex(sync.getUid());

        return Resp.builder()
                .code(RespCode.SUCCESS.getValue())
                .msg("获取索引成功")
                .data(fullIndex)
                .build();
    }


    @PostMapping("need")
    public Resp syncNoteGetNeed(@RequestBody SyncRequest sync) {

        try {
            List<Note> neededNotes = noteService.getNeededNotes(sync.getUid(), sync.getDeleteData());
            return Resp.builder()
                    .code(RespCode.SUCCESS.getValue())
                    .msg("同步获取成功")
                    .data(neededNotes)
                    .build();
        } catch (Exception e) {
            return Resp.builder()
                    .code(RespCode.FAIL_SERVER.getValue())
                    .msg("同步获取失败," + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @PostMapping("/update")
    public Resp syncNotesUpdate(@RequestBody SyncRequest sync) throws JsonProcessingException {
        String s = mapper.writeValueAsString(sync);
        log.info("同步添加{}", s);
        log.info("收到同步请求 By {}, 内容： {}", sync.getUid(), sync.getUpdateData());
        if (sync.getUpdateData() == null || sync.getUpdateData().size() == 0) {
            return Resp.builder()
                    .code(RespCode.SUCCESS.getValue())
                    .msg("没有什么需要在云修改的")
                    .data(null)
                    .build();
        }

        try {
            noteService.updateNotes(sync.getUid(), sync.getUpdateData());
        } catch (Exception e) {
            return Resp.builder()
                    .code(RespCode.FAIL_SERVER.getValue())
                    .msg("同步修改失败," + e.getMessage())
                    .data(null)
                    .build();
        }
        return Resp.builder()
                .code(RespCode.SUCCESS.getValue())
                .msg("同步修改成功")
                .data(null)
                .build();
    }

    @PostMapping("/insert")
    public Resp syncNotesInsert(@RequestBody SyncRequest sync) {
        log.info("收到添加请求 By {}, 内容： {}", sync.getUid(), sync.getInsertData());
        if (sync.getInsertData() == null || sync.getInsertData().size() == 0) {
            return Resp.builder()
                    .code(RespCode.SUCCESS.getValue())
                    .msg("没有什么需要向云添加的")
                    .data(null)
                    .build();
        }
        try {
            noteService.insertNotes(sync.getUid(), sync.getInsertData());
        } catch (Exception e) {
            return Resp.builder()
                    .code(RespCode.FAIL_SERVER.getValue())
                    .msg("同步添加失败," + e.getMessage())
                    .data(null)
                    .build();
        }
        return Resp.builder()
                .code(RespCode.SUCCESS.getValue())
                .msg("同步添加成功")
                .data(null)
                .build();
    }

    @PostMapping("/delete")
    public Resp syncNotesDelete(@RequestBody SyncRequest sync) {
        log.info("收到删除请求 By {}, 内容： {}", sync.getUid(), sync.getDeleteData());
        if (sync.getDeleteData() == null || sync.getDeleteData().size() == 0) {
            return Resp.builder()
                    .code(RespCode.SUCCESS.getValue())
                    .msg("没有什么需要从云删除的")
                    .data(null)
                    .build();
        }

        try {
            noteService.deleteNotes(sync.getUid(), sync.getDeleteData());
        } catch (Exception e) {
            return Resp.builder()
                    .code(RespCode.FAIL_SERVER.getValue())
                    .msg("同步删除失败," + e.getMessage())
                    .data(null)
                    .build();
        }
        return Resp.builder()
                .code(RespCode.SUCCESS.getValue())
                .msg("同步删除成功")
                .data(null)
                .build();
    }





}
