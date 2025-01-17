package com.agrestina.domain.payment;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    DINHEIRO,
    PIX,
    CARTAO_DEBITO,
    CARTAO_CREDITO,
    BOLETO_BANCARIO,
    CHEQUE,
    VALE_ALIMENTACAO,
    A_PRAZO
}
