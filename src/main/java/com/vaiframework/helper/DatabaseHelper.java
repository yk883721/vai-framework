package com.vaiframework.helper;

import com.vaiframework.utils.CollectionUtil;
import com.vaiframework.utils.PropsUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author admin
 */
public class DatabaseHelper {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    private static final BasicDataSource DATA_SOURCE;

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {

        DRIVER = ConfigHelper.getJdbcDriver();
        URL = ConfigHelper.getJdbcUrl();
        USERNAME = ConfigHelper.getJdbcUsername();
        PASSWORD = ConfigHelper.getJdbcPassword();

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("can not load jdbc driver", e);
        }
    }

    public static Connection getConnection() {

        Connection conn = CONNECTION_HOLDER.get();

        if (conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
                CONNECTION_HOLDER.set(conn);
            } catch (SQLException exception) {
                logger.error("get connection failure", exception);
                throw new RuntimeException(exception);
            }
        }

        return conn;
    }

    @Deprecated
    public static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException exception) {
                logger.error("close connection failure", exception);
                throw new RuntimeException(exception);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    public static <T> List<T> queryEntityList(Class<T> clazz, String sql, Object... params) {

        List<T> entityList = new ArrayList<>();

        try {

            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(clazz), params);

        } catch (SQLException exception) {

            logger.error("query entity list failure", exception);
            throw new RuntimeException(exception);

        }

        return entityList;

    }

    public static <T> T queryEntity(Class<T> clazz, String sql, Object... params) {

        T retEntity;

        try {

            Connection conn = getConnection();
            retEntity = QUERY_RUNNER.query(conn, sql, new BeanHandler<>(clazz), params);

        } catch (SQLException exception) {

            logger.error("query entity failure", exception);
            throw new RuntimeException(exception);

        }

        return retEntity;
    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {

        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            Connection conn = getConnection();
            resultList = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException exception) {
            logger.error("query entity failure", exception);
            throw new RuntimeException(exception);
        }

        return resultList;
    }

    public static int executeUpdate(String sql, Object... params) {

        int rows = 0;

        try {
            Connection conn = getConnection();

            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException exception) {
            logger.error("execute update failure", exception);
            throw new RuntimeException(exception);
        }

        return rows;
    }

    public static <T> boolean insertEntity(Class<?> clazz, Map<String, Object> fieldMap) {
        if (CollectionUtil.isNullOrEmpty(fieldMap)) {
            logger.error("can not insert entity: fieldMap is empty");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(clazz);

        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");

        for (String fieldName : fieldMap.keySet()) {
            columns.append("`").append(fieldName).append("`").append(", ");
            values.append("?, ");
        }

        columns.replace(columns.lastIndexOf(","), columns.length(), ")");
        values.replace(values.lastIndexOf(","), values.length(), ")");

        sql += columns + " VALUES " + values;

        Object[] params = fieldMap.values().toArray();

        return executeUpdate(sql, params) == 1;
    }

    public static <T> boolean updateEntity(Class<?> clazz, Long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isNullOrEmpty(fieldMap)) {
            logger.error("can not update entity: fieldMap is empty");
            return false;
        }

        String sql = "UPDATE " + getTableName(clazz) + " SET ";

        StringBuilder sets = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            sets.append("`").append(fieldName).append("`  =  ?, ");
        }
        sets.replace(sets.lastIndexOf(","), sets.length(), "");

        sql += sets + " WHERE id = ?";

        List<Object> paramList = new ArrayList<>(fieldMap.values());
        paramList.add(id);

        Object[] params = paramList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    public static <T> boolean deleteEntity(Class<?> clazz, Long id) {
        String sql = "DELETE FROM " + getTableName(clazz) + " WHERE id = ?";
        return executeUpdate(sql, id) == 1;
    }


    private static String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    public static void executeSqlFile(String filePath) {

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        if (is == null) {
            logger.error("execute sql file failure, file not found: " + filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String sql;
            while ((sql = reader.readLine()) != null) {
                executeUpdate(sql);
            }
        } catch (Exception exception) {
            logger.error("execute sql file failure", exception);
        }

    }

}
