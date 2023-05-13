package pl.university.applicationserver.auction.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.university.applicationserver.auction.document.AuctionLot;
import pl.university.applicationserver.auction.document.Status;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends MongoRepository<AuctionLot, String> {
    Optional<AuctionLot> findByNameAndStatus(String name, Status status);

    List<AuctionLot> findByStatus(Status status);

    List<AuctionLot> findBySellerId(String id);

    List<AuctionLot> findByWinnerIdAndStatus(String id, Status status);
}
