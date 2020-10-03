package br.com.pag.queroserpaguer.domain;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *  Cliente.
 *  
 *  @author igor.nunes
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 100,message = "O nome deve conter no máximo 100 caracteres.")
    @Column(name = "nome",nullable = false)
    private String nome;

    @Size(min = 11, max = 11, message = "o cpf deve conter 11 caracteres")
    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;

    @NotNull
    @Column(name = "data_nascimento")
    private ZonedDateTime dataNascimento;
    
    
    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Pedido> pedidos;

  
}
