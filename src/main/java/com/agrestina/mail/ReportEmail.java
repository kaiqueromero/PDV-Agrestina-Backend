package com.agrestina.mail;

import com.agrestina.dto.statistics.BillingReport;
import com.agrestina.dto.statistics.InventoryReport;
import com.agrestina.dto.statistics.ProductReport;
import com.agrestina.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportEmail {

    @Autowired
    private EmailService sender;

    public void send(InventoryReport inventory, ProductReport billing) {
        String emailContent = """
                Olá!
                
                Seus relatórios foram gerados e seguem abaixo!
                """ +
                "\n\n\nProdutos Fora de estoque: " + inventory.missingProducts()
                .stream().map(pos -> "\nId: " + pos.id() + "\nNome: " + pos.name() + "\nDescrição: " + pos.description() + "\nAtivo: " + pos.active())
                .reduce("", (partialString, element) -> partialString + element) +
                "\n\n\nFaturamento do dia: " + billing.totalRevenue() +
                "\n\nEstatísticas: " + billing.statistics().stream()
                .map(stat -> "\nProduto: " + stat.name() +
                        "\nQuantidade Vendida: " + stat.quantity() +
                        "\nFaturamento: " + stat.revenue())
                .reduce("", (partialString, element) -> partialString + element);

        sender.sendEmail(
                "Relatórios gerados",
                "kaiqueebr729@gmail.com",
                emailContent
        );

    }
}
