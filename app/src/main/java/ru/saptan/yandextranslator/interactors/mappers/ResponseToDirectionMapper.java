package ru.saptan.yandextranslator.interactors.mappers;


import java.util.ArrayList;
import java.util.List;

import ru.saptan.yandextranslator.data.entity.Direction;

public abstract class ResponseToDirectionMapper {

    public static List<Direction> map(List<String> codes) {

        List<Direction> directions = new ArrayList<>();

        for (int i = 0; i < codes.size(); i++) {
            String[] codeDirection = codes.get(i).split("-");
            Direction direction = new Direction();
            direction.setId(i);
            direction.setCodeInputLanguage(codeDirection[0]);
            direction.setCodeOutputLanguage(codeDirection[1]);

            directions.add(direction);
        }

        return directions;
    }
}
