package alonso.pedro.products.controllers;

import alonso.pedro.products.dtos.ProductDto;
import alonso.pedro.products.models.ProductModel;
import alonso.pedro.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

import static alonso.pedro.products.Utils.isNotNullOrEmpty;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {
    private final ProductService productService;


    @GetMapping("/product")
    public ResponseEntity<List<ProductModel>> listProducts(
            @PathParam("nome") String nome
    ) {
        if (isNotNullOrEmpty(nome)) {
            return ResponseEntity
                    .ok(productService.findProductWithName(nome));
        }

        return ResponseEntity
                .ok(productService.listProduct());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable("id") Long idProduto) {
        var produto = productService.findProductById(idProduto);

        return ResponseEntity.ok(produto);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductModel> creatingProduct(@RequestBody @Valid ProductDto productDto) {
        var productModel = productService.createProduct(productDto);

        return ResponseEntity.accepted()
                .body(productModel);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable("id") Long idProduto,
                                                      @PathParam("name") String name,
                                                      @PathParam("price") Double price,
                                                      @PathParam("description") String description) {
        productService.updateProduct(idProduto, name, price, description);

        return ResponseEntity.accepted()
                .build();
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable("id") Long idProduto) {
        productService.deleteProduct(idProduto);
    }


}
