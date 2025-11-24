package com.foodstudy.web.service;

import com.foodstudy.web.model.Usuario;
import com.foodstudy.web.model.FoodCash;
import com.foodstudy.web.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(Usuario usuario) {

        FoodCash foodCash = new FoodCash();
        foodCash.setSaldo(0f);
        foodCash.setUsuario(usuario);

        usuario.setFoodCash(foodCash);

        return usuarioRepository.save(usuario);
    }

    public Usuario loginOuCriar(String nome, String cpf) {
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseGet(() -> criarUsuario(nome, cpf));

        if (usuario.getFoodCash() == null) {
            FoodCash carteira = new FoodCash();
            carteira.setSaldo(0f);
            carteira.setUsuario(usuario);
            usuario.setFoodCash(carteira);
            usuario = usuarioRepository.save(usuario);
        }

        return usuario;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void adicionarFoodCash(Long usuarioId, float valor) {
        Usuario usuario = buscarPorId(usuarioId);
        FoodCash wallet = usuario.getFoodCash();

        if (wallet == null) {
            wallet = new FoodCash();
            wallet.setSaldo(0f);
            wallet.setUsuario(usuario);
            usuario.setFoodCash(wallet);
        }

        wallet.adicionar(valor);
        usuarioRepository.save(usuario);
    }

    public List<?> visualizarPedidos(Long usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.getPedidos();
    }

    private Usuario criarUsuario(String nome, String cpf) {
        Usuario usuario = new Usuario();
        usuario.setNome(Objects.requireNonNullElse(nome, "Novo usuário"));
        usuario.setCpf(cpf);
        usuario.setTipo("aluno");

        FoodCash carteira = new FoodCash();
        carteira.setSaldo(0f);
        carteira.setUsuario(usuario);
        usuario.setFoodCash(carteira);

        return usuarioRepository.save(usuario);
    }
}
