package lk.ijse.coir.bo.custom;

import java.sql.SQLException;

public interface LoginBO {
    boolean verifyCredential(String username, String password) throws SQLException, ClassNotFoundException;
}
