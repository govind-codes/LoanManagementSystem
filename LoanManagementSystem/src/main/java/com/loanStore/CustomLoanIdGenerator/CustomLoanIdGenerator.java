package com.loanStore.CustomLoanIdGenerator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomLoanIdGenerator implements IdentifierGenerator {
    private static final long serialVersionUID = 6166749430367858563L;
    private static int counter = 0;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String loanId = "L" + (++counter);
        return loanId;
    }	
}
