package com.example.controller;

import com.example.domain.Employee;
import com.example.form.UpdateEmployeeForm;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 従業員関連の処理の制御を行うコントローラ.
 *
 * @author takuto.itami
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 従業員一覧を出力します.
     *
     * @param model リクエストパラメータを格納
     * @return 従業員一覧画面にフォワード
     */
    @GetMapping("/showList")
    public String showList(Model model) {
        List<Employee> employees = employeeService.showList();
        model.addAttribute("employeeList", employees);
        return "employee/list";
    }

    /**
     * 従業員の詳細画面を表示します.
     *
     * @param id リクエストパラメータで送られてくる従業員ID
     * @param model 従業員情報を格納するためのリクエストパラメータ
     * @param form 扶養人数更新に使用するFormクラス
     * @return 従業員の詳細画面にフォワード
     */
    @GetMapping("/showDetail")
    public String showDetail(String id, Model model, UpdateEmployeeForm form) {
        Employee employee = employeeService.showDetail(Integer.parseInt(id));
        model.addAttribute("employee", employee);
        return "employee/detail";
    }
}
