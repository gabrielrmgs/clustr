package br.dev.irontech.clustr.auth;

import java.time.Duration;

import br.dev.irontech.clustr.usuario.Usuario;
import br.dev.irontech.clustr.usuario.UsuarioRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class AuthService {

    private UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {

        Usuario usuario = usuarioRepository
                .find("email", loginRequest.email())
                .firstResultOptional()
                .orElseThrow(() -> new BadRequestException("Credenciais inválidas!"));

        if (Boolean.FALSE.equals(usuario.isAtivo()))
            throw new BadRequestException("Usuário inativo no banco!");

        if (!BcryptUtil.matches(loginRequest.senhaPlano(), usuario.getSenhaHash()))
            throw new BadRequestException("Credencial inválida!");

        String token = Jwt
                .issuer("clustr")
                .subject(usuario.getId().toString())
                .groups(usuario.getRole().name())
                .expiresIn(Duration.ofHours(1))
                .sign();

        return new LoginResponse(token, usuario.getNome(), usuario.getRole());

    }

    @Transactional
    public CadastroResponse cadastro(@Valid CadastroRequest cadastroRequest) {

        boolean emailJaExiste = usuarioRepository
                .find("email", cadastroRequest.email())
                .firstResultOptional()
                .isPresent();

        if (emailJaExiste)
            throw new BadRequestException("E-mail já cadastrado no sistema.");

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(cadastroRequest.nome());
        novoUsuario.setEmail(cadastroRequest.email());
        novoUsuario.setSenhaHash(BcryptUtil.bcryptHash(cadastroRequest.senhaPlana()));
        usuarioRepository.persist(novoUsuario);

        return new CadastroResponse(novoUsuario.getId(), novoUsuario.getNome(), novoUsuario.getEmail());
    }
}
