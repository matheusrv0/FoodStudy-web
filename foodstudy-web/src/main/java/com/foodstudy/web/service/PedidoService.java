package com.foodstudy.web.service;

import com.foodstudy.web.enums.StatusPedido;
import com.foodstudy.web.model.Estabelecimento;
import com.foodstudy.web.model.Pedido;
import com.foodstudy.web.model.Produto;
import com.foodstudy.web.model.Usuario;

import com.foodstudy.web.repository.PedidoRepository;
import com.foodstudy.web.repository.UsuarioRepository;
import com.foodstudy.web.repository.ProdutoRepository;
import com.foodstudy.web.repository.EstabelecimentoRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         UsuarioRepository usuarioRepository,
                         ProdutoRepository produtoRepository,
                         EstabelecimentoRepository estabelecimentoRepository) {

        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    // Criar pedido
    public Pedido criarPedido(Long usuarioId, Long produtoId, Long estabelecimentoId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setProduto(produto);
        pedido.setEstabelecimento(estabelecimento);
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setCriadoEm(LocalDateTime.now());

        return pedidoRepository.save(pedido);
    }

   
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

   
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    
    public Pedido cancelar(Long pedidoId) {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    
    public Pedido agendarRetirada(Long pedidoId, LocalDateTime novaData) {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.setHorarioRetirada(novaData);
        pedido.setStatus(StatusPedido.CONFIRMADO);
        return pedidoRepository.save(pedido);
    }

    // Marcar pedido como retirado
    public Pedido marcarRetirado(Long pedidoId) {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.setStatus(StatusPedido.RETIRADO);
        return pedidoRepository.save(pedido);
    }
}
