package ru.saptan.yandextranslator.data.mappers;


public interface Mapper<From, To> {
    To map(From from);
}