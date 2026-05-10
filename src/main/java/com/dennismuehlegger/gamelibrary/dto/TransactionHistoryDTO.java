package com.dennismuehlegger.gamelibrary.dto;

import com.dennismuehlegger.gamelibrary.enums.TransactionResult;

import java.util.List;

public class TransactionHistoryDTO {
    private TransactionResult result;
    private List<TransactionItemDTO> transactions;

    public TransactionHistoryDTO() {
    }

    public TransactionHistoryDTO(TransactionResult result, List<TransactionItemDTO> transactions) {
        this.result = result;
        this.transactions = transactions;
    }

    public TransactionResult getResult() {
        return result;
    }

    public void setResult(TransactionResult result) {
        this.result = result;
    }

    public List<TransactionItemDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionItemDTO> transactions) {
        this.transactions = transactions;
    }
}
