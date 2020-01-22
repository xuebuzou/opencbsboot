package cn.com.bocd.opencbsboot.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}

	@Bean(name = "shiroRealm")
	@DependsOn("lifecycleBeanPostProcessor")
	public ShiroRealm shiroRealm() {
		ShiroRealm realm = new ShiroRealm();
		return realm;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm());
		return securityManager;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
		ShiroFilterFactoryBean sfilterBean = new ShiroFilterFactoryBean();
		sfilterBean.setSecurityManager(securityManager());
		sfilterBean.setLoginUrl("/zg");
		sfilterBean.setSuccessUrl("/zg/home");
		sfilterBean.setUnauthorizedUrl("/unAuthorized");

		Map map = new LinkedHashMap();
		map.put("authc",new MyFormAuthenticationFilter());
		sfilterBean.setFilters(map);

		LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
		filterChainDefinitionMap.put("/zg", "anon");
		filterChainDefinitionMap.put("/static/js/*", "anon");
		filterChainDefinitionMap.put("/static/assets/*", "anon");
		filterChainDefinitionMap.put("/static/css/*", "anon");
		filterChainDefinitionMap.put("/static/data/*", "anon");
		filterChainDefinitionMap.put("/static/image/*", "anon");
		filterChainDefinitionMap.put("/zg/home", "authc");
		filterChainDefinitionMap.put("/zg/home/openacct/*", "authc,roles[admin]");
		filterChainDefinitionMap.put("/zg/home/settings/user*", "authc,roles[admin]");
		filterChainDefinitionMap.put("/logout", "logout");
		sfilterBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return sfilterBean;
	}

	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
		defaultAAP.setProxyTargetClass(true);
		return defaultAAP;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
		aASA.setSecurityManager(securityManager());
		return aASA;
	}


}





