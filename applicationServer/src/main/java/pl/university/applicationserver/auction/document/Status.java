package pl.university.applicationserver.auction.document;

public enum Status {
    ACTIVE,  // lot is active
    FINISHED, // time is up and there are bets
    FAIlLED, // time is up and there are no bets
}
