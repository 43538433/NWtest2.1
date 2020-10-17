package com.xueyun.www.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 86135
 */
public class DataSourceUtils {

    /**
     * 1、声明静态数据成员变量
     */
    private static DataSource ds;

    /**
    2、创建连接池对象
     */
    static {

        /**
         * 加载配置文件中的数据
         * InputStream getResourceAsStream(String name)
         * 返回读取指定资源的输入流
         */
        InputStream is=  DataSourceUtils.class.getResourceAsStream("/druid.properties");
        Properties pp=new Properties();
        try {
            /**
             * void load(InputStream inStream)
             * 从输入流中读取属性列表（键和元素对）
             */
            pp.load(is);
            /**
             * 创建连接池，使用配置文件中的参数
             */
            ds= DruidDataSourceFactory.createDataSource(pp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 3、定义公有的得到数据源的方法
     */
    public  static DataSource getDataSource(){
        return ds;
    }

    /**
     * 4、定义得到连接对象的方法
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 5、定义关闭资源的方法
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 6、重载关闭方法
     */
    public static void close(Connection conn,Statement stmt){
        close(conn,stmt,null);
    }
}
