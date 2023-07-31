package io.github.gustavornunes.controller;

import io.github.gustavornunes.dto.AtualizacaoStatusPedidoDTO;
import io.github.gustavornunes.dto.InformacaoPedidoDTO;
import io.github.gustavornunes.dto.InformacoesPedidoDTO;
import io.github.gustavornunes.dto.PedidoDTO;
import io.github.gustavornunes.enums.StatusPedido;
import io.github.gustavornunes.model.ItemPedido;
import io.github.gustavornunes.model.Pedido;
import io.github.gustavornunes.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p) )
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@RequestBody AtualizacaoStatusPedidoDTO dto, @PathVariable Integer id){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converter (Pedido pedido){

        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .items(converter(pedido.getItens()))
                .status(pedido.getStatus().name())
                .build();

    }

    private List<InformacaoPedidoDTO> converter(List<ItemPedido> itens){

        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream().map(
                item -> InformacaoPedidoDTO
                        .builder().precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .descricaoProduto(item.getProduto().getDescricao())
                        .build())
                .collect(Collectors.toList());


    }



}
