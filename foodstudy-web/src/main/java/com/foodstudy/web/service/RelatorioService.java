package com.foodstudy.web.service;

import com.foodstudy.web.model.Estabelecimento;
import com.foodstudy.web.model.Relatorio;
import com.foodstudy.web.repository.EstabelecimentoRepository;
import com.foodstudy.web.repository.RelatorioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatorioService {

    private final RelatorioRepository relatorioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;

    public RelatorioService(RelatorioRepository relatorioRepository,
                            EstabelecimentoRepository estabelecimentoRepository) {
        this.relatorioRepository = relatorioRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    
    public Relatorio criarRelatorio(Long estabelecimentoId, Relatorio dados) {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));

        dados.setEstabelecimento(estabelecimento);
        dados.setData(LocalDateTime.now()); // data atual

        
        dados.gerar();

        return relatorioRepository.save(dados);
    }

    
    public List<Relatorio> listarTodos() {
        return relatorioRepository.findAll();
    }

    
    public Relatorio buscarPorId(Long id) {
        return relatorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relatório não encontrado"));
    }

    public List<Relatorio> listarPorEstabelecimento(Long estabelecimentoId) {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));

        return estabelecimento.getRelatorios();
    }
}
