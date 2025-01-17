package com.agrestina.mail;

import com.agrestina.domain.order.Order;
import com.agrestina.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRealizedEmail {

    @Autowired
    private EmailService sender;

    public void send(Order order) {
        String emailContent = "Olá! " + order.getClient().getName() +"." + "\nSeu pedido foi registrado com sucesso." + "\nData: " + order.getDate() +
                "\n\n\nItens: " + order.getItems().stream()
                .map(item -> "\n\nNome: " + item.getProduct().getName() +
                        "\nCategoria: " + item.getProduct().getCategory() +
                        "\nQuantidade: " + item.getQuantity() +
                        "\nPreço Unitário: " + item.getProduct().getPrice())
                .reduce("", (partialString, element) -> partialString + element) +
                "\n\nObrigado por comprar conosco!";

        sender.sendEmail(
                "Agrestina agradece o seu pedido! ",
                order.getClient().getEmail(),
                emailContent
        );
    }
}

