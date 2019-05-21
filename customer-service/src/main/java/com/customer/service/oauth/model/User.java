package com.customer.service.oauth.model;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document(value = "User")
@TypeAlias(value = "User")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
}

