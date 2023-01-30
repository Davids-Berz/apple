package com.apple.apple.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private static final Logger LOG = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    private static final String UPLOADS_FOLDER = "uploads";

    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path pathPhoto = getPath(filename);
        LOG.info("pathPhoto: " + pathPhoto);
        Resource recurso = new UrlResource(pathPhoto.toUri());
        if (!recurso.exists() && !recurso.isReadable())
            throw new RuntimeException("Error: no se puede cargar la imgen");
        return recurso;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFilename = file.getOriginalFilename() + UUID.randomUUID();
        Path rootPath = getPath(uniqueFilename);
        LOG.info("roothPath: " + rootPath);
        Files.copy(file.getInputStream(), rootPath);
        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File file = rootPath.toFile();
        if (file.exists() && file.canRead()) {
            if (file.delete()) {
                return true;
            }
        }
        return false;
    }

    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectories(Paths.get(UPLOADS_FOLDER));
    }
}
