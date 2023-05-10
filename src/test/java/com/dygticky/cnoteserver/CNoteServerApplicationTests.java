package com.dygticky.cnoteserver;

import com.dygticky.cnoteserver.mapper.NoteMapper;
import com.dygticky.cnoteserver.mapper.UserMapper;
import com.dygticky.cnoteserver.model.Note;
import com.dygticky.cnoteserver.model.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class CNoteServerApplicationTests {


    @Resource
    NoteMapper noteMapper;

    List<Note> fullIndex;
    List<Note> neededNotes;
    List<Integer> neededIndex;

    //    @Test
    void testGetNotes() {
        fullIndex = noteMapper.getFullIndex(1);

        System.out.println(fullIndex);
        neededIndex = new ArrayList<>();

        fullIndex.forEach(t -> {
            neededIndex.add(t.getId());
        });

        neededNotes = noteMapper.getNeededNotes(1, neededIndex);

        System.out.println(neededNotes);

    }


    //    @Test
    void testInsertNotes() {


        List<Note> newNotes = new ArrayList<>();
        Note build = Note.builder()
                .id(1231)
                .uid(1)
                .title("M1")
                .content("C!")
                .category("CA1")
                .createDate(System.currentTimeMillis())
                .lastUpdate(System.currentTimeMillis())
                .marked(false)
                .status(0)
                .build();
        newNotes.add(build);
        build.setId(1232);
        build.setTitle("M2");
        newNotes.add(build);

        noteMapper.insertNotes(1, newNotes);

    }

    //    @Test
    void testUpdateNotes() {
        testGetNotes();
        Note note = neededNotes.get(0);
        note.setCategory("Edit By DYG With SpringBoot");

        neededNotes.remove(0);
        neededNotes.add(note);

        note = neededNotes.get(0);
        note.setCategory("Edit By DYG With SpringBoot");
        neededNotes.remove(0);
        neededNotes.add(note);

        noteMapper.updateNotes(1, neededNotes);

    }

    //    @Test
    void testDeleteNotes() {
        testGetNotes();

        noteMapper.deleteNotes(1, neededIndex);


    }

    @Resource
    UserMapper userMapper;

    @Test
    void testUserPart() {

        User user = new User();
        user.setUsername("dyg");
        user.setPassword("pass");


        User userLogin = userMapper.queryByUserName(user.getUsername());
        userLogin.setAlias("EditByIDEA");

        userMapper.updateUser(userLogin.getUid(), "alias", userLogin.getAlias());

        log.info("修改成功 {}", userMapper.queryByUserName(userLogin.getUsername()));

    }


}
