package com.elm0n0.tienda.api.auth.validator;

import com.elm0n0.tienda.api.auth.dto.request.RegisterRequest;

public interface AuthValidator {
    void validar(RegisterRequest usuario);
}
