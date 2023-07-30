package io.github.gustavornunes.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {
    private Integer produtoId;
    private Integer quantidade;
}
