package com.example.service;

import com.example.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理者情報を操作するサービスです.
 *
 * @author takuto.itami
 */
@Service
@Transactional
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepositoryrepository;


}
