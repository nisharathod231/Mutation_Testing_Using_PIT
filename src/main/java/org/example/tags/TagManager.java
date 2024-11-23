package org.example.tags;

import org.example.transactions.Transaction;

import java.util.*;
import java.util.stream.Collectors;

class TagManager {
    private final Map<String, TransactionTag> tags;
    private final Map<Transaction, Set<TransactionTag>> transactionTags;

    public TagManager() {
        this.tags = new HashMap<>();
        this.transactionTags = new HashMap<>();
    }

    public void createTag(String name, String description, String color) {
        tags.put(name, new TransactionTag(name, description, color));
    }

    public void tagTransaction(Transaction transaction, String... tagNames) {
        Set<TransactionTag> transactionTagSet = transactionTags.computeIfAbsent(
                transaction, k -> new HashSet<>());

        Arrays.stream(tagNames)
                .map(tags::get)
                .filter(Objects::nonNull)
                .forEach(transactionTagSet::add);
    }

    public List<Transaction> findTransactionsByTag(String tagName) {
        return transactionTags.entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .anyMatch(tag -> tag.getName().equals(tagName)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Set<TransactionTag> getTransactionTags(Transaction transaction) {
        return new HashSet<>(transactionTags.getOrDefault(transaction, new HashSet<>()));
    }

    public Map<String, List<Transaction>> groupTransactionsByTag() {
        Map<String, List<Transaction>> result = new HashMap<>();
        tags.keySet().forEach(tagName ->
                result.put(tagName, findTransactionsByTag(tagName)));
        return result;
    }
}
