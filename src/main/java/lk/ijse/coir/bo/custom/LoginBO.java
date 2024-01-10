package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.UserDto;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    boolean verifyCredential(String username, String password) throws SQLException, ClassNotFoundException;
    public boolean updatePassword(String username, String text) throws SQLException, ClassNotFoundException ;

    public UserDto getEmail(String username) throws SQLException, ClassNotFoundException;
    }
