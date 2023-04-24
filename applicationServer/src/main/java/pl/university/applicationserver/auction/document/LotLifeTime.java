package pl.university.applicationserver.auction.document;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LotLifeTime {
    ONE_DAY(1),
    THREE_DAYS(3),
    ONE_WEEK(7);

    private final int value;

    public int getValue(){
        return value;
    }
}
