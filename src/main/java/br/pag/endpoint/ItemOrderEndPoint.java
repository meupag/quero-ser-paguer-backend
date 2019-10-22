package br.pag.endpoint;

import br.pag.error.ResponseException;
import br.pag.message.Translator;
import br.pag.model.ItemOrder;
import br.pag.model.Order;
import br.pag.model.Product;
import br.pag.repository.ItemOrderRepository;
import br.pag.repository.OrderRepository;
import br.pag.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe de serviços relativos a entidade PedidoItem.
 *
 * @author brunner.klueger
 */
@RestController
@RequestMapping("pedido-itens")
@Api(tags = "ItemOrder-End-Point")
public class ItemOrderEndPoint {

    private final ItemOrderRepository itemOrderDAO;
    private final ProductRepository productDAO;
    private final OrderRepository orderDAO;

    @Autowired
    public ItemOrderEndPoint(ItemOrderRepository itemOrderDAO,
            ProductRepository productDAO,
            OrderRepository orderDAO) {
        this.itemOrderDAO = itemOrderDAO;
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
    }

    /**
     * Busca todos os Pedido-Itens cadastrados.
     *
     * @return lista de pedido-itens
     */
    @GetMapping
    @ApiOperation(value = "Busca todos os Pedido-Itens cadastrados no sistema", response = List.class, produces = "application/json")
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(itemOrderDAO.findAll(pageable), HttpStatus.OK);
    }

    /**
     * Consulta um Pedido-Item por seu ID.
     *
     * @param id id do pedido-item
     * @return pedido-item encontrado
     */
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca um Pedido a partir do seu ID", response = ItemOrder.class, produces = "application/json")
    public ResponseEntity<?> getOrderById(
            @ApiParam(value = "ID do Pedido que está sendo consultado no banco de dados", required = true) @PathVariable("id") Long id) {
        //verifica a existencia do pedido
        verifyIfOrderExists(id);
        return new ResponseEntity<>(itemOrderDAO.findById(id), HttpStatus.OK);
    }

    /**
     * Deleta um pedido a partir de seu ID.
     *
     * @param id id do pedido
     * @return status de sucesso ou falha ao deletar
     */
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deleta um Pedido-Item no banco de dados a partir do seu ID", produces = "application/json")
    public ResponseEntity<?> delete(
            @ApiParam(value = "ID do Pedido-Item que está sendo deletado no banco de dados", required = true) @PathVariable("id") Long id) {

        //verifica a existencia do pedido
        verifyIfOrderExists(id);
        itemOrderDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Cadastra um pedido no banco de dados.
     *
     * @param orderId id do cliente que será associado ao pedido-item
     * @param productId id do produto que será associado ao pedido-item
     * @param itemOrder pedido-item a ser cadastrado
     * @return pedido-item cadastrado
     */
    @PostMapping(path = "/{orderId}/{productId}")
    @ApiOperation(value = "Cadastra um Pedido-Item no banco de dados", response = ItemOrder.class, produces = "application/json")
    public ResponseEntity<?> save(
            @ApiParam(value = "Id do Pedido que será associado ao Pedido-Item", required = true) @PathVariable("orderId") Long orderId,
            @ApiParam(value = "Id do Produto que será associado ao Pedido-Item", required = true) @PathVariable("productId") Long productId,
            @ApiParam(value = "Objeto Order(Pedido) que será cadastrado no banco de dados", required = true) @Valid @RequestBody ItemOrder itemOrder) {

        //ID de cliente a ser associado não pode ser nulo
        if (Objects.isNull(orderId)) {
            throw new ResponseException(Translator.toLocale("ItemOrder.Save.IdOrder.NotNull"));
        }
        //ID de produto a ser associado não pode ser nulo
        if (Objects.isNull(productId)) {
            throw new ResponseException(Translator.toLocale("ItemOrder.Save.IdProduct.NotNull"));
        }
        //não é possivel cadastrar um pedido que já possui ID
        if (Objects.nonNull(itemOrder.getId())) {
            throw new ResponseException(Translator.toLocale("ItemOrder.Save.IdItemOrder.NotNull"));
        }

        Order order = orderDAO.findById(orderId)
                .orElseThrow(() -> new ResponseException(Translator.toLocale("ItemOrder.Save.Order.Id.NotFound")));

        Product product = productDAO.findById(orderId)
                .orElseThrow(() -> new ResponseException(Translator.toLocale("ItemOrder.Save.Product.Id.NotFound")));

        itemOrder.setOrder(order);
        itemOrder.setProduct(product);
        itemOrder = itemOrderDAO.save(itemOrder);
        return new ResponseEntity<>(itemOrder, HttpStatus.OK);
    }

    /**
     * Atualiza um pedido-item.
     *
     * @param itemOrder pedido-item a ser atualizado
     * @return sucesso ou falha
     */
    @PutMapping
    @ApiOperation(value = "Atualiza um Pedido-Item no banco de dados", response = ItemOrder.class, produces = "application/json")
    public ResponseEntity<?> update(
            @ApiParam(value = "Objeto ItemOrder(Pedido-Item) que será atualizado no banco de dados", required = true) @Valid @RequestBody ItemOrder itemOrder) {
        Long idOrder = itemOrder.getId();
        if (Objects.isNull(idOrder)) {//se não possui ID
            throw new ResponseException(Translator.toLocale("ItemOrder.Update.Id.IsNull"));
        }
        //verifica a existencia do pedido
        verifyIfOrderExists(idOrder);
        return itemOrderDAO.findById(idOrder)
                .map(updatedItemOrder -> {
                    updatedItemOrder.setPrice(itemOrder.getPrice());
                    updatedItemOrder.setQuantity(itemOrder.getQuantity());
                    return new ResponseEntity<>(itemOrderDAO.save(updatedItemOrder), HttpStatus.OK);
                }).orElseThrow(() -> new ResponseException(Translator.toLocale("ItemOrder.Id.NotFound")));
    }

    /**
     * Verifica se um Pedido-Item existe.
     *
     * @param id id do pedido-item
     */
    private void verifyIfOrderExists(Long id) {
        if (Objects.isNull(id) || Objects.isNull(itemOrderDAO.findById(id).orElse(null))) {
            throw new ResponseException(Translator.toLocale("ItemOrder.Id.NotFound", id));
        }
    }
}
