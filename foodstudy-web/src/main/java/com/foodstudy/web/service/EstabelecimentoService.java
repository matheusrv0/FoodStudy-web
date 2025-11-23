package com.foodstudy.web.service;

import com.foodstudy.web.model.Estabelecimento;
import com.foodstudy.web.model.Produto;
import com.foodstudy.web.repository.EstabelecimentoRepository;
import com.foodstudy.web.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ProdutoRepository produtoRepository;

    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository,
                                  ProdutoRepository produtoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.produtoRepository = produtoRepository;
    }

    
    public Estabelecimento salvar(Estabelecimento estabelecimento) {
        return estabelecimentoRepository.save(estabelecimento);
    }

   
    public List<Estabelecimento> listarTodos() {
        return estabelecimentoRepository.findAll();
    }

    
    public Estabelecimento buscarPorId(Long id) {
        return estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
    }

    public Produto adicionarProduto(Long estabelecimentoId, Produto produto) {
        Estabelecimento estabelecimento = buscarPorId(estabelecimentoId);

        produto.setEstabelecimento(estabelecimento);
        return produtoRepository.save(produto);
    }

   
    public List<Produto> listarProdutos(Long estabelecimentoId) {
        Estabelecimento estabelecimento = buscarPorId(estabelecimentoId);
        return estabelecimento.getProdutos();
    }


    public void deletar(Long id) {
        estabelecimentoRepository.deleteById(id);
    }
}
