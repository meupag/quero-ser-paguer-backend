package br.com.pag.queroserpaguer.repository;


import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import br.com.pag.queroserpaguer.domain.Cliente;


/**
 * Spring Data  repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
