package alonso.pedro.products.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroDTO {
    private String campo;
    private String mensagem;
}
