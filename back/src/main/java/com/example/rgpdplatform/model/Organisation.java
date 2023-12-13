package com.example.rgpdplatform.model;

import com.example.rgpdplatform.enums.ActivityArea_enum;
import com.example.rgpdplatform.enums.Workforce_enum;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "organisations")
public class Organisation extends AbstractDocument {


    private String name ;

    private ActivityArea_enum activityArea;

    private String expertiseField;

    private Workforce_enum workforce;

    @Override
    public String toString() {
        return "Organisation{" +
                "name=" + name +
                "activityArea=" + activityArea +
                ", expertiseField='" + expertiseField + '\'' +
                ", workforce=" + workforce +
                '}';
    }
}