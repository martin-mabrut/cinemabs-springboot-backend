package com.cinefamille.api.service;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class UploadService {

    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg", "image/png", "image/webp"
    );

    private final Path uploadDir;
    private final int maxWidth;
    private final int maxHeight;

    public UploadService(
            @Value("${app.upload.dir}")
            String uploadDir,

            @Value("${app.upload.max-width}")
            int maxWidth,

            @Value("${app.upload.max-height}")
            int maxHeight
    ) {
        this.uploadDir = Path.of(uploadDir);
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public String upload(MultipartFile file) throws IOException {
        String contentType = file.getContentType();

        if (!ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Format d'image non pris en charge");
        }

        Files.createDirectories(uploadDir);

        String extension = switch (contentType) {
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> ".jpg";
        };

        String filename = UUID.randomUUID().toString() + extension;

        Path destination = uploadDir.resolve(filename);

        Thumbnails.of(file.getInputStream()).size(maxWidth, maxHeight).crop(Positions.CENTER).toFile(destination.toFile());

        return "/api/uploads/" + filename;
    }
}