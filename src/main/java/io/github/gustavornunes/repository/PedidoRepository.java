package io.github.gustavornunes.repository;

import io.github.gustavornunes.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
