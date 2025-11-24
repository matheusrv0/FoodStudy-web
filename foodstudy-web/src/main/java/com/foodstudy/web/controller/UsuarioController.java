package com.foodstudy.web.controller;

import com.foodstudy.web.model.Usuario;
import com.foodstudy.web.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Map<String, String> payload) {
        String nome = payload.getOrDefault("nome", "Usuário");
        String cpf = payload.get("cpf");
        if (cpf == null || cpf.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF é obrigatório para login");
        }

        return usuarioService.loginOuCriar(nome, cpf);
    }

    // 1. Listar todos os usuários
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    // 2. Buscar usuário por ID
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    // 3. Cadastrar novo usuário
    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        return usuarioService.cadastrar(usuario);
    }

    // 4. Adicionar saldo ao FoodCash do usuário
    @PostMapping("/{id}/saldo")
    public void adicionarSaldo(@PathVariable Long id, @RequestParam float valor) {
        usuarioService.adicionarFoodCash(id, valor);
    }

    // 5. Visualizar pedidos do usuário
    @GetMapping("/{id}/pedidos")
    public List<?> visualizarPedidos(@PathVariable Long id) {
        return usuarioService.visualizarPedidos(id);
    }
}
