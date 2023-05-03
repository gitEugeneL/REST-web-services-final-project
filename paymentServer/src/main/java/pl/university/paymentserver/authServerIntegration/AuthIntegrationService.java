package pl.university.paymentserver.authServerIntegration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.university.paymentserver.authServerIntegration.dto.GetAuthUserDTO;
import pl.university.paymentserver.authServerIntegration.exception.AuthIntegrationServerException;


@Component
@RequiredArgsConstructor
public class AuthIntegrationService {

    final String AUTH_SERVER_CONNECTION_STRING = "http://localhost:8080/api/user";


    public GetAuthUserDTO getAuthUser(String token) {

        if (token.startsWith("Bearer "))
            token = token.substring(7);
        else
            throw new AuthIntegrationServerException("token is not valid");

        String url = AUTH_SERVER_CONNECTION_STRING + "/auth/info";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<GetAuthUserDTO> responseEntity = restTemplate
                    .exchange(url, HttpMethod.GET, entity, GetAuthUserDTO.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK)
                return responseEntity.getBody();
            else
                throw new AuthIntegrationServerException("Auth integration exception");

        } catch (RestClientException e) {
            throw new AuthIntegrationServerException("Auth integration exception");
        }
    }
}
