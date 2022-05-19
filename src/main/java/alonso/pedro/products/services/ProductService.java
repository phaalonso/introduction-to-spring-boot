package alonso.pedro.products.services;

import alonso.pedro.products.dtos.ProductDto;
import alonso.pedro.products.exceptions.BusinessException;
import alonso.pedro.products.exceptions.NotFoundException;
import alonso.pedro.products.models.ProductModel;
import alonso.pedro.products.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static alonso.pedro.products.Utils.isNotNullOrEmpty;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductModel> listProduct() {
        return productRepository.findAll();
    }

    public ProductModel findProductById(Long idProduto) {
        var produto = productRepository.findById(idProduto);

        if (produto.isEmpty()) {
            throw new NotFoundException("produto com o id " + idProduto + " não existe");
        }

        return produto.get();
    }

    public List<ProductModel> findProductWithName(String nome) {
//        Não ignora maiusculas e minusculas (poderia mudar a query, mas queria dar um exemplo de stream e filter)
//        try {
//            return productRepository.findByNameContaining(nome);
//        } catch (InvalidDataAccessApiUsageException ex) {
//            return new ArrayList<>();
//        }

        return productRepository.findAll().stream()
                .filter(productModel -> productModel.getName().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public ProductModel createProduct(ProductDto productDto) {
        if (productDto.getId() != null) {
            throw new BusinessException("product id is only assigned after insert");
        }

        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);

        if (productModel.getPrice() < 0) {
            throw new RuntimeException("Preço inválido");
        }

        productRepository.save(productModel);

        return productModel;
    }

    @Transactional
    public void updateProduct(Long idProduto, String name, Double price, String description) {
        /* Nesse método não é necessário salvar o objeto. Pois ele é anotaco com a tag @Transactional.
          Fazendo com que todas as alterações nos objetos desse método sejam refletidos na base de dados */
        var product = productRepository.findById(idProduto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        if (isNotNullOrEmpty(name) && !name.equals(product.getName())) {
            product.setName(name);
        }

        if (isNotNullOrEmpty(description) && !description.equals(product.getDescription())) {
            product.setDescription(product.getDescription());
        }

        if (price != null && price >= 0 && !price.equals(product.getPrice())) {
            product.setPrice(price);
        }
    }

    public void deleteProduct(Long idProduto) {
        boolean exists = productRepository.existsById(idProduto);

        if (!exists) {
            throw new IllegalStateException(
                    "product with id " + idProduto + " does not exist"
            );
        }

        productRepository.deleteById(idProduto);
    }
}
