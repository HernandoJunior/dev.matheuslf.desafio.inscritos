package dev.matheuslf.desafio.inscritos.model;


public enum StatusTask {
    TODO("A fazer"),
    DOING("Em construcao"),
    DONE("Feito");

    private String status;

    StatusTask(String status) {
        this.status = status;
    }
}
