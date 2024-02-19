import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ApiMicroservicios {
}
    private static final String API_URL = "https://ejemplo-api.com/usuarios";

    public static void main(String[] args) {
        // Crear un nuevo usuario
        Usuario nuevoUsuario = new Usuario("John Doe", "john.doe@example.com");
        ResponseEntity<Usuario> responseCrear = crearUsuario(nuevoUsuario);
        System.out.println("Usuario creado: " + responseCrear.getBody());

        // Leer usuario por ID
        long usuarioId = responseCrear.getBody().getId();
        ResponseEntity<Usuario> responseLeer = leerUsuarioPorId(usuarioId);
        System.out.println("Usuario leído: " + responseLeer.getBody());

        // Actualizar usuario
        Usuario usuarioActualizado = responseLeer.getBody();
        usuarioActualizado.setNombre("John Smith");
        ResponseEntity<Usuario> responseActualizar = actualizarUsuario(usuarioActualizado);
        System.out.println("Usuario actualizado: " + responseActualizar.getBody());

        // Eliminar usuario
        ResponseEntity<Void> responseEliminar = eliminarUsuario(usuarioId);
        System.out.println("Usuario eliminado con éxito");
    }

    private static ResponseEntity<Usuario> crearUsuario(Usuario usuario) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Usuario> response = restTemplate.postForEntity(API_URL, usuario, Usuario.class);
        return response;
    }

    private static ResponseEntity<Usuario> leerUsuarioPorId(long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Usuario> response = restTemplate.getForEntity(API_URL + "/" + id, Usuario.class);
        return response;
    }

    private static ResponseEntity<Usuario> actualizarUsuario(Usuario usuario) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Usuario> requestEntity = new HttpEntity<>(usuario, headers);
        ResponseEntity<Usuario> response = restTemplate.exchange(API_URL + "/" + usuario.getId(), HttpMethod.PUT, requestEntity, Usuario.class);
        return response;
    }

    private static ResponseEntity<Void> eliminarUsuario(long id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(API_URL + "/" + id, HttpMethod.DELETE, null, Void.class);
        return response;
    }

    // Clase de modelo para representar un usuario
    static class Usuario {
        private long id;
        private String nombre;
        private String correo;

        // Constructor, getters y setters
    }
}


