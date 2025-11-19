package kr.java.jdbc.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

// DB
// 1. Driver -> Connection
// 2. Connection -> SQL Query
public class DBUtil {
    public static Connection getConnection() {
        System.out.println("커넥션 연결 시작");
        try {
            // 1단계 - 드라이버를 인식
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2단계
            System.out.println("커넥션 연결 성공");
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
            String DB_URL = dotenv.get("DB_URL");
            String DB_USER = dotenv.get("DB_USER");
            String DB_PASSWORD = dotenv.get("DB_PASSWORD");
            // Properties - 유사 Map (Key, Value)
            Properties properties = new Properties();
            properties.setProperty("user", DB_USER);
            properties.setProperty("password", DB_PASSWORD);
            Connection conn = DriverManager.getConnection(
                    DB_URL,
//                    DB_USER,
//                    DB_PASSWORD
                    properties
            );
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
