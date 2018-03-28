/*
 * MyBatis配置文件 name：HelloServiceImpl.java copyright：Copyright by fenqile.com author：fone
 * version：2015年11月23日
 */
package com.test.springbootDemo.config;


/*import com.dshamc.pre.commons.util.plugin.PageInterceptor;
import com.dshamc.pre.inlet.dao.mysql.dao.PackageInfo;
import com.dshamc.pre.inlet.zk.DbNodeChangeListener;
import com.fenqile.dao.sharding.plugin.ShardPlugin;
import com.fenqile.dao.transform.MultipleDataSource;
import com.fenqile.dao.transform.MySqlSessionTemplate;
import com.fenqile.dao.transform.Transporter;
import com.fenqile.inbiz.configcenter.common.NodeChangeListener;
import com.fenqile.inbiz.configcenter.delegate.ConfigDelegate;
import com.fenqile.inbiz.configcenter.delegate.impl.ConfigDelegateImpl;
import com.fenqile.utils.common.PropertiesUtils;
import com.google.common.base.Preconditions;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;*/
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * MyBatis配置文件
 *
 * @version 2015年11月24日
 * @see MybatisConfig
 * @since
 */
@Configuration
@PropertySource({"classpath:config_center.properties"})
@EnableTransactionManagement
public class MybatisConfig implements ApplicationContextAware {

    /**
     * 打开分库分表插件
     */
    private static final boolean NEED_SHARDING = true;
    /**
     * spring context
     */
    private ApplicationContext context;

    /**
     * DB_NAME 数据库 库名
     */
    public static final String DB_NAME = "db.name";
    
    /**
     * DB_KEY 数据库 key
     */
    public static final String DB_KEY_PRE = "db.key.pre";

    /*******************
     * zk config
     ****************************************************************/

    /**
     * zk_address
     */
    private static final String ZK_ADDRESS = "configcenter_address";

    /**
     * zk_group
     */
    private static final String ZK_GROUP = "configcenter_groups";

    /**
     * ZK_DB_LISTENER_PATH
     */
    private static final String ZK_DB_LISTENER_PATH = "zk.db.path";

    /**
     * 生成zk代理对象
     *
     * @param env
     *            配置句柄
     * @return bean
     */
    /*@Bean(name = "dbConfigDelegate")
    public ConfigDelegate configDelegate(Environment env) {

        final String connect = Preconditions.checkNotNull(env.getProperty(ZK_ADDRESS));
        final String group = Preconditions.checkNotNull(env.getProperty(ZK_GROUP));

        Map<String, String> connectStrMap = new HashMap<String, String>();
        connectStrMap.put(group, connect);

        return new ConfigDelegateImpl(connectStrMap);
    }*/

    /**
     * 生成Transporter并从zookeeper上获取配置生成dataSource
     *
     * @param env
     *            配置句柄
     * @param zkConfigDelegate
     *            zk代理
     * @return Transporter 数据源转换
     * @throws Exception
     *             exception
     * @see dataSourceTransporter
     */
    /*@Bean
    public Transporter dataSourceTransporter(Environment env,@Qualifier("dbConfigDelegate") ConfigDelegate zkConfigDelegate)
        throws Exception {
        final String zkDbPath = env.getProperty(ZK_DB_LISTENER_PATH);
        final String group = Preconditions.checkNotNull(env.getProperty(ZK_GROUP));

        Map<String, String> dataMap = zkConfigDelegate.get(group, zkDbPath);
        Transporter transporter = new Transporter();
        transporter.buildDataSources(env.getProperty(DB_KEY_PRE), env.getProperty(DB_NAME), dataMap);

        return transporter;
    }*/

    /**
     * 生成数据库节点监听器
     *
     * @param env
     *            配置句柄
     * @param zkConfigDelegate
     *            zk代理
     * @param multipleDataSource
     *            数据源
     * @param transporter
     *            数据源转换器
     * @return NodeChangeListener
     * @see dbNodeChangeListener
     */
    /*@Bean
    public NodeChangeListener dbNodeChangeListener(Environment env,
    											   @Qualifier("dbConfigDelegate") ConfigDelegate zkConfigDelegate,
                                                   MultipleDataSource multipleDataSource,
                                                   Transporter transporter) {
        final String zkDbPath = env.getProperty(ZK_DB_LISTENER_PATH);
        final String group = Preconditions.checkNotNull(env.getProperty(ZK_GROUP));

        DbNodeChangeListener listener = new DbNodeChangeListener(zkDbPath, group);
        listener.setTransporter(transporter);
        listener.setMultipleDataSource(multipleDataSource);
        zkConfigDelegate.addListener(listener);
        return listener;
    }*/


    /*@Bean
    public PropertiesFactoryBean table2DB() {
        PropertiesFactoryBean properties = new PropertiesFactoryBean();
        Resource table2DBResource = new ClassPathResource("/sharding/table2db.properties");
        properties.setLocation(table2DBResource);
        return properties;
    }*/

    /**
     * 创建sqlSessionTemplate
     *
     * @param multipleDataSource
     *            复合数据源
     * @return sqlSessionTemplate
     * @throws Exception
     *             Exception
     * @see sqlSessionTemplate
     */
    /*@Bean
    public MySqlSessionTemplate sqlSessionTemplate(MultipleDataSource multipleDataSource,PropertiesFactoryBean table2DB)
        throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(multipleDataSource);
        factoryBean.setMapperLocations(context.getResources("classpath*:mappers*//*.xml"));
        factoryBean.setTypeAliasesPackage(PackageInfo.class.getPackage().getName());

        List<Interceptor> interceptors = new ArrayList<>();
        // page helper
        *//*PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        properties.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(properties);
        interceptors.add(pageHelper);*//*

        PageInterceptor pageInterceptor=new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("databaseType","mysql");
        pageInterceptor.setProperties(properties);
        interceptors.add(pageInterceptor);

        if(NEED_SHARDING){
            ShardPlugin shardPlugin = new ShardPlugin();
            Properties shardProperties = new Properties();
            shardProperties.setProperty("configsLocation","sharding/mybatis-sharding-config.xml");
            Properties table2DBProperties = table2DB.getObject();
            shardPlugin.setProperties(shardProperties);
            shardPlugin.setTable2DB(PropertiesUtils.convertToMap(table2DBProperties));
            interceptors.add(shardPlugin);
        }
        Interceptor[] interceptorsArray = interceptors.toArray(new Interceptor[interceptors.size()]);
        factoryBean.setPlugins(interceptorsArray);

        return new MySqlSessionTemplate(factoryBean.getObject());

    }*/

    /**
     * mpperScannnerConfigurer
     *
     * @return mpperScannnerConfigurer
     * @see mpperScannnerConfigurer
     */
    /*@Bean
    public MapperScannerConfigurer mpperScannnerConfigurer() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        msc.setBasePackage(PackageInfo.class.getPackage().getName());
        return msc;
    }*/

    /**
     * 创建DataSourceTransactionManager
     *
     * @param multipleDataSource
     *            复合数据源
     * @return DataSourceTransactionManager
     * @see transactionManager
     */
    /*@Bean
    public DataSourceTransactionManager transactionManager(MultipleDataSource multipleDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(multipleDataSource);
        return transactionManager;
    }*/

    /**
     * 设置applicationContext
     *
     * @param applicationContext
     *            applicationContext
     * @throws BeansException
     *             BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {
        context = applicationContext;
    }

}
