package com.hg.service.dto.mydto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LandLordCommunicationDTO implements Serializable {

    private String name;
    private String surenName;
    private String phone;
    private String message;
}
