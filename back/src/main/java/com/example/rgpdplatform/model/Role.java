package com.example.rgpdplatform.model;

import com.example.rgpdplatform.enums.Role_enum;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "roles")
public class Role extends AbstractDocument {

    private String roleName;

    @Override
    public String toString() {
        return "Role{" +
                "role='" + roleName + '\'' +
                '}';
    }
}
