package com.heroes_task.programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog;

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        List<Unit> playerUnits = new ArrayList<>();
        for (Unit unit : playerArmy.getUnits()) {
            if (unit.isAlive()) {
                playerUnits.add(unit);
            }
        }

        List<Unit> computerUnits = new ArrayList<>();
        for (Unit unit : computerArmy.getUnits()) {
            if (unit.isAlive()) {
                computerUnits.add(unit);
            }
        }

        Comparator<Unit> comparator = Comparator.comparingInt(Unit::getBaseAttack).reversed();

        while (!playerUnits.isEmpty() && !computerUnits.isEmpty()) {
            PriorityQueue<Unit> playerQueue = new PriorityQueue<>(comparator);
            PriorityQueue<Unit> computerQueue = new PriorityQueue<>(comparator);

            playerQueue.addAll(playerUnits);
            computerQueue.addAll(computerUnits);

            while (!playerQueue.isEmpty() || !computerQueue.isEmpty()) {
                while (!playerQueue.isEmpty()) {
                    Unit unit = playerQueue.poll();

                    if (unit.isAlive()) {
                        Unit target = unit.getProgram().attack();
                        printBattleLog.printBattleLog(unit, target);
                        break;
                    }
                }
                while (!computerQueue.isEmpty()) {
                    Unit unit = computerQueue.poll();

                    if (unit.isAlive()) {
                        Unit target = unit.getProgram().attack();
                        printBattleLog.printBattleLog(unit, target);
                        break;
                    }
                }
            }

            playerUnits = new ArrayList<>();
            for (Unit unit : playerArmy.getUnits()) {
                if (unit.isAlive()) {
                    playerUnits.add(unit);
                }
            }

            computerUnits = new ArrayList<>();
            for (Unit unit : computerArmy.getUnits()) {
                if (unit.isAlive()) {
                    computerUnits.add(unit);
                }
            }
        }
    }
}