package pl.university.applicationserver.auction.exception;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.university.applicationserver.auction.document.Image;

import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {
    Optional<Image> findByAuctionId(String id);
}
