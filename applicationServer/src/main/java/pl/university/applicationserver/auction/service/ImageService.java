package pl.university.applicationserver.auction.service;


import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.university.applicationserver.auction.document.Image;
import pl.university.applicationserver.auction.exception.ApiRequestException;
import pl.university.applicationserver.auction.exception.ImageRepository;
import pl.university.applicationserver.auction.utils.ImageUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;


    public String uploadImage(MultipartFile file, String auctionId) throws IOException {
        if (imageRepository.findByAuctionId(auctionId).isPresent())
            throw new ApiRequestException("Image already exist");

        Image img = new Image(
                file.getOriginalFilename(),
                file.getContentType(),
                ImageUtils.compressImage(file.getBytes()),
                auctionId
        );
        imageRepository.insert(img);
        return img.getId();
    }


    public byte[] downloadImage(String auctionId) {
        Image image = imageRepository.findByAuctionId(auctionId)
                .orElseThrow(() -> new ApiRequestException("Image not found"));
                return ImageUtils.decompressImage(image.getImageData());
    }
}
