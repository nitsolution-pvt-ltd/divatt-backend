package com.divatt.auth;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
@EnableAutoConfiguration
public class EcomOauthLoginApplication implements CommandLineRunner{

//	private static final Logger LOGGER = LoggerFactory.getLogger(EcomOauthLoginApplication.class);
	
	@Autowired
//	private YMLConfig myConfig;
	
	public static void main(String[] args) {
		SpringApplication.run(EcomOauthLoginApplication.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {
		
//		EcomAuthController ecomAuthController = new EcomAuthController();
//		
//		
//		
//		if(! (ecomAuthController instanceof EcomAuthContollerMethod))
//			System.exit(0);
////		if(!EcomAuthContollerMethod.check())
////			System.exit(0);
//		
//		LOGGER.info("-------------------------------------");
//		LOGGER.info("name : "+myConfig.getName());
//		LOGGER.info("environment : "+myConfig.getEnvironment());
//		LOGGER.info("contextpath : "+myConfig.getContextpath());
//		LOGGER.info("servers: "+myConfig.getServers());
//		LOGGER.info("-------------------------------------");
	}
	
	
	@Bean
	public BCryptPasswordEncoder bcryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
	
//	@Bean
//    public JavaMailSender javaMailSender() {
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost("smtp.hostinger.in");
//		mailSender.setPort(587);
//		mailSender.setUsername("no-reply@nitsolution.in");
//		mailSender.setPassword("no-Reply@123");
//		 
//		Properties properties = new Properties();
//		properties.setProperty("mail.smtp.auth", "true");
//		properties.setProperty("mail.smtp.starttls.enable", "true");
//		properties.setProperty("mail.smtp.ssl.enable", "false");
//		properties.setProperty("mail.smtps.quitwait", "false");
//		
//		
//		 
//		mailSender.setJavaMailProperties(properties);
//        return mailSender;
//    }
	
	@Bean
    public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp-relay.sendinblue.com");
		mailSender.setPort(587);
		mailSender.setUsername("chandan@nitsolution.in");
		mailSender.setPassword("IdW3JyV7cr569CzH");
		 
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.ssl.enable", "false");
		properties.setProperty("mail.smtps.quitwait", "false");
		
		
		 
		mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
	
	@Bean
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
	    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

	    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
	                    .loadTrustMaterial(null, acceptingTrustStrategy)
	                    .build();

	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

	    CloseableHttpClient httpClient = HttpClients.custom()
	                    .setSSLSocketFactory(csf)
	                    .build();

	    HttpComponentsClientHttpRequestFactory requestFactory =
	                    new HttpComponentsClientHttpRequestFactory();

	    requestFactory.setHttpClient(httpClient);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    return restTemplate;
	 }
	@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        return mapper;
    }

}
