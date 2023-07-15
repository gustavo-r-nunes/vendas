package io.github.gustavornunes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {

    private Integer id;
    private Pedido pedido;
    private Produto produto;
    private Integer quantidade;
}
