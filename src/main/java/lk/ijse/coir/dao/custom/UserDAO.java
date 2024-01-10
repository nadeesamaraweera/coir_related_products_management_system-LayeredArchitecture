package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dto.UserDto;

import java.sql.SQLException;

public interface UserDAO {
    boolean ischecked(String username, String password) throws SQLException, ClassNotFoundException;

    public boolean updatePassword(String username, String text) throws SQLException, ClassNotFoundException;

    public UserDto getEmail(String username) throws SQLException, ClassNotFoundException;
    }
