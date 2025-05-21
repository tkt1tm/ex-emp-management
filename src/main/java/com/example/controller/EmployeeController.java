package com.example.controller;

import com.example.domain.Employee;
import com.example.form.UpdateEmployeeForm;
import com.example.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
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

    @Autowired
    private HttpSession session;

    /**
     * 従業員一覧を出力します.
     *
     * @param model リクエストパラメータを格納
     * @return 従業員一覧画面にフォワード/未ログインの場合はログイン画面にリダイレクトします
     */
    @GetMapping("/showList")
    public String showList(Model model) {
        if (session.getAttribute("administratorName") == null) {
            return "redirect:/";
        }

        List<Employee> employees = employeeService.showList();
        model.addAttribute("employeeList", employees);
        return "employee/list";
    }

    /**
     * 従業員の詳細画面を表示します.
     *
     * @param id リクエストパラメータで送られてくる従業員ID
     * @param form 扶養人数更新に使用するFormクラス
     * @return 従業員の詳細画面にフォワード/未ログインの場合はログイン画面にリダイレクトします
     */
    @GetMapping("/showDetail")
    public String showDetail(String id, UpdateEmployeeForm form) {
        if (session.getAttribute("administratorName") == null) {
            return "redirect:/";
        }

        Employee employee = employeeService.showDetail(Integer.parseInt(id));

        BeanUtils.copyProperties(employee, form);
        form.setHireDate(employee.getHireDate().toString());
        form.setSalary(employee.getSalary().toString());
        form.setDependentsCount(employee.getDependentsCount().toString());

        return "employee/detail";
    }

    /**
     * 従業員の扶養人数を更新します.
     *
     * @param form リクエストパラメータ
     * @param result エラーメッセージを格納
     * @return 従業員詳細画面までリダイレクト/扶養人数が空欄の場合は詳細画面へフォワード/未ログインの場合はログイン画面にリダイレクトします
     */
    @PostMapping("/update")
    public String update(@Validated UpdateEmployeeForm form, BindingResult result) {
        if (session.getAttribute("administratorName") == null) {
            return "redirect:/";
        }

        if (result.hasErrors()) {
            return "employee/detail";
        }

        Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
        BeanUtils.copyProperties(form, employee);
        employee.setHireDate(LocalDate.parse(form.getHireDate()));
        employee.setSalary(Integer.parseInt(form.getSalary()));
        employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));

        employeeService.update(employee);
        return "redirect:/employee/showList";
    }
}
