package vo.venu.voiceventure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vo.venu.voiceventure.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}