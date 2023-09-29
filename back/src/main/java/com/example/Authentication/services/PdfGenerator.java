package com.example.Authentication.services;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGenerator {


    public static String PDF_EXTENSION = ".pdf";

    //Metodo Retornar o arquivo, a principio salva na pasta TEMP
    public static byte[] pdf(Object obj) throws Exception {
        // Configuração do template resolver
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        // Configuração do template engine
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Gerar o PDF
        byte[] pdfContent = gerarPDFTermo(templateEngine, obj);

        return pdfContent;
    }

    public static byte[] gerarPDFTermo(TemplateEngine templateEngine, Object object) throws Exception {
        final Context context = new Context();

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = dateTime.format(formatter);

        context.setVariable("object", object);
        context.setVariable("formattedDateTime", formattedDateTime);

        String processedHtml = templateEngine.process("template/relatorio_despesas/index", context);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream, false);
            renderer.finishPDF();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new Exception("Erro ao gerar PDF");
        }
    }
}
