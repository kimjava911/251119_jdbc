package kr.java.jdbc.repository;

import kr.java.jdbc.entity.Board;
import kr.java.jdbc.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardRepository {
    private final Connection connection;

    public BoardRepository() {
        this.connection = DBUtil.getConnection();
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
