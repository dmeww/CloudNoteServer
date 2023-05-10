package com.dygticky.cnoteserver.mapper;

import com.dygticky.cnoteserver.model.Note;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface NoteMapper {


    List<Note> getFullIndex(int uid);

    List<Note> getNeededNotes(int uid,List<Integer> list);

    void updateNotes(int uid,List<Note> list);

    void insertNotes(int uid,List<Note> list);

    void deleteNotes(int uid,List<Integer> list);

    Note queryByIdWithUid(int uid,int id);


}
