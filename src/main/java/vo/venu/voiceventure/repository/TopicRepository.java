package vo.venu.voiceventure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vo.venu.voiceventure.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}