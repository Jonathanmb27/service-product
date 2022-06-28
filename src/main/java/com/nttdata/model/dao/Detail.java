package com.nttdata.model.dao;

import com.nttdata.model.dao.util.PayMode;
import com.nttdata.model.dao.util.TypeOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detail {
    private BigDecimal amount;
    private PayMode payMode;
    private TypeOperation typeOperation;

}
