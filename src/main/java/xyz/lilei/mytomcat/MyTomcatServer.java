package xyz.lilei.mytomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @ClassName MyTomcatServer
 * @Description TODO
 * @Author lilei
 * @Date 11/08/2019 19:55
 * @Version 1.0
 **/
public class MyTomcatServer {

    public static void main(String[] args) throws Exception {
        // 把目录的绝对路径获取到
        String classpath = System.getProperty("user.dir");
        System.out.println(classpath);
        Tomcat tomcat = new Tomcat();
        // 设置Tomcat的端口
        Connector connector = tomcat.getConnector();
        connector.setPort(9091);
        // 设置Host
        Host host = tomcat.getHost();
        host.setName("localhost");
        host.setAppBase("webapps");
        // 加载class
        Context context = tomcat.addContext(host, "/",classpath);
        if (context instanceof StandardContext){
            StandardContext standardContext = (StandardContext) context;
            // 加载自己的web.xml配置文件
            standardContext.setDefaultContextXml("D:/workAPP/apache-tomcat-8.5.39-windows-x64/apache-tomcat-8.5.39/conf/web.xml");
            // 设置Servlet
            Wrapper wrapper = tomcat.addServlet("/", "MyServlet", new MyServlet());
            // 设置访问路径
            wrapper.addMapping("/lilei");
        }

        // Tomcat启动
        tomcat.start();
        // 强制Tomcat server等待, 避免main线程执行结束后关闭
        tomcat.getServer().await();
    }
}
