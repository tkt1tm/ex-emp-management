package com.example.repository;

import com.example.domain.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * administratorsテーブルを操作するリポジトリ.
 *
 * @author takuto.itami
 */
@Repository
public class AdministratorRepository {

    /** AdministratorオブジェクトのRowMapper */
    private final static RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
        Administrator administrator = new Administrator(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("mail_address"),
                rs.getString("password")
        );
        return  administrator;
    };

    /** 参照情報が注入されたNamedParameterJdbcTemplate型のオブジェクト */
    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * 管理者情報を挿入します.
     *
     * @param administrator 挿入する管理者情報
     */
    public void insert(Administrator administrator) {
        String sql = "INSERT INTO administrators (name, mail_address, password) VALUES (:name, :mailAddress, :password);";

        SqlParameterSource param = new MapSqlParameterSource().addValue("name", administrator.getName())
                .addValue("mailAddress", administrator.getMailAddress())
                .addValue("password", administrator.getPassword());

        template.update(sql, param);
    }

    /**
     * メールアドレスとパスワードから管理者情報を取得する.
     *
     * @param mailAddress 検索するメールアドレス
     * @param password 検索するパスワード
     * @return 検索で取得した管理者情報(存在しない場合はnull)
     */
    public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
        String sql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address = :mailAddress AND password = :password;";

        SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress)
                .addValue("password", password);

        List<Administrator> administrators = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);

        if (administrators.isEmpty()) {
            return null;
        }

        return administrators.get(0);
    }
}
