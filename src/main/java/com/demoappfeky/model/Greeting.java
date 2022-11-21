package com.demoappfeky.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

//@Entity
@Getter
@Setter
public class Greeting {

    private long id;
    private String content;
}
