package vo.venu.voiceventure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vo.venu.voiceventure.model.LanguagePreference;

@Repository
public interface LanguagePreferenceRepository extends JpaRepository<LanguagePreference, Long> {
}