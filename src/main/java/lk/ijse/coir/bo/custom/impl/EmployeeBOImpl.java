package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.EmployeeBO;
import lk.ijse.coir.dao.DAOFactory;
import lk.ijse.coir.dao.custom.EmployeeDAO;
import lk.ijse.coir.dto.EmployeeDto;
import lk.ijse.coir.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO =
            (EmployeeDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.Employee);


    @Override
    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = employeeDAO.getAll();
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDtos.add(new EmployeeDto(employee.getEmployeeId(), employee.getEmployeeName(), employee.getEmail(), employee.getJobTitle(), employee.getTel(), employee.getSalary(), employee.getDate()));
        }
        return employeeDtos;
    }

    @Override
    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(employeeDto.getEmployeeId(),employeeDto.getEmployeeName(),employeeDto.getEmail(),employeeDto.getTel(),employeeDto.getJobTitle(),employeeDto.getSalary(),employeeDto.getDate()));


}

    @Override
    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(employeeDto.getEmployeeId(),employeeDto.getEmployeeName(),employeeDto.getEmail(),employeeDto.getTel(),employeeDto.getJobTitle(),employeeDto.getSalary(),employeeDto.getDate()));
    }


    @Override
    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.exist(id);
    }

    @Override
    public void deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        employeeDAO.delete(id);

    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException {

        Employee employee = employeeDAO.search(id);
        if (employee != null) {
            return new EmployeeDto(employee.getEmployeeId(),employee.getEmployeeName(),employee.getEmail(), employee.getTel(), employee.getJobTitle(),employee.getSalary(),employee.getDate());

        } else {
            return null;
        }
    }
}
