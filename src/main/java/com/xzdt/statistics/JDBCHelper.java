package com.xzdt.statistics;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class JDBCHelper {

    private static JDBCHelper instance = null;
    // 数据库连接池
    private LinkedList<Connection> connections = new LinkedList<Connection>();

    // 静态代码块，加载数据库驱动
    static {
        try{
            Class.forName(ConfigurationManager.getStringProp(Constants.JDBC_DRIVER));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 私有化类构造器，实现单例模式
    private JDBCHelper(){
        // 在使用构造器创建实例时，要创建完成数据看连接池
        // 获取需要创建的连接数量
        Integer connSize = ConfigurationManager.getIntegerProp(Constants.JDBC_CONN_SIZE);

        // 循环创建连接
        for(int i = 0; i < connSize; i++){
            String url = ConfigurationManager.getStringProp(Constants.JDBC_URL);
            String username = ConfigurationManager.getStringProp(Constants.JDBC_USERNAME);
            String password = ConfigurationManager.getStringProp(Constants.JDBC_PASSWORD);
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                // 把连接放入连接池
                connections.add(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // 获取单例
    public static JDBCHelper getInstance(){
        if(instance == null){
            // 并发控制
            synchronized (JDBCHelper.class){
                if(instance == null){
                    instance = new JDBCHelper();
                }
            }
        }
        return instance;
    }


    // 获取数据库连接，控制并发
    public synchronized Connection getConnection(){
        // 如果连接被用完，则等待
        while(connections.size() == 0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 获取到连接
        return connections.poll();
    }

    // 向连接池返回连接
    public synchronized void returnConnection(Connection connection){
        if(connections.size() < 10){
            connections.add(connection);
        }
    }


}
