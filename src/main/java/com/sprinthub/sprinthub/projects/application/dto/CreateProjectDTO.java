package com.sprinthub.sprinthub.projects.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateProjectDTO {
    private String name;
    private String description;
}
