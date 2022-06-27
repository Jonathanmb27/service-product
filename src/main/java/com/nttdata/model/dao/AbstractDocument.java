package com.nttdata.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AbstractDocument implements Serializable {

    @Id
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int state;
    public AbstractDocument(){
        createdAt= LocalDateTime.now();
        modifiedAt= LocalDateTime.now();
        state=1;
    }
}
