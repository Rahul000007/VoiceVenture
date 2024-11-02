package vo.venu.voiceventure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vo.venu.voiceventure.model.AudioCallSession;

@Repository
public interface AudioCallSessionRepository extends JpaRepository<AudioCallSession, Long> {

}