package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.EmployeeDto;
import lk.ijse.coir.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException;

    boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException;

    boolean saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException;

    boolean existEmployee(String id) throws SQLException, ClassNotFoundException;

    void deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    int totalEmployeeCount() throws ClassNotFoundException, SQLException;


}
