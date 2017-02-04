package com.shw.netdisk.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.shw.netdisk.config.BeetlProperties;
import com.shw.netdisk.storage.StorageProperties;
import com.shw.netdisk.storage.StorageService;


@Configuration
@ComponentScan(basePackages = {"com.shw.netdisk.controller","com.shw.netdisk.storage", "com.shw.netdisk.application"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, ThymeleafAutoConfiguration.class})
@EnableConfigurationProperties({StorageProperties.class, BeetlProperties.class})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
            storageService.deleteAll();
            storageService.init();
		};
	}
	
	/*@Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    public CommonsMultipartResolverExt multipartResolver() {
		CommonsMultipartResolverExt commonsMultipartResolverExt = new CommonsMultipartResolverExt();
		commonsMultipartResolverExt.setDefaultEncoding("UTF-8");
		commonsMultipartResolverExt.setMaxUploadSize(50*1024*1024);
		return commonsMultipartResolverExt;
	}*/
	
	/*@Bean
    public HttpMessageConverters customConverters() {

        //fastjson处理消息转换
		//JacksonAutoConfiguration jacksonAutoConfiguration = new JacksonAutoConfiguration();
		
		
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        
          List<MediaType> fastMediaTypes = new ArrayList<>();
          fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
          fastConverter.setSupportedMediaTypes(fastMediaTypes);
        
        fastConverter.setFastJsonConfig(fastJsonConfig);

        //文件下载使用ByteArrayHttpMessageConverter处理
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        byteArrayHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        
         //ByteArrayHttpMessageConverter默认处理请求类型就是APPLICATION_OCTET_STREAM
         List<MediaType> byteMediaTypes = new ArrayList<>();
         byteMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
         byteArrayHttpMessageConverter.setSupportedMediaTypes(byteMediaTypes);
         
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(fastConverter);
        converters.add(byteArrayHttpMessageConverter);

        return new HttpMessageConverters(converters);
    }
	*/
	
	
}
