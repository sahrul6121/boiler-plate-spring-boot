package base.project.restapi.base.service;

import java.util.List;
import java.util.Optional;

public interface IBaseService<BaseModel> {
    List<BaseModel> findMany();

    Optional<BaseModel> findById(int id);

    BaseModel save(BaseModel payload);

    void deleteById(int id);
}
