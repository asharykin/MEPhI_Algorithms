package com.heroes_task.programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {
    private static final int M = 11;
    private static final int W = 3;
    private static final int H = 21;

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        Comparator<Unit> comparator = (a, b) -> {
            double effA = (double) (a.getBaseAttack() + a.getHealth()) / a.getCost();
            double effB = (double) (b.getBaseAttack() + b.getHealth()) / b.getCost();
            return Double.compare(effB, effA);
        };
        unitList.sort(comparator);

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < W; i++) {
            for (int j = 0; j < H; j++) {
                edges.add(new Edge(i, j));
            }
        }
        Collections.shuffle(edges);

        List<Unit> armyUnits = new ArrayList<>();
        int armyPoints = 0;
        int edgeIndex = 0;

        for (Unit unit : unitList) {
            for (int i = 0; (i < M) && (armyPoints + unit.getCost() <= maxPoints); i++, edgeIndex++) {
                Edge edge = edges.get(edgeIndex);

                Unit clonedUnit = new Unit(
                        unit.getName() + " " + (i + 1),
                        unit.getUnitType(),
                        unit.getHealth(),
                        unit.getBaseAttack(),
                        unit.getCost(),
                        unit.getAttackType(),
                        unit.getAttackBonuses(),
                        unit.getDefenceBonuses(),
                        edge.getX(),
                        edge.getY()
                );

                armyUnits.add(clonedUnit);
                armyPoints += unit.getCost();
            }
        }

        Army army = new Army(armyUnits);
        army.setPoints(armyPoints);
        return army;
    }
}