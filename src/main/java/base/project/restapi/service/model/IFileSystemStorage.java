package base.project.restapi.service.model;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import base.project.restapi.exception.FileNotFoundException;

public interface IFileSystemStorage {
    void init();

    String storeFile(MultipartFile file);

    Resource loadFile(String fileName) throws FileNotFoundException;
}
