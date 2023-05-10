package com.dygticky.cnoteserver.service;

import com.dygticky.cnoteserver.mapper.NoteMapper;
import com.dygticky.cnoteserver.model.Note;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class NoteService {

    @Resource
    NoteMapper noteMapper;

    public List<Note> getFullIndex(int uid) {
        return noteMapper.getFullIndex(uid);
    }

    public List<Note> getNeededNotes(int uid, List<Integer> ids) {
        return noteMapper.getNeededNotes(uid, ids);
    }

    public void updateNotes(int uid,List<Note> syncNotes) {
        noteMapper.updateNotes(uid,syncNotes);
    }

    public void insertNotes(int uid,List<Note> syncNotes) {
        noteMapper.insertNotes(uid,syncNotes);
    }

    public void deleteNotes(int uid, List<Integer> syncNotes) {
        noteMapper.deleteNotes(uid, syncNotes);
    }

    public Note getShareNote(int uid, int id) {
        return noteMapper.queryByIdWithUid(uid, id);
    }

}
