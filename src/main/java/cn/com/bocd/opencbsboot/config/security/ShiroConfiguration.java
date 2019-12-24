package cn.com.bocd.opencbsboot.config.security;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfiguration {
	/**
	 * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
	 * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
	 * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * HashedCredentialsMatcher，这个类是为了对密码进行编码的，
	 * 防止密码在数据库里明码保存，当然在登陆认证的时候，
	 * 这个类也负责对form里输入的密码进行编码。
	 */
	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}

	/**
	 * 自定义的realm
	 */
	@Bean(name = "shiroRealm")
	@DependsOn("lifecycleBeanPostProcessor")
	public ShiroRealm shiroRealm() {
		ShiroRealm realm = new ShiroRealm();
		return realm;
	}

//    /**
//     * EhCacheManager，缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，
//     * 然后每次用户请求时，放入用户的session中，如果不设置这个bean，每个请求都会查询一次数据库。
//     */
//    @Bean(name = "ehCacheManager")
//    @DependsOn("lifecycleBeanPostProcessor")
//    public EhCacheManager ehCacheManager() {
//        return new EhCacheManager();
//    }
	/**
	 * 安全管理器
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm());
		return securityManager;
	}

	/**
	 * shiro过滤器
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
		ShiroFilterFactoryBean sfilterBean = new ShiroFilterFactoryBean();
		sfilterBean.setSecurityManager(securityManager());
		sfilterBean.setLoginUrl("/index");
		sfilterBean.setSuccessUrl("/home");
		//权限认证失败跳转
		sfilterBean.setUnauthorizedUrl("/403");

		//解决登陆后无法跳转的问题
		Map map = new LinkedHashMap();
		map.put("authc",new MyFormAuthenticationFilter());
		sfilterBean.setFilters(map);

		//顺序拦截器
		LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
		//设置匿名访问
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/static/js/*", "anon");
		filterChainDefinitionMap.put("/static/assets/*", "anon");
		filterChainDefinitionMap.put("/static/css/*", "anon");
		filterChainDefinitionMap.put("/static/data/*", "anon");
		filterChainDefinitionMap.put("/static/image/*", "anon");
		//设置user,role,permission认证
		filterChainDefinitionMap.put("/home", "authc");

		//设置logout
		filterChainDefinitionMap.put("/logout", "logout");

		sfilterBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return sfilterBean;
	}

	/**
	 * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
		defaultAAP.setProxyTargetClass(true);
		return defaultAAP;
	}

	/**
	 * 开启shiro aop注解支持 使用代理方式;所以需要开启代码支持;
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
		aASA.setSecurityManager(securityManager());
		return aASA;
	}


}





