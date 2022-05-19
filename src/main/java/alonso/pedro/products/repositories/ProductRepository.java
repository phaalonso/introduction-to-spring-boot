package alonso.pedro.products.repositories;

import alonso.pedro.products.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
//    List<ProductModel> findByNameContaining(String name);
}
