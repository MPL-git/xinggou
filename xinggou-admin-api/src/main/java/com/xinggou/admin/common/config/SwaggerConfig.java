package com.xinggou.admin.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.xinggou.common.constant.Const;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Swagger 配置类
 *
 * @author hdl
 */
@EnableSwagger2
@EnableKnife4j
@Configuration
@ComponentScan(basePackages = "com.xinggou.admin.common.config")
public class SwaggerConfig {

    @Bean
    public Docket bcApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .groupName("BC系统用户中心")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.xinggou.admin.bc"))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(securitySchemes())
            .securityContexts(securityContexts());
    }

    @Bean
    public Docket rcApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("RC资源中心")
                .select()
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.xinggou.admin.rc"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            //接口文档的名字
            .title("醒购平台端接口文档")
            //接口文档的描述
            .description("登录接口会返回token， 除了登录相关的几个接口不需要传token参数外，其他接口都需要在Header传token参数才可访问")
            //服务条款网址
            .termsOfServiceUrl("http://localhost/")
            //接口文档的版本
            .version("0.0.1")
            .build();
    }

    private List<SecurityScheme> securitySchemes() {
        return newArrayList(
                new ApiKey("token", Const.AUTH_TOKEN, "header")
        );
    }

    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build()
        );
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("xxx", "描述信息");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference(Const.AUTH_TOKEN, authorizationScopes));
    }

}