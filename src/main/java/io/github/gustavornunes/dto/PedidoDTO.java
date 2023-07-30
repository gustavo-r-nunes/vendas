package io.github.gustavornunes.dto;

import io.github.gustavornunes.model.ItemPedido;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Integer clienteId;
    private BigDecimal total;
    private List<ItemPedidoDTO> itens;
}
