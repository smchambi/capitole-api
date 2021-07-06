package com.capitole.entrypoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealCheckController {
	private static final String CHECK = "check!";

	/**
	 * Entry point to check the running application.
	 *
	 * @return successful response in case the application is successfully started.
	 */
	@GetMapping("/health")
	public String health() {
		return CHECK;
	}
}
