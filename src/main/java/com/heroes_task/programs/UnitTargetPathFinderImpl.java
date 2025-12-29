package com.heroes_task.programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.EdgeDistance;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {
    private static final int W = 27;
    private static final int H = 21;
    private static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    @Override
    public List<Edge> getTargetPath(Unit attackUnit, Unit targetUnit, List<Unit> existingUnitList) {
        int startX = attackUnit.getxCoordinate();
        int startY = attackUnit.getyCoordinate();

        int targetX = targetUnit.getxCoordinate();
        int targetY = targetUnit.getyCoordinate();

        Set<String> unitPositions = new HashSet<>();
        for (Unit unit : existingUnitList) {
            if (unit != attackUnit && unit != targetUnit && unit.isAlive()) {
                unitPositions.add(unit.getxCoordinate() + "," + unit.getyCoordinate());
            }
        }

        int[][] distances = new int[W][H];
        boolean[][] visited = new boolean[W][H];
        Edge[][] predecessors = new Edge[W][H];

        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                distances[x][y] = Integer.MAX_VALUE;
            }
        }
        distances[startX][startY] = 0;

        Comparator<EdgeDistance> comparator = Comparator.comparingInt(EdgeDistance::getDistance);
        PriorityQueue<EdgeDistance> priorityQueue = new PriorityQueue<>(comparator);
        priorityQueue.add(new EdgeDistance(startX, startY, 0));

        while (!priorityQueue.isEmpty()) {
            EdgeDistance current = priorityQueue.poll();
            int currentX = current.getX();
            int currentY = current.getY();

            if (visited[currentX][currentY]) {
                continue;
            }
            visited[currentX][currentY] = true;

            if (currentX == targetX && currentY == targetY) {
                break;
            }

            for (int[] direction : DIRS) {
                int newX = currentX + direction[0];
                int newY = currentY + direction[1];

                if (newX >= 0 && newX < W && newY >= 0 && newY < H) {
                    if (!unitPositions.contains(newX + "," + newY) || (newX == targetX && newY == targetY)) {
                        int newDistance = distances[currentX][currentY] + 1;
                        if (newDistance < distances[newX][newY]) {
                            distances[newX][newY] = newDistance;
                            predecessors[newX][newY] = new Edge(currentX, currentY);
                            priorityQueue.add(new EdgeDistance(newX, newY, newDistance));
                        }
                    }
                }
            }
        }

        List<Edge> path = new ArrayList<>();
        if (predecessors[targetX][targetY] == null) {
            return path;
        }

        int currX = targetX;
        int currY = targetY;

        while (currX != startX || currY != startY) {
            path.add(new Edge(currX, currY));
            Edge predecessor = predecessors[currX][currY];
            currX = predecessor.getX();
            currY = predecessor.getY();
        }

        path.add(new Edge(startX, startY));
        Collections.reverse(path);
        return path;
    }
}
