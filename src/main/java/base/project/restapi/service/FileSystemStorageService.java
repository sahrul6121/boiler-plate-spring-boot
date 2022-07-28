package base.project.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import base.project.restapi.configuration.UploadFileConfiguration;
import base.project.restapi.exception.CustomErrorException;
import base.project.restapi.exception.FileNotFoundException;
import base.project.restapi.exception.FileStorageException;
import base.project.restapi.service.model.IFileSystemStorage;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements IFileSystemStorage {
    private final Path dirLocation;

    @Autowired
    public FileSystemStorageService(
        UploadFileConfiguration uploadFileConfiguration
    ) {
        this.dirLocation = Paths.get(uploadFileConfiguration.getLocation())
            .toAbsolutePath()
            .normalize();
    }

    @Override
    @PostConstruct
    public void init() throws FileStorageException {
        try {
            Files.createDirectories(this.dirLocation);
        } catch (Exception exception) {
            throw new FileStorageException("Cant create upload dir !");
        }
    }

    @Override
    public String storeFile(MultipartFile file) throws FileStorageException {
        try {
            String fileName = file.getOriginalFilename();

            if (fileName == null) {
                throw new CustomErrorException("File name can`t be empty !");
            }

            Path dirFile = this.dirLocation.resolve(fileName);

            Files.copy(file.getInputStream(), dirFile, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (Exception exception) {
            throw new FileStorageException("Cant upload file !");
        }
    }

    @Override
    public Resource loadFile(String fileName) throws FileNotFoundException {
        try {
            Path file = this.dirLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(file.toUri());

            if ((!resource.exists()) && !(resource.isReadable())) {
                throw new FileNotFoundException("Could not find file");
            }

            return resource;
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new FileNotFoundException("Could not download file");
        }
    }
}
