package com.nttdata.util.reporte;

import com.nttdata.model.dao.Product;

import java.io.ByteArrayInputStream;

public interface GeneratePdfReport {
    ByteArrayInputStream clientReport(Product product);
}
