package pl.university.applicationserver.auction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.university.applicationserver.auction.document.AuctionLot;
import pl.university.applicationserver.auction.document.Status;
import pl.university.applicationserver.auction.dto.CreateAuctionDTO;
import pl.university.applicationserver.auction.dto.CreateBetDTO;
import pl.university.applicationserver.auction.dto.GetAuctionDTO;
import pl.university.applicationserver.auction.dto.UpdateAuctionDTO;
import pl.university.applicationserver.auction.exception.ApiRequestException;
import pl.university.applicationserver.auction.repository.AuctionRepository;
import pl.university.applicationserver.auction.sheduler.AuctionLotScheduler;
import pl.university.applicationserver.authServerIntegration.dto.GetAuthUserDTO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionLotScheduler auctionLotScheduler;


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
        // insert to db
        auctionRepository.insert(auctionLot);
        // scheduler for auction finish ---------------------------------------------
        auctionLotScheduler.endAuction(auctionLot.getEnd_time(), auctionLot.getId());
        // --------------------------------------------------------------------------
    }


    public void updateAuction(GetAuthUserDTO authUser, UpdateAuctionDTO dto, String id) {
        //--------------------------------------
        // authUser can only update his auctions.
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
        // authUser can only delete his auctions.
        //--------------------------------------
        AuctionLot auction = auctionRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Auction not found for id: " + id));

        if(!Objects.equals(authUser.getId(), auction.getSeller_id()))
            throw new ApiRequestException("User is not authorized to update this auction");

        auctionRepository.delete(auction);
    }


    public void createBet(CreateBetDTO dto, GetAuthUserDTO authUser) {
        //--------------------------------------------------------------------
        // only authUser can place a bet and cannot place a bet on his product
        // --------------------------------------------------------------------
        String auctionId = dto.getAuctionId();
        String authUserId = authUser.getId();

        auctionRepository.findById(auctionId)
                .map(auctionLot -> {
                    if(Objects.equals(auctionLot.getSeller_id(), authUserId))
                        throw new ApiRequestException("User is trying to buy his product");

                    if (auctionLot.getStatus() != Status.ACTIVE)
                        throw new ApiRequestException("The auction is outdated");

                    BigDecimal authUserBet = new BigDecimal(dto.getBet());
                    BigDecimal startingPrice = auctionLot.getStarting_price();
                    BigDecimal maxBet = new BigDecimal(0);

                    Map<String, BigDecimal> participation = auctionLot.getParticipants();
                    if (participation == null)
                        participation = new HashMap<>();
                    else
                        maxBet = Collections.max(participation.values());  // find max bet

                    // authUserBet > startingPrice && authUserBet > maxBet
                    if (authUserBet.compareTo(startingPrice) > 0 && authUserBet.compareTo(maxBet) > 0) {
                        participation.put(authUserId, new BigDecimal(dto.getBet()));
                        auctionLot.setParticipants(participation);
                        return auctionRepository.save(auctionLot);
                    }
                    else
                        throw new ApiRequestException("the bet is less than the current price");

                })
                .orElseThrow(() -> new ApiRequestException("Auction lot not fount for id: " + auctionId));
    }


    public List<GetAuctionDTO> getAuctions(GetAuthUserDTO authUser) {
        //--------------
        // only authUser
        // -------------
        String authUserId = authUser.getId();

        return auctionRepository.findAll()
                .stream()
                .map(auction -> mapAuctionToDTO(auction, authUserId))
                .collect(Collectors.toList());
    }


    public GetAuctionDTO getOneAuction(String id, GetAuthUserDTO authUser) {
        //--------------
        // only authUser
        // -------------
        String authUserId = authUser.getId();

        return auctionRepository.findById(id)
                .map(auction -> mapAuctionToDTO(auction, authUserId))
                .orElseThrow(() -> new ApiRequestException("Auction not found for id: " + id));
    }


    private GetAuctionDTO mapAuctionToDTO(AuctionLot auction, String authUserId) {
        // todo -----------------------
        boolean readyForPayment =
                auction.getStatus() == Status.FINISHED && Objects.equals(auction.getWinner_id(), authUserId);
        // todo -----------------------

        return new GetAuctionDTO(
                auction.getId(),
                auction.getSeller_email(),
                auction.getSeller_name(),
                auction.getStatus().toString(),
                auction.getParticipants(),   // todo delete id users and add names
                auction.getName(),
                auction.getDescription(),
                auction.getStarting_price(),
                auction.getCurrent_price(),
                auction.getEnd_time(),
                auction.getWinner_id()
        );
    }
}
