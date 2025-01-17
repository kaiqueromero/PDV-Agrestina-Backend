package com.agrestina.domain.order;

public enum OrderStatus {
        INICIADO("Iniciado"),
        EM_ANDAMENTO("Em andamento"),
        FINALIZADO("Finalizado"),
        CANCELADO("Cancelado"),
        AGUARDANDO_PAGAMENTO("Aguardando pagamento"),
        PAGO("Pago");

        private final String description;

        OrderStatus(String description) {
                this.description = description;
        }

        public String getDescription() {
                return description;
        }
}