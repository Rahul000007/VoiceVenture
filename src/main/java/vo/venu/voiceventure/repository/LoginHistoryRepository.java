package vo.venu.voiceventure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vo.venu.voiceventure.model.LoginHistory;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}