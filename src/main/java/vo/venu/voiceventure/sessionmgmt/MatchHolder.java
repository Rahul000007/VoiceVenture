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
    private Set<String> activeUsers = new HashSet<>();
    private Map<String, String> usersInMatchingState = new HashMap<>();
    private Map<String, MatchAcceptanceEvent> matchAcceptanceEvent= new HashMap<>();
    private Map<String, Boolean> isCaller = new HashMap<>();
}
