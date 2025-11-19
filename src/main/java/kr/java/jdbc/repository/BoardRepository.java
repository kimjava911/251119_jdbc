package kr.java.jdbc.repository;

import kr.java.jdbc.entity.Board;
import kr.java.jdbc.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardRepository {
    private final Connection connection;

    public BoardRepository() {
        this.connection = DBUtil.getConnection();
    }

    public boolean login(String username) {
        //        String query = "SELECT 1 FROM dual WHERE '%s' = '%s'";
        String query = "SELECT 1 FROM dual WHERE ? = ?";
//        try (Statement statement = connection.createStatement(); ) {
        try (PreparedStatement pstmt = connection.prepareStatement(query); ) {
            // Statement <- 문자열을 그냥 집어넣음 그대로. -> SQL 문 자체로 인식함
            // JDBC Statement -> 문자열 쿼리 -> 그냥 그대로 실행시켜버리는... SQL Injection
//            return statement.executeQuery(query.formatted("kimjava", username)).next();
            pstmt.setString(1, "kimjava"); // 쿼리가 아니라 '값'으로 인식하게 됨
            pstmt.setString(2, username);
            // ' OR '1'='1 <- 들어가도...
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 저장 -> return?
    // 1. 아무것도 리턴하지 않는다 <- 문제가 있다면 SQLException
    // 2. boolean <- true/false / Exception
    // 3. int <- update, delete 몇개가 있는지...
    // 4. Entity
    public void save(String content) {
        try (Statement statement = connection.createStatement(); ) {
            String query = "INSERT INTO BOARD (content) VALUES ('%s')";
            statement.executeUpdate(query.formatted(content));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Board> findAll() {
        try (Statement statement = connection.createStatement(); ) {
            String query = "SELECT * FROM BOARD";
            ResultSet rs = statement.executeQuery(query);
            List<Board> boardList = new ArrayList<>();
            while (rs.next()) {
                // 행을 받은 셈
                // 1 : board_id
                // 2 : content
                // 3 : created_at
                // get타입(위치-1시작인덱스)
                int boardId = rs.getInt(1);
                String content = rs.getString(2);
                String createdAt = rs.getString(3);
                boardList.add(
                        new Board(boardId, content, createdAt)
                );
            }
            return boardList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
