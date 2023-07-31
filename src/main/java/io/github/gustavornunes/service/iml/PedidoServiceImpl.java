package io.github.gustavornunes.service.iml;

import io.github.gustavornunes.dto.ItemPedidoDTO;
import io.github.gustavornunes.dto.PedidoDTO;
import io.github.gustavornunes.enums.StatusPedido;
import io.github.gustavornunes.exception.PedidoNaoEncontradoException;
import io.github.gustavornunes.exception.RegraNegocioException;
import io.github.gustavornunes.model.Cliente;
import io.github.gustavornunes.model.ItemPedido;
import io.github.gustavornunes.model.Pedido;
import io.github.gustavornunes.model.Produto;
import io.github.gustavornunes.repository.ClienteRepository;
import io.github.gustavornunes.repository.ItemPedidoRepository;
import io.github.gustavornunes.repository.PedidoRepository;
import io.github.gustavornunes.repository.ProdutoRepository;
import io.github.gustavornunes.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Pedido pedido = new Pedido();

        Integer idCliente = dto.getClienteId();
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new RegraNegocioException("Id do cliente inválido"));


        pedido.setDataPedido(LocalDate.now());
        pedido.setTotal(dto.getTotal());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = processarItens(pedido, dto.getItens());
        repository.save(pedido);
        pedido.setItens(itensPedido);

        itemPedidoRepository.saveAll(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer idPedido) {
        return repository.findByIdFetchItens(idPedido);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido status) {
        repository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(status);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> processarItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar pedidos sem itens");
        }

        return itens
                .stream()
                .map(dto -> {
                    Integer produtoId = dto.getProdutoId();
                    Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new RegraNegocioException("Id de produto inválido : " + produtoId));
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
}
