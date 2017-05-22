package service;

import helpers.*;

import java.util.Arrays;

public class WariantA {

    public enum TYPE {
        OBSTACLE,
        NONE
    }

    private MatrixSpace matrixSpace;

    private IterationIntegralContainer iterationIntegralContainer;

    private Obstacle obstaclePoints;

    private TYPE type;

    private Double Q = -1.0;

    private final Double ni = 1.0;

    private final Double density = 1.0;

    // Dla zadania 1
    public WariantA(MatrixSpace matrixSpace) {
        this.matrixSpace = matrixSpace;
        this.iterationIntegralContainer = new IterationIntegralContainer();
        this.type = TYPE.NONE;

        evaluateInitialEdges();
    }

    public WariantA(MatrixSpace matrixSpace, Double Q, TYPE type) {
        this.matrixSpace = matrixSpace;
        this.iterationIntegralContainer = new IterationIntegralContainer();
        this.type = type;
        this.Q = Q;

        evaluateInitialWithObstacle();
    }

    public void evaluateInitialWithObstacle() {

        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();
        //prawy brzeg
        for (int i = 0; i < 91; i++) {
            Double value = potentialPoints[i][0].getY();
            //Wirowosc
            potentialPoints[i][0].setWir(
                    (Q / (2.0 * ni)) * (2.0 * value - 0.0 - 0.9)
            );

            //Potencjal
            potentialPoints[i][0].setValue(
                    (Q / (2.0 * ni)) * (Math.pow(value, 3.0) / 3.0 - (Math.pow(value, 2.0) / 2.0) * (0.0 + 0.9) + (0.0 * 0.9 * value))
            );
        }

        //lewy brzeg
        for (int i = 0; i < 91; i++) {
            Double value = potentialPoints[i][300].getY();
            //Wirowosc
            potentialPoints[i][300].setWir(
                    (Q / (2.0 * ni)) * (2.0 * value - 0.0 - 0.9)
            );

            //Potencjal
            potentialPoints[i][300].setValue(
                    (Q / (2.0 * ni)) * (Math.pow(value, 3.0) / 3.0 - (Math.pow(value, 2.0) / 2.0) * (0.0 + 0.9) + (0.0 * 0.9 * value))
            );
        }

        //dolny brzeg
//        Double potMin = potentialPoints[90][0].getValue();
//        for (int i = 0; i < 301; i++) {
//            potentialPoints[90][i].setValue(potMin);
//        }
//
//        //gorny brzeg i zastawka
        Double potMax = potentialPoints[0][0].getValue();
        for (int i = 0; i < 301; i++) {
            potentialPoints[0][i].setValue(potMax);
        }

        for (int i = 0; i < 91; i++) {
            for (int j = 0; j < 301; j++) {
                if (potentialPoints[i][j].getObstacle()) {
                    potentialPoints[i][j].setValue(potMax);
                    potentialPoints[i][j].setWir(0.0);
                }
            }
        }

        for (int i = 1; i < 90; i++) {
            for (int j = 1; j < 300; j++) {
                if (!potentialPoints[i][j].getObstacle()) {
                    potentialPoints[i][j].setValue((Q / (2.0 * ni)) * (Math.pow(potentialPoints[i][j].getY(), 3.0) / 3.0 - (Math.pow(potentialPoints[i][j].getY(), 2.0) / 2.0) * (0.0 + 0.9) + (0.0 * 0.9 * potentialPoints[i][j].getY())));

                }
            }
        }

        for (int i = 0; i <= 90; i++) {
            for (int j = 0; j <= 300; j++) {
                if (!potentialPoints[i][j].getObstacle()) {
                    potentialPoints[i][j].setWir(
                            (Q / (2.0 * ni)) * (2.0 * potentialPoints[i][j].getY() - 0.0 - 0.9)
                    );
                }
            }
        }

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
        evaluateWirEdge();
    }

