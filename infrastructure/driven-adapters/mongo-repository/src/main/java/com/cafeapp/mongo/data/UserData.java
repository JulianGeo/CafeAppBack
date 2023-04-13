package com.cafeapp.mongo.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @Id
    private String id =UUID.randomUUID().toString().substring(0,10);
    private String idNum;
    //@NotBlank(message="Empty field error")
    //@NotNull(message ="name is required")
    //@Pattern(regexp="^[A-Z][a-z]*$", message="name format is required")
    private String name;
    private String lastname;
    @NotBlank(message="Empty field error")
    @NotNull(message ="name is required")
    private String email;
    private String password;
}
