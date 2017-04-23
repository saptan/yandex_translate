package ru.saptan.yandextranslator.data.repository.specifications;


public interface SqlSpecification extends Specification {
    String toSqlQuery();
}
