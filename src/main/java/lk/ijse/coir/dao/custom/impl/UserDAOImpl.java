package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.UserDAO;
import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean ischecked(String username, String password) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT password FROM user WHERE user_name = ?", username);
        while (resultSet.next()) {
            String pw = resultSet.getString(1);
            if (password.equals(pw)) {
                return true;
            }
        }

        return false;
    }

    public boolean updatePassword(String username, String text) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE user SET password=? WHERE user_name=?";
        return SQLUtil.execute(sql, text, username);
    }

    public UserDto getEmail(String username) throws SQLException, ClassNotFoundException {


        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE user_name=?");

            if (resultSet.next()) {
                return new UserDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)

                );
            }

        return null;
    }
}