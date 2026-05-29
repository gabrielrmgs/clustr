package br.dev.irontech.clustr.auth;

import java.util.UUID;

public record CadastroResponse(UUID id, String nome, String email){}
