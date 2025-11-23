package com.foodstudy.web.service;

import com.foodstudy.web.model.Assinatura;
import com.foodstudy.web.model.Usuario;
import com.foodstudy.web.repository.AssinaturaRepository;
import com.foodstudy.web.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final UsuarioRepository usuarioRepository;

    public AssinaturaService(AssinaturaRepository assinaturaRepository,
                             UsuarioRepository usuarioRepository) {
        this.assinaturaRepository = assinaturaRepository;
        this.usuarioRepository = usuarioRepository;
    }

   
    public Assinatura salvar(Assinatura assinatura, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        assinatura.setUsuario(usuario);
        return assinaturaRepository.save(assinatura);
    }

    
    public List<Assinatura> listarTodas() {
        return assinaturaRepository.findAll();
    }

    
    public Assinatura buscarPorId(Long id) {
        return assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));
    }

   
    public Assinatura ativar(Long assinaturaId) {
        Assinatura assinatura = buscarPorId(assinaturaId);

       
        assinatura.ativar();

        return assinaturaRepository.save(assinatura);
    }
}
