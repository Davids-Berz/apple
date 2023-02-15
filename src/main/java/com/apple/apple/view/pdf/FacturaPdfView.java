package com.apple.apple.view.pdf;

import com.apple.apple.models.entity.Factura;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.util.Map;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Factura factura = (Factura) model.get("factura");

        PdfPTable table = new PdfPTable(1);
        table.addCell("Datos del Cliente");
        table.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
        table.addCell(factura.getCliente().getEmail());

        PdfPTable table2 = new PdfPTable(1);
        table.addCell("Datos de la Factura");
        table.addCell("Folio: " + factura.getId());
        table.addCell("Descripcion: " + factura.getDescripcion());
        table.addCell("Fecha: " + factura.getCreateAt());

        document.add(table);
        document.add(table2);
    }
}
