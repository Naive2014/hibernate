package com.test;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class HibernateSessionFactory {
	
	/** 引入sessionFactory (sessionFactory:session工厂) */
	private static SessionFactory sessionFactory;
	/**构造Configuration配置   */
	private static Configuration configuration = new Configuration();
	/**定义一个ThreadLocal(本地路线)对象用于存放session (仅仅用于存放session，好来判断SessionFactory是否为空)*/
	private static ThreadLocal<Session> threadLocal = new ThreadLocal<>();
	
	static{
		//1.读取放在classpath下的全局文件hibernate.cfg.xml
		configuration.configure("/hibernate.cfg.xml");
		//2.生成一个sessionFactory
		sessionFactory =configuration.buildSessionFactory();
	}
	
	//获取session对象
	public static Session getSession(){
		Session session=threadLocal.get();
		//threadLocal（本地路线）是用来存放sessioin的，是用来判断session里面是否有已经有打开了的工厂，以确保可以正常开工，
		//如果session里面没有工厂,那么就进行重新创注册服务的sessionFactory（sessioin工厂）工厂（sessionFactory）,如果有那就进行下面的操作，打开session进行工作
		if(null==session || !session.isOpen()){
			if(sessionFactory == null){
				//重新创建起session工厂
				rebuildSessionFactory();
			}
		}
		//如果sessionFactory工厂不为空，那么就让sessionFactory工厂将session打开,然后将其赋给session，打开了的sessionFactory工厂可以进行开工；（比如：开启事务，执行命令，提交事务，回滚事务）
		session=(sessionFactory!=null)?sessionFactory.openSession():null;
		//将session设置到threadLocal中
		threadLocal.set(session);
		return session;
	}
 
	//重建sessionFactory
	private static void rebuildSessionFactory() {
		//1.读取hibernate.cfg.xml
		configuration.configure("/hibernate.cfg.xml");
		//2.利用配置建立生成一个注册服务的sessionFactory（sessioin工厂）
		sessionFactory =configuration.buildSessionFactory();
	}
	//关闭session
	public static void closeSession(){
		//将threadLocal的值session值赋给sessioin，然后将threadLocal的值设置为空，如果session的值不为空，那么就关闭清空session
		Session session=threadLocal.get();
		threadLocal.set(null);
		if(session!=null){
			session.close();
		}
	}
	
}
