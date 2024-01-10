package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dao.CrudDAO;
import lk.ijse.coir.dto.DeliveryDto;
import lk.ijse.coir.dto.EmployeeDto;
import lk.ijse.coir.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<Employee> {
    int totalEmployeeCount() throws ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;
}
