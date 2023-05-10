package com.dygticky.cnoteserver.model;

import lombok.Data;

import java.util.List;

@Data
public class SyncRequest {

    Integer uid;

    List<Note> updateData;

    List<Integer> deleteData;

    List<Note> insertData;


}
