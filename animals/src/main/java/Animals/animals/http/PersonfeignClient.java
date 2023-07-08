package Animals.animals.http;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="person")
public interface PersonfeignClient {
}
