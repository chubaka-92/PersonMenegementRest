package com.example.personmenegementrest.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {

    protected String id;
    protected String name;
    protected String age;
    protected String email;
    protected String position;
    protected String salary;
    protected boolean valid = true;
    protected String experience;

}
