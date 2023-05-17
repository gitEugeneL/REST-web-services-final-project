package pl.university.applicationserver.auction.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.university.applicationserver.auction.service.ImageService;
import pl.university.applicationserver.authServerIntegration.AuthIntegrationService;

import java.io.IOException;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;
    private final AuthIntegrationService authIntegrationService;


    @PostMapping("/upload/{auction-id}")
    public ResponseEntity<?> uploadImage(
            @NonNull @RequestHeader("Authorization") String token,
            @RequestParam("image")MultipartFile file,
            @PathVariable("auction-id") String auctionId) throws IOException {
        authIntegrationService.getAuthUser(token);
        String imageId = imageService.uploadImage(file, auctionId);
        return ResponseEntity.ok(imageId);
    }


    @GetMapping("/download/{auction-id}")
    public ResponseEntity<?> downloadImage(
            @NonNull @RequestHeader("Authorization") String token,
            @PathVariable("auction-id") String auctionId) {
        authIntegrationService.getAuthUser(token);
        byte[] imageData = imageService.downloadImage(auctionId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
