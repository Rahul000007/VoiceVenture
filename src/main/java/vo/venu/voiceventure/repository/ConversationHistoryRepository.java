package vo.venu.voiceventure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vo.venu.voiceventure.model.ConversationHistory;

@Repository
public interface ConversationHistoryRepository extends JpaRepository<ConversationHistory, Long> {
}
