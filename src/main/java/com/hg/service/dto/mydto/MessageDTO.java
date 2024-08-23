package com.hg.service.dto.mydto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageDTO implements Serializable {

    private String severity;
    private String summary;
    private String detail;
    private Object id;
    private String key;
    private int life;
    private boolean sticky;
    private boolean closable;
    private Object data;
    private String icon;
    private String contentStyleClass;
    private String styleClass;
    private String closeIcon;
}
