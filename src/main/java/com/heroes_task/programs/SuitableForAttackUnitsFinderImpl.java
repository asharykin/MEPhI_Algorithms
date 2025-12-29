package com.heroes_task.programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> suitableUnits = new ArrayList<>();

        for (List<Unit> row : unitsByRow) {
            Unit bestUnit = null;
            int bestX = isLeftArmyTarget ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            for (Unit unit : row) {
                if (unit != null && unit.isAlive()) {
                    int x = unit.getxCoordinate();

                    if (isLeftArmyTarget) {
                        if (x >= 0 && x <= 2 && x > bestX) {
                            bestX = x;
                            bestUnit = unit;
                        }
                    } else {
                        if (x >= 24 && x <= 26 && x < bestX) {
                            bestX = x;
                            bestUnit = unit;
                        }
                    }
                }
            }

            if (bestUnit != null) {
                suitableUnits.add(bestUnit);
            }
        }

        return suitableUnits;
    }
}
