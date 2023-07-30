package io.github.gustavornunes.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacaoPedidoDTO {

    private String descricaoProduto;

    private BigDecimal precoUnitario;

    private Integer quantidade;
}
