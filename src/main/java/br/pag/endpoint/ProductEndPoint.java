package br.pag.endpoint;

import br.pag.error.ResponseException;
import br.pag.message.Translator;
import br.pag.model.Product;
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
 * Classe de serviços relativos a entidade Produto.
 *
 * @author brunner.klueger
 */
@RestController
@RequestMapping("produtos")
@Api(tags = "Product-End-Point")
public class ProductEndPoint {

    private final ProductRepository productDAO;

    @Autowired
    public ProductEndPoint(ProductRepository productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Busca todos os produtos cadastrados.
     *
     * @return lista de produtos
     */
    @GetMapping
    @ApiOperation(value = "Busca todos os Produtos cadastrados no sistema", response = List.class, produces = "application/json")
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(productDAO.findAll(pageable), HttpStatus.OK);
    }

    /**
     * Consulta um produto por seu ID.
     *
     * @param id id do produto
     * @return produto encontrado
     */
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca um Produto a partir do seu ID", response = Product.class, produces = "application/json")
    public ResponseEntity<?> getProductById(
            @ApiParam(value = "ID do Produto que está sendo consultado no banco de dados", required = true) @PathVariable("id") Long id) {
        //verifica a existencia do produto
        verifyIfProductExists(id);
        return new ResponseEntity<>(productDAO.findById(id), HttpStatus.OK);
    }

    /**
     * Deleta um produto a partir de seu ID.
     *
     * @param id id do produto
     * @return status de sucesso ou falha ao deletar
     */
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deleta um Produto no banco de dados a partir do seu ID", produces = "application/json")
    public ResponseEntity<?> delete(
            @ApiParam(value = "ID do Produto que está sendo consultado no banco de dados", required = true) @PathVariable("id") Long id) {

        //verifica a existencia do produto
        verifyIfProductExists(id);
        productDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Cadastra um produto no banco de dados.
     *
     * @param newProduct produto a ser cadastrado
     * @return produto cadastrado
     */
    @PostMapping
    @ApiOperation(value = "Cadastra um Produto no banco de dados", response = Product.class, produces = "application/json")
    public ResponseEntity<?> save(
            @ApiParam(value = "Objeto Product(Produto) que será cadastrado no banco de dados", required = true) @Valid @RequestBody Product newProduct) {
        //não é possivel cadastrar um produto que já possui ID
        if (newProduct.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        newProduct = productDAO.save(newProduct);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    /**
     * Atualiza um produto.
     *
     * @param updatedProduct produto a ser atualizado
     * @return sucesso ou falha
     */
    @PutMapping
    @ApiOperation(value = "Atualiza um Produto no banco de dados", response = Product.class, produces = "application/json")
    public ResponseEntity<?> update(
            @ApiParam(value = "Objeto Product(Produto) que será atualizado no banco de dados", required = true) @Valid @RequestBody Product updatedProduct) {
        //verifica se o objeto é nulo
        Long idProduct = updatedProduct.getId();
        if (idProduct != null) {//se possui ID
            //verifica a existencia do produto
            verifyIfProductExists(idProduct);
            updatedProduct = productDAO.save(updatedProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Verifica se um Cliente existe.
     *
     * @param id id do produto
     */
    private void verifyIfProductExists(Long id) {
        if (Objects.isNull(id) || Objects.isNull(productDAO.findById(id).orElse(null))) {
            throw new ResponseException(Translator.toLocale("error.notFound", id));
        }
    }
}
