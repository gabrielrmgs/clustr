package br.dev.irontech.clustr.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// futuramente adicionar atributos como data de nascimento, peso, etc para montar uma base de progressão/dashboard/gráfico/imc
public record CadastroRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String senhaPlana) {

}
