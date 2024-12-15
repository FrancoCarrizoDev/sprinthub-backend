package com.sprinthub.sprinthub.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class ProjectDTO {
    private UUID id;
    private String name;
    private String description;

}
