package com.elm0n0.tienda.auth.validator;

import com.elm0n0.tienda.auth.dto.request.RegisterRequest;

public interface AuthValidator {
    void validar(RegisterRequest usuario);
}
