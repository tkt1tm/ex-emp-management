package com.example.service;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 従業員関連機能の業務処理を行うサービス.
 *
 * @author takuto.itami
 */
@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 従業員情報を全件取得します.
     *
     * @return 全従業員の情報が入ったリスト
     */
    public List<Employee> showList() {
        return employeeRepository.findAll();
    }

    /**
     * 従業員の詳細情報を取得します.
     *
     * @param id 取得したい情報を持つ従業員のID
     * @return IDをもとに取得した従業員のデータ
     */
    public Employee showDetail(Integer id) {
        return employeeRepository.findById(id);
    }

    /**
     * 従業員の情報を更新します.
     *
     * @param employee 更新した情報が入った従業員データ
     */
    public void update(Employee employee) {
        employeeRepository.update(employee);
    }
}
