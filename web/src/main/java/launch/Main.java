package launch;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.descriptor.web.ContextResource;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());
        //todo added
        tomcat.enableNaming();
        //todo trying
        ContextResource resource = new ContextResource();
//        resource.setName("local");
//        resource.setType(DataSource.class.getName());
//        resource.setAuth("Container");
//        resource.setType("javax.sql.DataSource");
//        resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
//        resource.setProperty("maxTotal", "10");
//        resource.setProperty("minIdle", "2");
//        resource.setProperty("maxWaitMillis", "10000");
//        resource.setProperty("username", "root");
//        resource.setProperty("password", "12131415a");
//        resource.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
//        resource.setProperty("url", "jdbc:mysql://localhost:3306/social_network?serverTimezone=UTC");
//        ctx.getNamingResources().addResource(resource);


        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}
