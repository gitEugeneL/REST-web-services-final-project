package pl.university.paymentserver.applicationServerIntegration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.university.paymentserver.applicationServerIntegration.dto.GetAuctionDTO;
import pl.university.paymentserver.applicationServerIntegration.exception.AuctionIntegrationServerException;


@Component
@RequiredArgsConstructor
public class ApplicationIntegrationService {

    final String APPLICATIOPN_SERVER_CONNECTION_STRING = "http://localhost:8081/api/auction";

    public GetAuctionDTO getAuction(String token, String id) {
        if (token.startsWith("Bearer "))
            token = token.substring(7);
        else
            throw new AuctionIntegrationServerException("token is not valid");

        String url = APPLICATIOPN_SERVER_CONNECTION_STRING + "/" + id;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<GetAuctionDTO> responseEntity = restTemplate
                    .exchange(url, HttpMethod.GET, entity, GetAuctionDTO.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK)
                return responseEntity.getBody();
            else
                throw new AuctionIntegrationServerException("Auction integration exception");
        } catch (RestClientException e) {
            throw new AuctionIntegrationServerException("Auction integration exception");
        }
    }


    public void updateAuctionStatus(String token, String id) {
        if (token.startsWith("Bearer "))
            token = token.substring(7);
        else
            throw new AuctionIntegrationServerException("token is not valid");

        String url = APPLICATIOPN_SERVER_CONNECTION_STRING + "/paid/" + id;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new AuctionIntegrationServerException("Auction integration exception");
            }
        } catch (RestClientException e) {
            throw new AuctionIntegrationServerException("Auction integration exception");
        }
    }
}
