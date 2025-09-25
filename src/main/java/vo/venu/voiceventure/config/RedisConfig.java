package vo.venu.voiceventure.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class RedisConfig {

    @Bean
    public RedissonClient redissonClient(Environment env) throws IOException {
        Resource resource = new ClassPathResource("redisson-config.yml");
        Config config = Config.fromYAML(resource.getInputStream());
        return Redisson.create(config);
    }
}
