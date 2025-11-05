package dev.matheuslf.desafio.inscritos.model;

public enum PriorityTask {
    LOW("Baixa"),
    MEDIUM("Média"),
    HIGH("Alta");

    private String priority;

    PriorityTask(String priority){
        this.priority = priority;
    }
}
