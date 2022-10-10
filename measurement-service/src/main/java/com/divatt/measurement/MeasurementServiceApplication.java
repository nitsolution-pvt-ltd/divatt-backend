package com.divatt.measurement;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.divatt.measurement.config.YMLConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
public class MeasurementServiceApplication implements CommandLineRunner{
	
	@Autowired
	private YMLConfig myConfig;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementServiceApplication.class);
	

	public static void main(String[] args) {
		SpringApplication.run(MeasurementServiceApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		
	
		
		LOGGER.info("-------------------------------------");
		LOGGER.info("name : "+myConfig.getName());
		LOGGER.info("environment : "+myConfig.getEnvironment());
		LOGGER.info("contextpath : "+myConfig.getContextpath());
		LOGGER.info("servers: "+myConfig.getServers());
		LOGGER.info("-------------------------------------");
	}
	
	
	@Bean
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		
		TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
	    SSLContext sslContext = SSLContexts
	    		.custom()
	    		.loadTrustMaterial(null, acceptingTrustStrategy)
	    		.build();
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("https", sslsf)
	            .register("http", new PlainConnectionSocketFactory())
	            .build();

//	    BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(socketFactoryRegistry);
	    
	    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
	     connectionManager.setMaxTotal(30);
	     connectionManager.setDefaultMaxPerRoute(50);
	     
	    RequestConfig config = RequestConfig.custom().setConnectTimeout(100000).build();
	    
	    CloseableHttpClient httpClient = HttpClients
	    		.custom()
	    		.setSSLSocketFactory(sslsf)
	            .setConnectionManager(connectionManager)
	            .setDefaultRequestConfig(config)
	            .build();
	    

	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    return restTemplate;
 	}
}
