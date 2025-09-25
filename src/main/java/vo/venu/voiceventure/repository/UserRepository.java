package vo.venu.voiceventure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vo.venu.voiceventure.enums.Status;
import vo.venu.voiceventure.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select  u from User u where u.email = :email AND u.status = :status")
    User findByUserNameAndStatus(@Param("email") String email, Status status);

    User findByUserIdAndStatus(String userId, Status status);

}