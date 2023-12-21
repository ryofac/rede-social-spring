package com.ryofac.livbook.livbook.Exceptions.ProfileException;

import com.ryofac.livbook.livbook.Exceptions.AppException;

// Exceção que encapsula as exceções relacionadas a perfis
public class ProfileException extends AppException {
    public ProfileException(String msg) {
        super(msg);
    }

}
