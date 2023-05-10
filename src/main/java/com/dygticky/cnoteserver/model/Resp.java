package com.dygticky.cnoteserver.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resp {

    int code;
    String msg;
    Object data;


}
