package com.foodstudy.web.config;

import com.foodstudy.web.model.*;
import com.foodstudy.web.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ProdutoRepository produtoRepository;
    private final AssinaturaRepository assinaturaRepository;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           EstabelecimentoRepository estabelecimentoRepository,
                           ProdutoRepository produtoRepository,
                           AssinaturaRepository assinaturaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.produtoRepository = produtoRepository;
        this.assinaturaRepository = assinaturaRepository;
    }

    @Override
    public void run(String... args) {
        seedUsuarios();
        seedEstabelecimentos();
        seedAssinaturas();
    }

    private void seedUsuarios() {
        if (usuarioRepository.count() > 0) {
            return;
        }

        Usuario user = new Usuario();
        user.setNome("Alice Estudante");
        user.setCpf("11122233344");
        user.setTipo("aluno");

        FoodCash carteira = new FoodCash();
        carteira.setSaldo(75f);
        carteira.setUsuario(user);
        user.setFoodCash(carteira);

        usuarioRepository.save(user);
    }

    private void seedEstabelecimentos() {
        if (estabelecimentoRepository.count() > 0) {
            return;
        }

        Estabelecimento cantina = new Estabelecimento();
        cantina.setNome("Cantina Central");
        estabelecimentoRepository.save(cantina);

        Estabelecimento lanchonete = new Estabelecimento();
        lanchonete.setNome("Lanchonete do Campus");
        estabelecimentoRepository.save(lanchonete);

        List<Produto> produtos = Arrays.asList(
                novoProduto("Coxinha Assada", "Recheio de frango orgânico", 6.5f, "salgado", cantina),
                novoProduto("Suco Verde", "Couvert detox com hortelã", 8.0f, "bebida", cantina),
                novoProduto("Wrap Integral", "Peito de peru com salada", 12.5f, "lanche", lanchonete),
                novoProduto("Brownie Fit", "Cacau 70% com castanhas", 9.0f, "sobremesa", lanchonete)
        );

        produtoRepository.saveAll(produtos);
    }

    private Produto novoProduto(String nome, String descricao, Float preco, String tipo, Estabelecimento estabelecimento) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setTipo(tipo);
        produto.setEstabelecimento(estabelecimento);
        return produto;
    }

    private void seedAssinaturas() {
        if (assinaturaRepository.count() > 0) {
            return;
        }

        Assinatura mensal = new Assinatura();
        mensal.setTipo("Plano Mensal");
        mensal.setValor(29.9f);
        mensal.setBeneficios("5% de cashback em todos os pedidos e retirada prioritária");

        Assinatura premium = new Assinatura();
        premium.setTipo("Plano Premium");
        premium.setValor(49.9f);
        premium.setBeneficios("10% de cashback, frete grátis e brindes sazonais");

        assinaturaRepository.saveAll(List.of(mensal, premium));
    }
}
