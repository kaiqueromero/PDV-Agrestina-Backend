package com.agrestina.mail;

import com.agrestina.dto.statistics.InventoryReport;
import com.agrestina.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ReportEmail {

    @Autowired
    private EmailService sender;

    public void send(InventoryReport inventory, BigDecimal billing) {
        String emailContent = """
                Olá!
                
                Seus relatórios foram gerados e seguem abaixo!
                """ +
                "\n\n\nProdutos Fora de estoque: " + inventory.missingProducts()
                .stream().map(pos -> "\nId: " + pos.id() + "\nNome: " + pos.name() + "\nDescrição: " + pos.description() + "\nAtivo: " + pos.active())
                .reduce("", (partialString, element) -> partialString + element) +
                "\n\n\nFaturamento do dia: " + billing;
        sender.sendEmail(
                "Relatórios gerados",
                "kaiqueebr729@gmail.com",
                emailContent
        );

    }
}
