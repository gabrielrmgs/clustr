package br.dev.irontech.clustr.auth;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    public LoginResponse login(@Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @POST
    @Path("/cadastro")
    public Response cadastro(@Valid CadastroRequest cadastroRequest) {
        CadastroResponse response = authService.cadastro(cadastroRequest);
        return Response.status(Status.CREATED).entity(response).build();
    }
}
