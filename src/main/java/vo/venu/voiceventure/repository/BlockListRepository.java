package vo.venu.voiceventure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vo.venu.voiceventure.model.BlockList;

@Repository
public interface BlockListRepository extends JpaRepository<BlockList, Long> {
}