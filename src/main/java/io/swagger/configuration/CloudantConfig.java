package io.swagger.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

@Component
public class CloudantConfig {

	@Value("${local.cloudant.user}")
	private String user;
	@Value("${local.cloudant.pwd}")
	private String pwd;
	@Value("${local.cloudant.host}")
	private String host;
	@Value("${local.cloudant.port}")
	private String port;
	@Value("${local.cloudant.url}")
	private String url;

	@Bean
	public CloudantBinding getCloudantBinding() {
		CloudantBinding bindings = new CloudantBinding();
		String vcap_services = System.getenv("VCAP_SERVICES");
		if (vcap_services != null && !vcap_services.isEmpty()) {
			Object document = Configuration.defaultConfiguration().jsonProvider().parse(vcap_services);
			String user = JsonPath.read(document, "$.cloudantNoSQLDB[0].credentials.username");
			String pwd = JsonPath.read(document, "$.cloudantNoSQLDB[0].credentials.password");
			String host = JsonPath.read(document, "$.cloudantNoSQLDB[0].credentials.host");
			int port = JsonPath.read(document, "$.cloudantNoSQLDB[0].credentials.port");
			String url = JsonPath.read(document, "$.cloudantNoSQLDB[0].credentials.url");
			bindings.setUser(user);
			bindings.setPwd(pwd);
			bindings.setHost(host);
			bindings.setPort(port);
			bindings.setUrl(url);
			bindings.setSslEnabled(true);
		} else {
			bindings.setUser(user);
			bindings.setPwd(pwd);
			bindings.setHost(host);
			bindings.setPort(Integer.parseInt(port));
			bindings.setUrl(url);
			bindings.setSslEnabled(false);
		}
		return bindings;
	}
}
