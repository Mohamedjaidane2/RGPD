package com.example.rgpdplatform.dto.requests.create;

import com.example.rgpdplatform.enums.ActivityArea_enum;
import com.example.rgpdplatform.enums.Workforce_enum;
import com.example.rgpdplatform.model.Guest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrganisationNewDto {

    private String name ;

    private ActivityArea_enum activityArea;

    private String expertiseField;

    private Workforce_enum workforce;





}
