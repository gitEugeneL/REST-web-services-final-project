package pl.university.paymentserver.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.university.paymentserver.payment.document.PaidAuction;

import java.util.Optional;

public interface PaidAuctionRepository extends MongoRepository<PaidAuction, String> {
    Optional<PaidAuction> findByWinnerId(String winnerId);
}
