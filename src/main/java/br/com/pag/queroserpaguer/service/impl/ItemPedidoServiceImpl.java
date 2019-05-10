package br.com.pag.queroserpaguer.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pag.queroserpaguer.domain.ItemPedido;
import br.com.pag.queroserpaguer.repository.ItemPedidoRepository;
import br.com.pag.queroserpaguer.service.ItemPedidoService;

/**
 * Service Implementation for managing {@link ItemPedido}.
 */
@Service
@Transactional
public class ItemPedidoServiceImpl implements ItemPedidoService {

    private final Logger log = LoggerFactory.getLogger(ItemPedidoServiceImpl.class);

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoServiceImpl(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }
   
    @Override
    public ItemPedido save(ItemPedido itemPedido) {
        log.info("Salvando  ItemPedido : {}", itemPedido);
        return itemPedidoRepository.save(itemPedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemPedido> findAll() {
        log.info("Obtendo lista de  ItemPedidos");
        return itemPedidoRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ItemPedido> findById(Long id) {
        log.info("get ItemPedido : {}", id);
        return itemPedidoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.info("deletando o ItemPedido : {}", id);
        itemPedidoRepository.deleteById(id);
    }
    
    @Override
    public List<ItemPedido> getItensByIdPedido(Long idPedido){
    	return itemPedidoRepository.getItensByIdPedido(idPedido);
    }
}
