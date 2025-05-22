package com.example.repository;

import com.example.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * employeesテーブルを操作するリポジトリ.
 *
 * @author takuto.itami
 */
@Repository
public class EmployeeRepository {

    /** EmployeeオブジェクトのRowMapper */
    private final static RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
        Employee employee = new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("image"),
                rs.getString("gender"),
                rs.getDate("hire_date").toLocalDate(),
                rs.getString("mail_address"),
                rs.getString("zip_code"),
                rs.getString("address"),
                rs.getString("telephone"),
                rs.getInt("salary"),
                rs.getString("characteristics"),
                rs.getInt("dependents_count")
        );
        return  employee;
    };

    /** 参照情報が注入されたNamedParameterJdbcTemplate型のオブジェクト */
    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * 従業員一覧情報を入社日降順で取得します.
     *
     * @return 従業員の一覧情報(存在しない場合は0件の一覧)
     */
    public List<Employee> findAll() {
        String sql = "SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count FROM employees ORDER BY hire_date DESC;";

        List<Employee> employees = template.query(sql, EMPLOYEE_ROW_MAPPER);

        return employees;
    }

    /**
     * 従業員一覧情報を11件入社日降順で取得します.
     *
     * @return 従業員の一覧情報を11件刻みで(存在しない場合は0件の一覧)
     */
    public List<Employee> findPartially(Integer start) {
        String sql = "SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count FROM employees ORDER BY hire_date DESC OFFSET :start LIMIT 11;";

        SqlParameterSource param = new MapSqlParameterSource().addValue("start", start);

        List<Employee> employees = template.query(sql, param, EMPLOYEE_ROW_MAPPER);

        return employees;
    }

    /**
     * 主キーから従業員情報を取得します.
     *
     * @return 渡された主キーを持つ従業員の情報
     */
    public Employee findById(Integer id) {
        String sql = "SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count FROM employees WHERE id = :id;";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

        return employee;
    }

    /**
     * 従業員情報を更新します.
     *
     * @param employee 更新したい情報が入った従業員情報
     */
    public void update(Employee employee) {
        String sql = "UPDATE employees SET name = :name, image = :image, gender = :gender, hire_date = :hireDate, mail_address = :mailAddress, zip_code = :zipCode, address = :address, telephone = :telephone, salary = :salary, characteristics = :characteristics, dependents_count = :dependentsCount WHERE id = :id;";

        SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

        template.update(sql, param);
    }
}
