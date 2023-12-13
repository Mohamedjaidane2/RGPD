package com.example.rgpdplatform.dto;
import com.example.rgpdplatform.enums.ActivityArea_enum;
import com.example.rgpdplatform.enums.Workforce_enum;
import com.example.rgpdplatform.model.Organisation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrganisationDto {

    private String name;

    private ActivityArea_enum activityArea;

    private String expertiseField;

    private Workforce_enum workforce;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date created;

    public static OrganisationDto customMapping(Organisation organisation){
        return OrganisationDto.builder()
                .name(organisation.getName())
                .workforce(organisation.getWorkforce())
                .created(organisation.getCreationDate())
                .activityArea(organisation.getActivityArea())
                .expertiseField(organisation.getExpertiseField())
                .build();
    }
}
