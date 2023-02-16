package com.apple.apple.view.csv;

import com.apple.apple.models.entity.Cliente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

import java.util.Map;

@Component("listar.csv")
public class ClienteCsvView extends AbstractView {

    public ClienteCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
        response.setContentType(getContentType());
        Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");

        ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        // the header elements are used to map the bean values to each column (names must match)
        String[] header = {"id", "nombre", "apellido", "email", "createAt"};
        // write the header
        beanWriter.writeHeader(header);

        // write the beans
        for (Cliente cliente : clientes) {
            beanWriter.write(cliente, header);
        }
        beanWriter.close();

    }
}
