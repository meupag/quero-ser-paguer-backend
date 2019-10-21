package br.pag.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author brunner.klueger
 */
@Entity
@Table(name = "CLIENTE")
@Getter
@Setter
@ApiModel(description = "Todos os detalhes de um Customer(Cliente) ")
public class Customer extends AbstractEntity {

    @Size(max = 65, message = "bean.custormer.name.maxSize")
    @Column(name = "NOME")
    @NotEmpty(message = "{bean.custormer.name.NotEmpty}")
    @ApiModelProperty(notes = "Nome do Cliente")
    private String name;

    @Column(name = "CPF")
    @NotEmpty(message = "{bean.custormer.socialSecurityNumber.NotEmpty}")
    @ApiModelProperty(notes = "CPF do Cliente")
    @Pattern(regexp = "[\\d]{11}", message = "{bean.custormer.socialSecurityNumber.onlyNumber}")
    private String socialSecurityNumber;

    @Column(name = "DATA_NASCIMENTO")
    @NotNull(message = "{bean.custormer.birthDate.NotNull}")
    @ApiModelProperty(notes = "Data de nascimento do Cliente")
    private LocalDateTime birthDate;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @Column(nullable = true)
    @ApiModelProperty(notes = "Lista de Pedidos do Cliente")
    private Set<Order> orders = new HashSet<>();

    public Customer() {
    }

}
