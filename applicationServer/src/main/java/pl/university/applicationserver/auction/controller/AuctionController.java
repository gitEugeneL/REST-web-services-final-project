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
        // get authUser or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // get auctionDto list
        List<GetAuctionDTO> getAuctionDTOs = auctionService.getAuctions(authUser);
        return ResponseEntity.ok(getAuctionDTOs);
    }


    @GetMapping("{auction-id}")
    public ResponseEntity<GetAuctionDTO> getOneAuction(@PathVariable("auction-id") String id,
                                                       @NonNull @RequestHeader("Authorization") String token) {
        // get authUser or throw
        GetAuthUserDTO authUser = authIntegrationService.getAuthUser(token);
        // get auctionDto or throw
        GetAuctionDTO getAuctionDTO = auctionService.getOneAuction(id, authUser);
        return ResponseEntity.ok(getAuctionDTO);
    }
}
