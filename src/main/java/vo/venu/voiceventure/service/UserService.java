package vo.venu.voiceventure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vo.venu.voiceventure.model.User;
import vo.venu.voiceventure.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String getUsernameById(Long userId) {
        User user= userRepository.findById(userId).get();
        return user.getUsername();
    }
}
