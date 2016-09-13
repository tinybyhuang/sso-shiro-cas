# sso-shiro-cas
spring下使用shiro+cas配置单点登录，多个系统之间的访问，每次只需要登录一次

## 系统模块说明
1.  cas：  单点登录模块，这里直接拿的是cas的项目改了点样式而已
2.  doc：   文档目录，里面有数据库生成语句，采用的是MySQL5.0，数据库名为db_test  
3.  spring-node-1：   应用1
4.  spring-node-2：   应用2

  其中node1跟node2都是采用spring + springMVC + mybatis 框架，使用maven做项目管理

## cas集成说明
1.首先采用的是查数据库的方式来校验用户身份的，在cas/WEB-INF/deployerConfigContext.xml中第135行构建了这个类型
``` xml
<!-- 设置密码的加密方式，这里使用的是MD5加密 -->
	<bean id="passwordEncoder"
      class="org.jasig.cas.authentication.handler.DefaultPasswordEncoder"
      c:encodingAlgorithm="MD5"
      p:characterEncoding="UTF-8" />

  <!-- 通过数据库验证身份，这个得自己去实现 -->
	<bean id="primaryAuthenticationHandler"
      class="com.distinct.cas.jdbc.QueryDatabaseAuthenticationHandler"
      p:dataSource-ref="dataSource"
      p:passwordEncoder-ref="passwordEncoder"
      p:sql="select password from t_user where account=? and status = 'active'" />
      
  <!-- 设置数据源 -->
	 <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		  <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		  <property name="url" value="jdbc:mysql://127.0.0.1:3306/db_test?useUnicode=true&amp;characterEncoding=utf8"></property>
		  <property name="username" value="root"></property>
		  <property name="password" value="123456"></property>  
  </bean>
```
  其中QueryDatabaseAuthenticationHandler这个类是自定义构建的，在cas/WEB-INF/lib/cas-jdbc-1.0.0.jar里面，有兴趣的同学可以发编译看下，关于几个属性的说明
  1.  dataSource：    数据源，配置MySQL的连接信息
  2.  passwordEncoder：   加密方式，这里用的是MD5
  3.  sql：   sql查询语句，这个语句就是根据用户输入的账号查询其密码

#### 以上就是单点登录管理的主要配置

## 应用系统的配置node1
1. 应用系统采用shiro做权限控制，并且跟cas集成
2. 在/spring-node-1/src/main/resources/conf/shiro.properties 文件中
``` properties
shiro.loginUrl=http://127.0.0.1:8080/cas/login?service=http://127.0.0.1:8081/node1/shiro-cas

shiro.logoutUrl=http://127.0.0.1:8080/cas/logout?service=http://127.0.0.1:8081/node1/shiro-cas

shiro.cas.serverUrlPrefix=http://127.0.0.1:8080/cas

shiro.cas.service=http://127.0.0.1:8081/node1/shiro-cas

shiro.failureUrl=/users/loginSuccess

shiro.successUrl=/users/loginSuccess

```
其中shiro.loginUrl 跟 shiro.logoutUrl的前面是cas验证的地址，后面的是我们应用系统的地址，这样配置的方式是为了在访问我们的应用系统的时候，先到cas进行验证，如果验证成功了，cas将重定向到shiro.successUrl 所表示的地址

3.在/spring-node-1/src/main/resources/conf/shiro.xml 文件中
``` xml
<!-- Shiro Filter -->
	<bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
		<property name="securityManager" ref="securityManager" />
		<!-- 设定用户的登录链接，这里为cas登录页面的链接地址可配置回调地址 -->
		<property name="loginUrl" value="${shiro.loginUrl}" />
		<property name="filters">
			<map>
				<!-- 添加casFilter到shiroFilter -->
				<entry key="casFilter" value-ref="casFilter" />
				<entry key="logoutFilter" value-ref="logoutFilter" />
			</map>
		</property>
		<property name="filterChainDefinitions">
			<value>
				/shiro-cas = casFilter
				/logout = logoutFilter
				/users/** = user
			</value>
		</property>
	</bean>

	<bean id="casFilter" class="org.apache.shiro.cas.CasFilter">
		<!-- 配置验证错误时的失败页面 -->
		<property name="failureUrl" value="${shiro.failureUrl}" />
		<property name="successUrl" value="${shiro.successUrl}" />
	</bean>

	<bean id="logoutFilter" class="org.apache.shiro.web.filter.authc.LogoutFilter">
		<!-- 配置验证错误时的失败页面 -->
		<property name="redirectUrl" value="${shiro.logoutUrl}" />
	</bean>

	<bean id="casRealm" class="com.spring.mybatis.realm.UserRealm">
		<!-- 认证通过后的默认角色 -->
		<property name="defaultRoles" value="ROLE_USER" />
		<!-- cas服务端地址前缀 -->
		<property name="casServerUrlPrefix" value="${shiro.cas.serverUrlPrefix}" />
		<!-- 应用服务地址，用来接收cas服务端票据 -->
		<property name="casService" value="${shiro.cas.service}" />
	</bean>

	<!-- Shiro's main business-tier object for web-enabled applications -->
	<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
		<property name="subjectFactory" ref="casSubjectFactory"></property>
		<property name="realm" ref="casRealm" />
	</bean>

	<bean id="casSubjectFactory" class="org.apache.shiro.cas.CasSubjectFactory"></bean>

	<bean
		class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
		<property name="securityManager" ref="securityManager" />
	</bean>

	<bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"></bean>
	<bean
		class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
		<property name="staticMethod"
			value="org.apache.shiro.SecurityUtils.setSecurityManager"></property>
		<property name="arguments" ref="securityManager"></property>
	</bean>
```
> 其中shiroFilter这个类主要用于需要拦截的url请求，需要注意的是这个是shiro的拦截，我们还需要配置cas的过滤配置casFilter

> casRealm这个类是需要我们自己实现的，主要用于shiro的权限验证，里面的属性说明如下

1.  defaultRoles： 默认的角色
2.  casServerUrlPrefix：  cas地址
3.  casService：  系统应用地址

最后我们还需要在/spring-node-1/src/main/webapp/WEB-INF/web.xml 文件中配置相关的过滤器拦截全部请求
``` xml
<filter>
		<filter-name>shiroFilter</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
		<init-param>
			<param-name>targetFilterLifecycle</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>shiroFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```
	
##  系统运行
1.  端口说明，cas：8080，node1：8081，node2:8082，大家可以采用maven提供的tomcat7插件，配置如下：
``` xml
<plugin>
			<groupId>org.apache.tomcat.maven</groupId>
			<artifactId>tomcat7-maven-plugin</artifactId>
			<version>2.1</version>
			<configuration>
				<port>8081</port>
				<uriEncoding>UTF-8</uriEncoding>
				<server>tomcat7</server>
				<path>/node1</path>
			</configuration>
		</plugin>
```
	这样的配置，我们甚至都不需要配置tomcat服务器了，建议这种方式
	
2.各个模块的访问地址
> cas：http://127.0.0.1:8080/cas

> node1：http://127.0.0.1:8081/node1

> node2：http://127.0.0.1:8082/node2

3.访问系统
> 输入  http://127.0.0.1:8081/node1/shiro-cas ，进入cas验证

> 输入用户名  admin，密码 admin@2015，验证成功后将会重定向到http://127.0.0.1:8081/node1//users/loginSuccess ，也就是node1系统的主页，里面的节点2代表的是node2系统的主页，你会发现我们不需要登录到node2系统就能访问其中的系统了




