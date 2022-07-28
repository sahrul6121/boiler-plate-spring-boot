package base.project.restapi.service;

import base.project.restapi.enums.RoleName;
import base.project.restapi.model.RoleModel;
import base.project.restapi.repository.RoleRepository;
import base.project.restapi.service.model.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService implements IRoleService {
    RoleRepository roleRepository;

    CriteriaBuilder criteriaBuilder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public RoleService(
        RoleRepository roleRepository
    ) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<RoleModel> findById(int id) {
        return roleRepository.findById(id);
    }

    @Override
    public RoleModel save(RoleModel payload) {
        return roleRepository.save(payload);
    }

    @Override
    public void deleteById(int id) {
        roleRepository.deleteById(id);
    }

    public List<RoleModel> paginate(Pageable pageable) {
        criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<RoleModel> query = criteriaBuilder
                .createQuery(RoleModel.class);

        Root<RoleModel> rootQuery = query.from(RoleModel.class);

        query.select(rootQuery);

        return entityManager
                .createQuery(query)
                .setFirstResult(pageable.getPageNumber() > 1 ? pageable.getPageNumber() * pageable.getPageSize() : 0)
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public List<RoleModel> findMany() {
        criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<RoleModel> query = criteriaBuilder
                .createQuery(RoleModel.class);

        Root<RoleModel> rootQuery = query.from(RoleModel.class);

        query.select(rootQuery);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public RoleModel findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
