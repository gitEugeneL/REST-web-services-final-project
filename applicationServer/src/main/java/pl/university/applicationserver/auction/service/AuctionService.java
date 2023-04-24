package pl.university.applicationserver.auction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.university.applicationserver.auction.document.AuctionLot;
import pl.university.applicationserver.auction.document.Status;
import pl.university.applicationserver.auction.dto.CreateAuctionDTO;
import pl.university.applicationserver.auction.dto.GetAuctionDTO;
import pl.university.applicationserver.auction.dto.UpdateAuctionDTO;
import pl.university.applicationserver.auction.exception.ApiRequestException;
import pl.university.applicationserver.auction.repository.AuctionRepository;
import pl.university.applicationserver.authServerIntegration.dto.GetAuthUserDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public void createAuction(CreateAuctionDTO createAuctionDTO, GetAuthUserDTO authUser) {

        if (auctionRepository.findByNameAndStatus(
                createAuctionDTO.getName().trim().toLowerCase(), Status.ACTIVE).isPresent()) {
            throw new ApiRequestException("An active lot with this name already exists");
        }

        AuctionLot auctionLot = new AuctionLot(
                authUser.getId(),
                authUser.getEmail(),
                authUser.getFirstName(),
                createAuctionDTO.getName().trim().toLowerCase(),
                createAuctionDTO.getDescription().trim(),
                new BigDecimal(createAuctionDTO.getStartingPrice())
        );
        auctionLot.setEnd_time(createAuctionDTO.getLifeTime());

        auctionRepository.insert(auctionLot);
    }


    public void updateAuction(GetAuthUserDTO authUser, UpdateAuctionDTO dto, String id) {
        //--------------------------------------
        // authUser can only update his actions.
        //--------------------------------------
        auctionRepository.findById(id)
                .map(auction -> {
                    if(!Objects.equals(authUser.getId(), auction.getSeller_id())) {
                        throw new ApiRequestException("User is not authorized to update this auction");
                    }
                    auction.setDescription(dto.getDescription().trim());
                    return auctionRepository.save(auction);
                })
                .orElseThrow(() -> new ApiRequestException("Auction not found for id: " + id));
    }


    public void deleteAuction(GetAuthUserDTO authUser, String id) {
        //--------------------------------------
        // authUser can only delete his actions.
        //--------------------------------------
        AuctionLot auction = auctionRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Auction not found for id: " + id));

        if(!Objects.equals(authUser.getId(), auction.getSeller_id()))
            throw new ApiRequestException("User is not authorized to update this auction");

        auctionRepository.delete(auction);
    }


    public List<GetAuctionDTO> getAuctions() {
        return auctionRepository.findAll()
                .stream()
                .map(this::mapToGetAuctionDTO)
                .collect(Collectors.toList());
    }


    public GetAuctionDTO getOneAuction(String id) {
        return auctionRepository.findById(id)
                .map(this::mapToGetAuctionDTO)
                .orElseThrow(() -> new ApiRequestException("Auction lot not found for id: " + id));
    }


    private GetAuctionDTO mapToGetAuctionDTO(AuctionLot auction) {
        return new GetAuctionDTO(auction.getId(), auction.getSeller_email(), auction.getSeller_name(),
                auction.getStatus().toString(), auction.getParticipation(), auction.getName(),
                auction.getDescription(), auction.getStarting_price(), auction.getCurrent_price(),
                auction.getEnd_time());
    }
}
