package base.project.restapi.service;

import base.project.restapi.model.UserModel;
import base.project.restapi.repository.UserRepository;
import base.project.restapi.service.model.IUserService;
import base.project.restapi.transformers.UserTransformer;
import base.project.restapi.transformers.model.UserPrincipalTransformModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserService implements IUserService {
    UserRepository userRepository;

    CriteriaBuilder criteriaBuilder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserModel> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public UserModel save(UserModel payload) {
        return userRepository.save(payload);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);

        UserModel userModel = new UserModel();

        userModel.setId(id);
    }

    public List<UserModel> paginate(Pageable pageable) {
        criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserModel> query = criteriaBuilder
                .createQuery(UserModel.class);

        Root<UserModel> rootQuery = query.from(UserModel.class);

        query.select(rootQuery);

        return entityManager
                .createQuery(query)
                .setFirstResult(pageable.getPageNumber() > 1 ? pageable.getPageNumber() * pageable.getPageSize() : 0)
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public List<UserModel> findMany() {
        criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserModel> query = criteriaBuilder
                .createQuery(UserModel.class);

        Root<UserModel> rootQuery = query.from(UserModel.class);

        query.select(rootQuery);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public UserModel findOneByUsername(String username) {
        try {
            criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaQuery<UserModel> query = criteriaBuilder
                    .createQuery(UserModel.class);

            Root<UserModel> rootQuery = query.from(UserModel.class);

            query.where(criteriaBuilder.equal(rootQuery.get("username"), username));

            query.select(rootQuery);

            return entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserPrincipalTransformModel loadUserByUsername(String username) {
        UserModel user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User NOT Found"));

        return UserTransformer.transformToPrincipal(user);
    }
}
