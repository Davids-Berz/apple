package com.apple.apple.view.pdf;

import com.apple.apple.models.entity.Factura;
import com.apple.apple.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.Map;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

    private static final Logger LOG = LoggerFactory.getLogger(FacturaPdfView.class);

    @Autowired
    private MessageSource messageSource;


    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        // el metodo se obtiene desde la superclase
        MessageSourceAccessor message = getMessageSourceAccessor();

        Factura factura = (Factura) model.get("factura");

        PdfPCell cell = null;
        cell = new PdfPCell(new Phrase("Datos del Cliente"));
        cell.setBackgroundColor(new Color(140,222,238, 93));
        cell.setPadding(8f);

        PdfPTable table = new PdfPTable(1);
        table.setSpacingAfter(20);
        table.addCell(cell);
        table.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
        table.addCell(factura.getCliente().getEmail());

        PdfPTable table2 = new PdfPTable(1);
        table2.setSpacingAfter(20);
        table2.addCell("Datos de la Factura");
        table2.addCell(message.getMessage("text.cliente.factura.folio")+": " + factura.getId());
        table2.addCell(message.getMessage("text.cliente.factura.descripcion")+": " + factura.getDescripcion());
        table2.addCell(message.getMessage("text.cliente.factura.fecha")+": " + factura.getCreateAt());

        document.add(table);
        document.add(table2);

        PdfPTable table3 = new PdfPTable(4);
        table3.setWidths(new float[]{3.5f,1,1,1});
        table3.addCell("Producto");
        table3.addCell("Precio");
        table3.addCell("Cantidad");
        table3.addCell("Total");

        for (ItemFactura item: factura.getItems()) {
            table3.addCell(item.getProducto().getNombre());
            table3.addCell(item.getProducto().getPrecio().toString());
            table3.addCell(item.getCantidad().toString());
            table3.addCell(item.calcularImporte().toString());
        }

        cell = new PdfPCell(new Phrase("total: "));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table3.addCell(cell);
        table3.addCell(factura.getTotal().toString());

        document.add(table3);
        LOG.info("diseño itext: https://developers.itextpdf.com/examples/tables-itext5");
    }
}
