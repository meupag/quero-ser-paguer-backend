package com.store.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.store.message.ValidatorFieldMessage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
@JsonDeserialize(builder = Customer.CustomerBuilder.class)
@Builder(builderClassName = "CustomerBuilder", toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cliente")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = ValidatorFieldMessage.NameEmpty)
    @Column(name = "nome", length = 100)
    private String name;

    @Pattern(regexp = "\\d{11}", message = ValidatorFieldMessage.CpfInvalid)
    @NotEmpty(message = ValidatorFieldMessage.CpfEmpty)
    @Column(unique = true, columnDefinition = "CHAR(11)")
    private String cpf;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    @Column(name = "data_nascimento")
    private LocalDate birthDate;

    @ToString.Exclude
    @JsonManagedReference(value = "customer")
    @OneToMany(targetEntity = Order.class, mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orderList;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_conta")
    private AccountStatus accountStatus;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CustomerBuilder {
    }

}
