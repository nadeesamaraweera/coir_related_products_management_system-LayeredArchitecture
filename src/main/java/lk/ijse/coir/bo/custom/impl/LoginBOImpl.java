package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.LoginBO;
import lk.ijse.coir.dao.custom.UserDAO;
import lk.ijse.coir.dao.custom.impl.UserDAOImpl;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean verifyCredential(String username , String password) throws SQLException, ClassNotFoundException {
        return userDAO.ischecked(username,password);




    }
}
