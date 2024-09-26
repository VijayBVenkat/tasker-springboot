package com.vijay.tasker.DTO;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskDTO {

    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String userName;

}

