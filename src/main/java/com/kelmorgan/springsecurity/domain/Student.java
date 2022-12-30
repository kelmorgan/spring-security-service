package com.kelmorgan.springsecurity.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {

    private int id;
    private String name;
}
