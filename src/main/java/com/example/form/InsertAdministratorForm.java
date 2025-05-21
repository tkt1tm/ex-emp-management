package com.example.form;

import jakarta.validation.constraints.NotBlank;

/**
 * 管理者情報登録時に使用するフォーム.
 *
 * @author takuto.itami
 */
public class InsertAdministratorForm {
    /** 名前 */
    @NotBlank(message = "氏名を入力してください")
    private String name;
    /** メールアドレス */
    @NotBlank(message = "メールアドレスを入力してください")
    private String mailAddress;
    @NotBlank(message = "パスワードを入力してください")
    /** パスワード */
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "InsertAdministratorForm{" +
                "name='" + name + '\'' +
                ", mailAddress='" + mailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
