package connect.data.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication(
        scanBasePackages = {
                "com.connect"
        })
public class TestDBApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TestDBApplication.class);
        application.run(args);
    }
}
