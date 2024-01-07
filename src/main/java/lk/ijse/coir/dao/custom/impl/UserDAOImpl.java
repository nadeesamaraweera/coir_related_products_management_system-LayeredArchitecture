package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean ischecked(String username, String password) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT password FROM user WHERE user_name = ?",username);
        while (resultSet.next()) {
            String pw = resultSet.getString(1);
            if (password.equals(pw)) {
                return true;
            }
        }

        return false;
}
}