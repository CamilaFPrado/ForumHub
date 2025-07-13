package com.forumhub.security;

import com.forumhub.model.Usuario;
import com.forumhub.repository.UsuarioRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + username));

        // Converte seus perfis para GrantedAuthorities (autoridades do Spring)
        var authorities = usuario.getPerfis().stream()
                .map(perfil -> new org.springframework.security.core.authority.SimpleGrantedAuthority(perfil.getNome()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenha(),
                authorities
        );
    }
}
