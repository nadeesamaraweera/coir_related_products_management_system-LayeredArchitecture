package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.EmployeeDAO;
import lk.ijse.coir.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee");
        ArrayList<Employee> employees= new ArrayList<>();

        while (resultSet.next()) {
            Employee employee = new Employee(
                    resultSet.getString("employee_id"),
                    resultSet.getString("employee_name"),
                    resultSet.getString("email"),
                    resultSet.getString("tel"),
                    resultSet.getString("job_title"),
                    resultSet.getDouble("salary"),
                    resultSet.getString("date"));

            employees.add(employee);

        }
        return employees;
    }

    @Override
    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee VALUES(?, ?, ?, ? ,?,?,?)",
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getEmail(),
                employee.getTel(),
                employee.getJobTitle(),
                employee.getSalary(),
                employee.getDate()

        );
    }
    @Override
    public boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET employee_name = ?, email = ?, tel = ?,job_title=?,salary =? , date =? WHERE employee_id = ?",
                employee.getEmployeeName(),
                employee.getEmail(),
                employee.getTel(),
                employee.getJobTitle(),
                employee.getSalary(),
                employee.getDate(),
                employee.getEmployeeId());

    }


    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM employee WHERE employee_id=?",id);
        return rst.next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE employee_id = ?", id);

    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE employee_id=?", id);
        while (rst.next()){
            Employee employee =new Employee(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getDouble(6),rst.getString(7));
            return employee;
        }
        return  null;
    }




}
