package io.github.gustavornunes.service;

import io.github.gustavornunes.dto.PedidoDTO;
import io.github.gustavornunes.enums.StatusPedido;
import io.github.gustavornunes.model.Pedido;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer idPedido);

    void atualizaStatus(Integer id, StatusPedido status);
}
