package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;


/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

  public Member save(Member member) throws SQLException {
    String sql = "insert into member(member_id, money) values(?, ?)";

    // try resources 문으로 close 처리
    try(Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {

      pstmt.setString(1, member.getMemberId());
      pstmt.setInt(2, member.getMoney());
      pstmt.executeUpdate();
      return member;

    } catch (SQLException e) {
      log.error("db error", e);
      throw e;
    }
  }

  public Member findById(String memberId) throws SQLException {
    String sql = "select * from member where member_id = ?";

    ResultSet rs = null;

    try (Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);) {

      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        Member member = new Member();
        member.setMemberId(rs.getString("member_id"));
        member.setMoney(rs.getInt("money"));
        return member;
      } else {
        throw new NoSuchElementException("member not found memberId=" + memberId);
      }

    } catch (SQLException e) {
      log.error("db error", e);
      throw e;
    } finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  public void update(String memberId, int money) throws SQLException {
    String sql = "update member set money=? where member_id=?";

    try (Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {

      pstmt.setInt(1, money);
      pstmt.setString(2, memberId);
      int resultSize = pstmt.executeUpdate();
      log.info("resultSize={}", resultSize);

    } catch (SQLException e) {
      log.error("db error", e);
      throw e;
    }
  }

  public void delete(String memberId) throws SQLException {
    String sql = "delete from member where member_id=?";

    try (Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {

      pstmt.setString(1, memberId);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      log.error("db error", e);
      throw e;
    }
  }

  private Connection getConnection() {
    return DBConnectionUtil.getConnection();
  }

  private void close(Connection con, Statement stmt, ResultSet rs) {

    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        log.info("error", e);
      }
    }

    if (stmt != null) {
      try {
        stmt.close();
      } catch (SQLException e) {
        log.info("error", e);
      }
    }

    if (con != null) {
      try {
        con.close();
      } catch (SQLException e) {
        log.info("error", e);
      }
    }

  }
}
