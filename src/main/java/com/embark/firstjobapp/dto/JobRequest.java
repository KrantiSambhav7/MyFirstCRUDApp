package com.embark.firstjobapp.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequest {
    private String title;
    private String description;
    private Integer minSalary;
    private Integer maxSalary;
    private String location;
    private Long companyId;
}
