package br.dev.irontech.clustr.auth;

import br.dev.irontech.clustr.usuario.Role;

public record LoginResponse(String tokenJwt, String nomeUsuario, Role perfil) {

}
