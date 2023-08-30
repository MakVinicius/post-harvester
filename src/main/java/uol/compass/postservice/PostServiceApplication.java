package uol.compass.postservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import uol.compass.postservice.feignclient.PostConsumerFeign;

@SpringBootApplication
@EnableFeignClients
public class PostServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

	@Autowired
	private PostConsumerFeign postConsumerFeign;

	@Override
	public void run(String... args) throws Exception {
		this.postConsumerFeign.getPostById(1);
	}
}
