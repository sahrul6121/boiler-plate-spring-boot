package base.project.restapi.service;

import org.springframework.stereotype.Service;
import base.project.restapi.model.ExampleModel;
import base.project.restapi.service.model.IExampleService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExampleService implements IExampleService {
    @Override
    public List<ExampleModel> findMany() {
        return null;
    }

    @Override
    public Optional<ExampleModel> findById(int id) {
        return Optional.empty();
    }

    @Override
    public ExampleModel save(ExampleModel payload) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
