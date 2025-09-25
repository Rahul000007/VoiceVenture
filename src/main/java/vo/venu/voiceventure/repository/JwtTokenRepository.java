package vo.venu.voiceventure.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vo.venu.voiceventure.enums.Status;
import vo.venu.voiceventure.model.JwtToken;
import vo.venu.voiceventure.model.User;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

    JwtToken findByUserAndStatus(User user, Status status);

}
