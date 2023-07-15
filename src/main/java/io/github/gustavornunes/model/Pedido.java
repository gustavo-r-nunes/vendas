package io.github.gustavornunes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private Integer id;
    private Cliente cliente;
    private LocalDate dataPedido;
    private BigDecimal total;

}
