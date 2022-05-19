package alonso.pedro.products.dtos;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDto {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @DecimalMin("0.00")
    private Double price;
    @NotNull
    @NotBlank
    private String description;
}