    public void evaluateWirEdge() {

        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        // Gorny A i G
        for (int i = 0; i <= 85; i++) {
            potentialPoints[0][i].setWir(
                    2*(potentialPoints[1][i].getValue() - potentialPoints[0][i].getValue())/(matrixSpace.getJump()*matrixSpace.getJump())
            );
        }

        for (int i = 116; i < 301; i++) {
            potentialPoints[0][i].setWir(
                    2*(potentialPoints[1][i].getValue() - potentialPoints[0][i].getValue())/(matrixSpace.getJump()*matrixSpace.getJump())
            );
        }

        //Dolny
        for (int i = 0; i < 301; i++) {
            potentialPoints[90][i].setWir(
                    2*(potentialPoints[89][i].getValue() - potentialPoints[90][i].getValue())/(matrixSpace.getJump()*matrixSpace.getJump())
            );
        }

        //Zastawka B
        for (int i = 1; i < 20; i++) {
            potentialPoints[i][85].setWir(
                    2*(potentialPoints[i][84].getValue() - potentialPoints[i][85].getValue())/(matrixSpace.getJump()*matrixSpace.getJump())
            );
        }

        //Zastawka D
        for (int i = 20; i <= 40; i++) {
            potentialPoints[i][101].setWir(
                    2*(potentialPoints[i][100].getValue() - potentialPoints[i][101].getValue())/(matrixSpace.getJump()*matrixSpace.getJump())
            );
        }

        //Zastawka F
        for (int i = 0; i < 40; i++) {
            potentialPoints[i][116].setWir(
                    2*(potentialPoints[i][117].getValue() - potentialPoints[i][116].getValue())/(matrixSpace.getJump()*matrixSpace.getJump())
            );
        }

        //Zastawka C
        for (int i = 85; i <= 101; i++) {
            potentialPoints[20][i].setWir(
                    2*(potentialPoints[21][i].getValue() - potentialPoints[20][i].getValue())/(matrixSpace.getJump()*matrixSpace.getJump())
            );
        }

        //Zastawka E
        for (int i = 101; i <= 116; i++) {
            potentialPoints[40][i].setWir(
                    2*(potentialPoints[41][i].getValue() - potentialPoints[40][i].getValue())/(matrixSpace.getJump()*matrixSpace.getJump())
            );
        }

        //Punkt wspolne AB, BC, CD, DE, EF, FG
        potentialPoints[20][85].setWir(
                (potentialPoints[19][85].getValue() + potentialPoints[20][86].getValue())/2.0
        );

        potentialPoints[40][101].setWir(
                (potentialPoints[39][101].getValue() + potentialPoints[40][102].getValue())/2.0
        );

        potentialPoints[40][116].setWir(
                (potentialPoints[39][116].getValue() + potentialPoints[40][115].getValue())/2.0
        );

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    private void evaluateInitialEdges() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        //prawy brzeg
        for (int i = 0; i < 91; i++) {
            Double value = potentialPoints[i][0].getY();
            //Wirowosc
            potentialPoints[i][0].setWir(
                    (Q / (2.0 * ni)) * (2.0 * value - 0.0 - 0.9)
            );

            //Potencjal
            potentialPoints[i][0].setValue(
                    (Q / (2.0 * ni)) * (Math.pow(value, 3.0) / 3.0 - (Math.pow(value, 2.0) / 2.0) * (0.0 + 0.9) + (0.0 * 0.9 * value))
            );
        }

        //lewy brzeg
        for (int i = 0; i < 91; i++) {
            Double value = potentialPoints[i][300].getY();
            //Wirowosc
            potentialPoints[i][300].setWir(
                    (Q / (2.0 * ni)) * (2.0 * value - 0.0 - 0.9)
            );

            //Potencjal
            potentialPoints[i][300].setValue(
                    (Q / (2.0 * ni)) * (Math.pow(value, 3.0) / 3.0 - (Math.pow(value, 2.0) / 2.0) * (0.0 + 0.9) + (0.0 * 0.9 * value))
            );
        }

        //gorny brzeg
        for (int i = 0; i < 301; i++) {
            Double value = potentialPoints[0][i].getY();
            //Wirowosc
            potentialPoints[0][i].setWir(
                    (Q / (2.0 * ni)) * (2.0 * value - 0.0 - 0.9)
            );

            //Potencjal
            potentialPoints[0][i].setValue(
                    (Q / (2.0 * ni)) * (Math.pow(value, 3.0) / 3.0 - (Math.pow(value, 2.0) / 2.0) * (0.0 + 0.9) + (0.0 * 0.9 * value))
            );
        }

        //dolny brzeg
        for (int i = 0; i < 301; i++) {
            Double value = potentialPoints[90][i].getY();
            //Wirowosc
            potentialPoints[90][i].setWir(
                    (Q / (2.0 * ni)) * (2.0 * value - 0.0 - 0.9)
            );

            //Potencjal
            potentialPoints[90][i].setValue(
                    (Q / (2.0 * ni)) * (Math.pow(value, 3.0) / 3.0 - (Math.pow(value, 2.0) / 2.0) * (0.0 + 0.9) + (0.0 * 0.9 * value))
            );
        }

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    private Double evaluateVelocity(Double y) {
        return (Q / (2 * ni)) * (y - 0.0) * (y - 0.9);
    }

    public void calculatePotential() {

        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        for (int i = potentialPoints.length - 2; i > 0; i--) {
            for (int j = 1; j < potentialPoints[0].length - 1; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()) {
                    Double value = (potentialPoints[i][j+1].getValue() + potentialPoints[i][j - 1].getValue() +
                            potentialPoints[i + 1][j].getValue() + potentialPoints[i-1][j].getValue() -
                            potentialPoints[i][j].getWir() * matrixSpace.getJump() * matrixSpace.getJump()
                    ) / 4.0;
                    potentialPoints[i][j].setValue(value);
                }
            }
        }

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    public void calculateWirowosc() {
        if (type == TYPE.OBSTACLE) {
            evaluateWirEdge();
        }
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        for (int i = potentialPoints.length - 2; i > 0; i--) {
            for (int j = 1; j < potentialPoints[0].length - 1; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()) {
                    Double value = ((potentialPoints[i][j+1].getWir() + potentialPoints[i][j - 1].getWir() +
                            potentialPoints[i + 1][j].getWir() + potentialPoints[i-1][j].getWir()) / 4.0) -
                            (1.0 / 16.0) * (
                                    (potentialPoints[i - 1][j].getValue() - potentialPoints[i + 1][j].getValue()) *
                                            (potentialPoints[i][j + 1].getWir() - potentialPoints[i][j - 1].getWir()) -
                                            (potentialPoints[i][j +1].getValue() - potentialPoints[i][j - 1].getValue()) *
                                                    (potentialPoints[i - 1][j].getWir() - potentialPoints[i + 1][j].getWir())
                            );

                    potentialPoints[i][j].setWir(value);
                }
            }
        }

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    public void generateStrumienAndWirowosc() {
        int x = 145;
        int y = 45;

        for (int i = 0; i < 100; i++) {
            calculateWirowosc();
            calculatePotential();
        }

        Double diffStr = 0.0;
        Double diffWir = 0.0;
        int k = 0;
        PotentialPoint potentialPoint = new PotentialPoint(matrixSpace.getDoubleMatrix().getMatrix()[y][x]);
        do {
            calculateWirowosc();
            calculatePotential();
            PotentialPoint newPoint = new PotentialPoint(matrixSpace.getDoubleMatrix().getMatrix()[y][x]);

            diffStr = Math.abs((potentialPoint.getValue() - newPoint.getValue()) / potentialPoint.getValue());
            diffWir = Math.abs((potentialPoint.getWir() - newPoint.getWir()) / potentialPoint.getWir());

            potentialPoint = newPoint;
            System.out.println("Iteration:" + k++ + " diffStr: " + diffStr + " diffWir: " + diffWir);
        } while (diffStr > 10e-7 || diffWir > 10e-7);

    }

    public VelocitiesByIteration generateVelocities() {

        int x = 50;
        VelocitiesByIteration velocitiesByIteration = new VelocitiesByIteration();
        PotentialPoint[] potentialPoints = new PotentialPoint[91];
        for (int i = 1; i < 90; i++) {
            potentialPoints[i] = matrixSpace.getDoubleMatrix().getMatrix()[i][x];

            Double velocity = (matrixSpace.getDoubleMatrix().getMatrix()[i - 1][x].getValue() - matrixSpace.getDoubleMatrix().getMatrix()[i + 1][x].getValue()) / (2 * matrixSpace.getJump());
            Double velocityAnal = evaluateVelocity(potentialPoints[i].getY());
            velocitiesByIteration.addVelocities(velocity, velocityAnal);

        }
        return velocitiesByIteration;
    }

    public PotentialAndWirContainer evaluatePotencjalAndWirowoscAt(int x) {

        PotentialPoint[] potentialPoints = new PotentialPoint[91];
        PotentialAndWirContainer potentialAndWirContainer = new PotentialAndWirContainer();

        for (int i = 90; i > -1; i--) {
            potentialPoints[i] = matrixSpace.getDoubleMatrix().getMatrix()[i][x];

            Double wirAnal = (Q / (2.0 * ni)) * (2.0 * potentialPoints[i].getY() - 0.0 - 0.9);
            Double potenAnal = (Q / (2.0 * ni)) * (Math.pow(potentialPoints[i].getY(), 3.0) / 3.0 - (Math.pow(potentialPoints[i].getY(), 2.0) / 2.0) * (0.0 + 0.9) + (0.0 * 0.9 * potentialPoints[i].getY()));

            potentialAndWirContainer.addPoint(91 - i, potentialPoints[i].getValue(), potenAnal, potentialPoints[i].getWir(), wirAnal);
        }

        return potentialAndWirContainer;
    }

    private Double calculateIntegralAtIteration() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        Double a = 0.0;

        for (int i = 1; i < potentialPoints.length - 1; i++) {
            for (int j = 1; j < potentialPoints[0].length - 1; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()) {
                    a += Math.pow(potentialPoints[i + 1][j].getValue() - potentialPoints[i - 1][j].getValue(), 2.0) +
                            Math.pow(potentialPoints[i][j + 1].getValue() - potentialPoints[i][j - 1].getValue(), 2.0);
                }
            }
        }
        calculatePotential();

        return (1.0 / 8.0) * a;
    }

    public Double calculateIntegral() {
        Double currentIntegralValue = calculateIntegralAtIteration();
        Double diff = 0.0;

        int k = 1;
        if (this.iterationIntegralContainer.getMyPairs().size() != 0)
            k = this.iterationIntegralContainer.getMyPairs().get(this.iterationIntegralContainer.getMyPairs().size() - 1).getIteration();
        this.iterationIntegralContainer.add(k++, currentIntegralValue);

        do {
            Double newIntegralValue = calculateIntegralAtIteration();
            this.iterationIntegralContainer.add(k++, newIntegralValue);

            diff = Math.abs(newIntegralValue - currentIntegralValue) / currentIntegralValue;
            currentIntegralValue = newIntegralValue;

        } while (diff > 10e-8);

        return currentIntegralValue;
    }

    public MatrixSpace getMatrixSpace() {
        return matrixSpace;
    }

    public IterationIntegralContainer getIterationIntegralContainer() {
        return iterationIntegralContainer;
    }
}
