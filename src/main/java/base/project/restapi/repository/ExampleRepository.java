package base.project.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import base.project.restapi.model.ExampleModel;

public interface ExampleRepository extends JpaRepository<ExampleModel, Integer> { }
