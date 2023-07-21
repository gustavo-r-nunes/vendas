package io.github.gustavornunes.repository;

import io.github.gustavornunes.model.Cliente;
import io.github.gustavornunes.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}
