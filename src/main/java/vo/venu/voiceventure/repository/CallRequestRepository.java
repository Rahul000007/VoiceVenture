package vo.venu.voiceventure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vo.venu.voiceventure.model.CallRequest;

@Repository
public interface CallRequestRepository extends JpaRepository<CallRequest,Long> {
}
