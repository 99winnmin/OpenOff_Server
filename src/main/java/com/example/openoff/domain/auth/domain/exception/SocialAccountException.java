package com.example.openoff.domain.auth.domain.exception;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;

public class SocialAccountException extends BusinessException {
    public SocialAccountException(Error error) {
        super(error);
    }
}
