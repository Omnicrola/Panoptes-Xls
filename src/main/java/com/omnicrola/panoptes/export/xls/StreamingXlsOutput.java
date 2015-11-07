package com.omnicrola.panoptes.export.xls;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by omnic on 11/7/2015.
 */
public class StreamingXlsOutput implements StreamingOutput {
    private XSSFWorkbook workbook;

    public StreamingXlsOutput(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException, WebApplicationException {
        workbook.write(outputStream);
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }
}
