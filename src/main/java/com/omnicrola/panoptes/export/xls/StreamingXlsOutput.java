package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by omnic on 11/7/2015.
 */
public class StreamingXlsOutput implements StreamingOutput {
    private IWorkbook workbook;

    public StreamingXlsOutput(IWorkbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException, WebApplicationException {
        workbook.write(outputStream);
    }

    public IWorkbook getWorkbook() {
        return workbook;
    }
}
