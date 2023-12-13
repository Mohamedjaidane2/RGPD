package com.example.rgpdplatform.model;

import com.example.rgpdplatform.enums.Profile_enum;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;



@Data
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "guests")
public class Guest extends AbstractDocument {
    private String firstName;

    private String lastName;

    private String function;

    private String telephone;

    private String email;

    private String expertiseField; //FIXME

    private Profile_enum profile;

    @DocumentReference(lazy = true)
    private Organisation organisation;

    @Override
    public String toString() {
        return "Guest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", function='" + function + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", expertiseField='" + expertiseField + '\'' +
                ", profile=" + profile +
                ", organisation=" + organisation +
                '}';
    }
}
