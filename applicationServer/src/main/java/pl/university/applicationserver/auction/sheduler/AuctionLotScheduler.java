package pl.university.applicationserver.auction.sheduler;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.stereotype.Component;
import pl.university.applicationserver.auction.document.AuctionLot;
import pl.university.applicationserver.auction.document.Status;
import pl.university.applicationserver.auction.repository.AuctionRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;


@Component
@EnableScheduling
@RequiredArgsConstructor
public class AuctionLotScheduler {

    private final TaskScheduler taskScheduler;
    private final AuctionRepository auctionRepository;


    public void endAuction(Instant endTime, String auctionId) {
        taskScheduler.schedule(() -> {
            AuctionLot auctionLot = auctionRepository.findById(auctionId)
                    .orElseThrow(() -> new RuntimeException("find end auction error"));

            Map<String, BigDecimal> participants = auctionLot.getParticipants();

            if (participants == null || participants.isEmpty()) {
                auctionLot.setStatus(Status.FAIlLED);
            } else {
                String maxBetUserId = participants.entrySet().stream().max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey).orElse(null);
                if (maxBetUserId != null) {
                    auctionLot.setWinner_id(maxBetUserId);
                    auctionLot.setStatus(Status.FINISHED);
                } else {
                    auctionLot.setStatus(Status.FAIlLED);
                }
            }
            auctionRepository.save(auctionLot);
        }, endTime);
    }
}
