package vo.venu.voiceventure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vo.venu.voiceventure.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}