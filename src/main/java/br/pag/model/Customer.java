package br.pag.model;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author brunner.klueger
 */
@Data
@Entity
@Table(name = "CLIENTE")
public class Customer extends AbstractEntity {

    @Size(max = 65, message = "bean.custormer.name.maxSize")
    @Column(name = "NOME")
    @NotEmpty(message = "{bean.custormer.name.NotEmpty}")
    private String name;

    @Size(max = 65, message = "Campo [cpf] não pode ser maior que 14 caracteres")
    @Column(name = "CPF")
    @NotEmpty(message = "O campo [cpf] do Cliente é obrigatorio")
    private String socialSecurityNumber;

    @Column(name = "DATA_NASCIMENTO")
    @NotNull(message = "O campo [data de nascimento] do Cliente é obrigatório")
    private LocalDateTime birthDate;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "customer")
    private Set<Order> orders;

    public Customer() {
    }

}
