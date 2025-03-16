package br.com.fiap.reservas.infra.gateway;

import br.com.fiap.reservas.entities.UsuarioEntity;
import br.com.fiap.reservas.infra.repository.usuario.Usuario;
import br.com.fiap.reservas.infra.repository.usuario.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UsuarioRepositorioJpaTest {

    private UsuarioRepositorioJpa usuarioRepositorioJpa;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioRepositorioJpa = new UsuarioRepositorioJpa(usuarioRepository);
    }

    @Test
    void deveBuscarUsuarioPeloId() {
        Usuario usuarioMock = new Usuario(1L, "nome", "email", "senha");
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuarioMock));

        UsuarioEntity resultado = usuarioRepositorioJpa.buscarUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals("nome", resultado.getNome());
        assertEquals("email", resultado.getEmail());
        verify(usuarioRepository).findById(anyLong());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioRepositorioJpa.buscarUsuarioPorId(1L));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }
}