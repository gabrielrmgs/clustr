package br.dev.irontech.clustr.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = "E-mail vazio!") String email, @NotBlank(message = "Senha vazia!") String senhaPlano) {

}
