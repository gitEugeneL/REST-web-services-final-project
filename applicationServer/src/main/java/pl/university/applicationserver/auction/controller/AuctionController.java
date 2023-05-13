package pl.university.applicationserver.auction.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.university.applicationserver.auction.dto.CreateAuctionDTO;
import pl.university.applicationserver.auction.dto.CreateBetDTO;
import pl.university.applicationserver.auction.dto.GetAuctionDTO;
import pl.university.applicationserver.auction.dto.UpdateAuctionDTO;
import pl.university.applicationserver.auction.service.AuctionService;
import pl.university.applicationserver.auction.utils.DtoValidator;
import pl.university.applicationserver.authServerIntegration.AuthIntegrationService;
import pl.university.applicationserver.authServerIntegration.dto.GetAuthUserDTO;

import java.util.List;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auction")
public class AuctionController {

    private final AuctionService auctionService;
    private final AuthIntegrationService authIntegrationService;
    private final DtoValidator dtoValidator;


    @PostMapping("/create")
    public ResponseEntity<String> CreateAuction(
            @NonNull @RequestHeader("Authorization") String token,
            @RequestBody @Valid CreateAuctionDTO dto,
            BindingResult bindingResult) {
        // get authUser or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // validate dto or throw
        dtoValidator.validate(bindingResult);
        // create auction or throw
        auctionService.createAuction(dto, authUser);
        return ResponseEntity.ok("Auction lot added successfully");
    }


    @PutMapping("{auction-id}")
    public ResponseEntity<String> updateAuction(
            @NonNull @RequestHeader("Authorization") String token,
            @PathVariable("auction-id") String id,
            @RequestBody @Valid UpdateAuctionDTO dto, BindingResult bindingResult) {
        // get authUser or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // validate dto or throw
        dtoValidator.validate(bindingResult);
        // update auth user auction or throw
        auctionService.updateAuction(authUser, dto, id);
        return ResponseEntity.ok("Auction id: " + id + " successfully changed");
    }


    @PutMapping("/paid/{auction-id}")
    public ResponseEntity<String> updateAuctionStatus(
            @NonNull @RequestHeader("Authorization") String token,
            @PathVariable("auction-id") String id) {
        // get authUser or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // update paid auction or throw
        auctionService.updateAuctionStatus(authUser, id);
        return ResponseEntity.ok("Auction id: " + id + " successfully changed");
    }


    @DeleteMapping("{auction-id}")
    public ResponseEntity<String> deleteAuction(
            @NonNull @RequestHeader("Authorization") String token,
            @PathVariable("auction-id") String id) {

        // get authUser or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);

        auctionService.deleteAuction(authUser, id);
        return ResponseEntity.ok("Auction id: " + id + " successfully deleted");
    }


    @PostMapping("/bet")
    public ResponseEntity<String> createBet(
            @NonNull @RequestHeader("Authorization") String token, @RequestBody @Valid CreateBetDTO dto,
            BindingResult bindingResult) {

        // get authUser or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // validate dto or throw
        dtoValidator.validate(bindingResult);
        // create bet or throw
        auctionService.createBet(dto, authUser);
        return ResponseEntity.ok("The new bet has been created successfully");
    }


    @GetMapping
    public ResponseEntity<List<GetAuctionDTO>> getAuctions(@NonNull @RequestHeader("Authorization") String token) {
        // validate token or throw
        authIntegrationService.getAuthUser(token);
        // get auctionDto list
        List<GetAuctionDTO> getAuctionDTOs = auctionService.getAuctions();
        return ResponseEntity.ok(getAuctionDTOs);
    }

    @GetMapping("auth-user-auctions")
    public ResponseEntity<List<GetAuctionDTO>> getAuthUserAuctions(@NonNull @RequestHeader("Authorization") String token) {
        // validate token or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // get auth user auctions list
        List<GetAuctionDTO> getAuctionDTOS = auctionService.getAuthUserAuctions(authUser);
        return ResponseEntity.ok(getAuctionDTOS);
    }


    @GetMapping("auth-participant-auctions")
    public ResponseEntity<List<GetAuctionDTO>> getAuthParticipantAuctions(@NonNull @RequestHeader("Authorization") String token) {
        // validate token or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // get auth participant auctions list
        List<GetAuctionDTO> getAuctionDTOS = auctionService.getAuthParticipantAuctions(authUser);
        return ResponseEntity.ok(getAuctionDTOS);
    }


    @GetMapping("auth-purchased-auctions")
    public ResponseEntity<List<GetAuctionDTO>> getAuthPurchasedAuctions(@NonNull @RequestHeader("Authorization") String token) {
        // validate token or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // get auth purchased auctions list
        List<GetAuctionDTO> getAuctionDTOS = auctionService.getAuthPurchasedAuctions(authUser);
        return ResponseEntity.ok(getAuctionDTOS);
    }



    @GetMapping("auth-winner-auctions")
    public ResponseEntity<List<GetAuctionDTO>> getAuthWinnerAuctions(@NonNull @RequestHeader("Authorization") String token) {
        // validate token or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // get auth participant auctions list
        List<GetAuctionDTO> getAuctionDTOS = auctionService.getAuthWinnerAuctions(authUser);
        return ResponseEntity.ok(getAuctionDTOS);
    }


    @GetMapping("{auction-id}")
    public ResponseEntity<GetAuctionDTO> getOneAuction(@PathVariable("auction-id") String id,
                                                       @NonNull @RequestHeader("Authorization") String token) {
        // validate token or throw
        authIntegrationService.getAuthUser(token);
        // get auctionDto or throw
        GetAuctionDTO getAuctionDTO = auctionService.getOneAuction(id);
        return ResponseEntity.ok(getAuctionDTO);
    }
}
