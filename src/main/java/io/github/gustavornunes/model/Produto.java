package io.github.gustavornunes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private Integer id;
    private String descricao;
    private BigDecimal preco;

}
