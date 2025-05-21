package com.example.controller;

import com.example.domain.Administrator;
import com.example.form.InsertAdministratorForm;
import com.example.form.LoginForm;
import com.example.service.AdministratorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理者情報の操作を行うコントローラです.
 *
 * @author takuto.itami
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private HttpSession session;

    /**
     * ログイン画面を表示させます.
     *
     * @param form リクエストパラメータを格納
     * @return ログイン画面へ遷移させます
     */
    @GetMapping("/")
    public String toLogin(LoginForm form) {
        return "administrator/login";
    }

    /**
     * 管理者のログイン処理を行います.
     *
     * @param form 入力されたメールアドレスとパスワードが格納されている
     * @param model リクエストパラメータを格納(エラーメッセージ用)
     * @return ログイン成功時は従業員一覧へフォワード/失敗時はログイン画面にリダイレクト
     */
    @PostMapping("/login")
    public String login(LoginForm form, Model model) {
        Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());

        if (administrator == null) {
            model.addAttribute("errorMsg", "メールアドレスまたはパスワードが不正です。");
            return "administrator/login";
        }

        session.setAttribute("administratorName", administrator.getName());

        return "redirect:/employee/showList";
    }

    /**
     * 管理者登録画面に遷移させます.
     *
     * @param form リクエストパラメータを格納するFormクラスのオブジェクト
     * @return 管理者登録画面のビューを返します
     */
    @GetMapping("/toInsert")
    public String toInsert(InsertAdministratorForm form) {
        return "administrator/insert";
    }

    /**
     * 管理者情報を登録します.
     *
     * @param form リクエストパラメータ
     * @return ログイン画面にリダイレクトします
     * */
    @PostMapping("/insert")
    public String insert(InsertAdministratorForm form) {
        Administrator administrator = new Administrator();
        BeanUtils.copyProperties(form, administrator);
        administratorService.insert(administrator);
        return "redirect:/";
    }

    /**
     * ログアウトをします.
     *
     * @param form ログイン画面へのリダイレクトに必要なフォーム
     * @return ログイン画面にリダイレクトします
     */
    @GetMapping("/logout")
    public String logout(LoginForm form) {
        session.invalidate();
        return "redirect:/";
    }
}
