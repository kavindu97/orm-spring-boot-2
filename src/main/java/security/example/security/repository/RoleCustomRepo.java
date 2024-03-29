package security.example.security.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import security.example.security.entity.Role;
import security.example.security.entity.User;
import java.util.List;

@Repository
public class RoleCustomRepo {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> getRole(User user){
        StringBuilder sql=new StringBuilder()
                .append("SELECT r.name as name FROM users u JOIN user_role ur ON u.user_id=ur.user_id JOIN roles r ON r.id = ur.role_id Where 1=1");
        if(user.getEmail()!=null){
            sql.append(" and email=:email");
        }
        NativeQuery<Role> query=((Session)entityManager.getDelegate()).createNativeQuery(sql.toString());
        if(user.getEmail()!=null){
            query.setParameter("email",user.getEmail());
        }
        query.addScalar("name", StandardBasicTypes.STRING);
        query.setResultTransformer(Transformers.aliasToBean(Role.class));
        return query.list();
    }



}
