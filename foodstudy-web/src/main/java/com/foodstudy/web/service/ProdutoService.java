package com.foodstudy.web.service;

import com.foodstudy.web.model.Produto;
import com.foodstudy.web.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

   
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    //
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    
    public Produto atualizar(Long id, Produto dados) {
        Produto produto = buscarPorId(id);

        produto.setNome(dados.getNome());
        produto.setDescricao(dados.getDescricao());
        produto.setPreco(dados.getPreco());
        produto.setTipo(dados.getTipo());
        produto.setEstabelecimento(dados.getEstabelecimento());

        return produtoRepository.save(produto);
    }

   
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
}
