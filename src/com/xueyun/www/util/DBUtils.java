package com.xueyun.www.util;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 86135
 */
public class DBUtils {
    /**
     * 增删改的通用方法
     */
    public static int update(String sql, Object... param) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs=null;

        int cId=1;

        try {
            //获取连接对象
            connection = DataSourceUtils.getConnection();
            //创建预编译的语句对象，提供SQL语句，并且有占位符
            ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            //替换占位符为真实数据
            ParameterMetaData parameterMetaData = ps.getParameterMetaData();
            int count = parameterMetaData.getParameterCount();

            //给每个占位符赋值
            for (int i = 0; i < count; i++) {
                ps.setObject(i + 1, param[i]);
            }
            //执行增删改操作
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if(rs.next()) {
                //获取插入数据的自增长的主键Id
                cId=rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            DataSourceUtils.close(connection, ps);
        }
        return cId;
    }

    /**
     * 通用查询方法
     */
    public static <T> List<T> query(String sql, Class<T> clazz, Object... param) {
        List<T> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            //创建连接对象
            connection = DataSourceUtils.getConnection();
            //创建预编译的语句对象，提供SQl语句
            ps = connection.prepareStatement(sql);
            //获取参数元数据对象
            ParameterMetaData parameterMetaData = ps.getParameterMetaData();
            int count = parameterMetaData.getParameterCount();
            //给每个占位符赋值
            for (int i = 0; i < count; i++) {
                ps.setObject(i + 1, param[i]);
            }
            resultSet = ps.executeQuery();
            //获取结果集元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取总列数
            int columnCount = metaData.getColumnCount();
            //得到结果集，遍历结果集，封装成List对象
            while (resultSet.next()) {
                //每次遍历创建一个对象进行封装，获取无参的构造方法，在进行实例化
                T obj = clazz.getConstructor().newInstance();
                //循环给每个属性赋值
                for (int i = 1; i <= columnCount; i++) {
                    //获取这一列的名字，与类中成员变量名字是一样的
                    String columnName = metaData.getColumnName(i);
                    //从结果集中取出这一列的值
                    Object value = resultSet.getObject(columnName);
                    //通过名字获取相应的属性对象
                    Field field = clazz.getDeclaredField(columnName);

                    //设置为暴力反射
                    field.setAccessible(true);
                    //给属性赋值，参数1：要赋值的对象，参数2：要赋的值
                    field.set(obj, value);
                }
                //添加到集合中
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            DataSourceUtils.close(connection, ps, resultSet);
        }
        return list;
    }
}
