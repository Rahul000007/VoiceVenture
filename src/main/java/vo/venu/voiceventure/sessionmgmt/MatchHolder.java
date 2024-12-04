package vo.venu.voiceventure.sessionmgmt;


import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vo.venu.voiceventure.dto.MatchAcceptanceEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Component
@ToString
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MatchHolder {
    private Set<Long> activeUsers = new HashSet<>();
    private Map<Long, Long> usersInMatchingState = new HashMap<>();
    private Map<Long, MatchAcceptanceEvent> matchAcceptanceEvent= new HashMap<>();
    private Map<Long, Boolean> isCaller = new HashMap<>();
}
