package pl.university.applicationserver.auction.document;


import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Image {
    private String id;
    private String name;
    private String type;
    private byte[] imageData;

    @Indexed(unique = true)
    private String auctionId;

    public Image(String name, String type, byte[] imageData, String auctionId) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
        this.auctionId = auctionId;
    }
}
