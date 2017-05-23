package service;

import helpers.PotentialAndWirContainer;
import helpers.PotentialPoint;
import helpers.VelocitiesByIteration;

public class WariantAAlt {

    private MatrixSpace matrixSpace;

    double Q = -1.0;
    double ni = 1.0;
    double jump = 0.01;

    public WariantAAlt(MatrixSpace matrixSpace) {
        this.matrixSpace = matrixSpace;

        this.jump = matrixSpace.getJump();

        evaluateInitialEdges();
    }

    private double calculateInitialPotential(double y) {
        return (this.Q / (2.0 * this.ni)) * (Math.pow(y, 3) / 3.0 - (Math.pow(y, 2.0) / 2.0) * (0.0 + 0.9) + (0.0) * (0.9) * y);
    }

    private double calculateInitialWir(double y) {
        return (this.Q / (2.0 * this.ni)) * (2.0 * y - 0.0 - 0.9);
    }

    public void evaluateInitialEdges() {

        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        // Initialize potential
        for (int i = 0; i <= 300; i++) {
            potentialPoints[i][0].setValue(
                    calculateInitialPotential(potentialPoints[i][0].getY())
            );
            potentialPoints[i][90].setValue(
                    calculateInitialPotential(potentialPoints[i][90].getY())
            );
        }

        for (int i = 0; i <= 90; i++) {
            potentialPoints[0][i].setValue(
                    calculateInitialPotential(potentialPoints[0][i].getY())
            );
            potentialPoints[300][i].setValue(
                    calculateInitialPotential(potentialPoints[300][i].getY())
            );
        }


        // Initialize wirowosc
        for (int i = 0; i <= 300; i++) {
            potentialPoints[i][0].setWir(
                    calculateInitialWir(potentialPoints[i][0].getY())
            );
            potentialPoints[i][90].setWir(
                    calculateInitialWir(potentialPoints[i][90].getY())
            );
        }

        for (int i = 0; i <= 90; i++) {
            potentialPoints[0][i].setWir(
                    calculateInitialWir(potentialPoints[0][i].getY())
            );
            potentialPoints[300][i].setWir(
                    calculateInitialWir(potentialPoints[300][i].getY())
            );
        }
        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);

    }

    public void calculatePotential() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();
        for (int i = 1; i < 300; i++) {
            for (int j = 1; j < 90; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()) {
                    Double value = (potentialPoints[i + 1][j].getValue() +
                            potentialPoints[i - 1][j].getValue() +
                            potentialPoints[i][j - 1].getValue() +
                            potentialPoints[i][j + 1].getValue() -
                            potentialPoints[i][j].getWir() * jump * jump) / 4.0;

                    potentialPoints[i][j].setValue(value);
                }
            }
        }
        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    public void calculateWirowosc() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        for (int i = 1; i < 300; i++) {
            for (int j = 1; j < 90; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()) {
                    Double value = ((potentialPoints[i + 1][j].getWir() +
                            potentialPoints[i - 1][j].getWir() +
                            potentialPoints[i][j - 1].getWir() +
                            potentialPoints[i][j + 1].getWir()) / 4.0) -
                            ((potentialPoints[i][j + 1].getValue() - potentialPoints[i][j - 1].getValue()) *
                                    (potentialPoints[i + 1][j].getWir() - potentialPoints[i - 1][j].getWir()) -
                                    (potentialPoints[i + 1][j].getValue() - potentialPoints[i - 1][j].getValue()) *
                                            (potentialPoints[i][j + 1].getWir() - potentialPoints[i][j - 1].getWir()) / 16.0
                            );
                    potentialPoints[i][j].setWir(value);
                }
            }
        }
        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);

    }

    private Double evaluateVelocity(Double y) {
        return (Q / (2 * ni)) * (y - 0.0) * (y - 0.9);
    }

    public void executeTaskOne() {
        int x = 145;
        int y = 45;
        Double diffStr = 0.0;
        Double diffWir = 0.0;
        int k = 0;

        for (int i = 0; i < 1000; i++) {
            calculateWirowosc();
            calculatePotential();
        }

        PotentialPoint potentialPoint = new PotentialPoint(matrixSpace.getDoubleMatrix().getMatrix()[x][y]);
        do {

            //policz
            calculateWirowosc();
            calculatePotential();

            PotentialPoint newPoint = new PotentialPoint(matrixSpace.getDoubleMatrix().getMatrix()[x][y]);

            diffStr = Math.abs((potentialPoint.getValue() - newPoint.getValue()));
            diffWir = Math.abs((potentialPoint.getWir() - newPoint.getWir()));

            potentialPoint = new PotentialPoint(newPoint);
            System.out.println("Iteration:" + k++ + " diffStr: " + diffStr + " diffWir: " + diffWir);
        }
        while (diffStr > 10e-7 || diffWir > 10e-7);
    }

    public VelocitiesByIteration generateVelocities() {

        int x = 50;
        VelocitiesByIteration velocitiesByIteration = new VelocitiesByIteration();
        PotentialPoint[] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix()[x];

        for (int i = 1; i < 90; i++) {

            Double velocity = (potentialPoints[i + 1].getValue() - potentialPoints[i - 1].getValue()) / (2 * jump);
            Double velocityAnal = evaluateVelocity(potentialPoints[i].getY());
            velocitiesByIteration.addVelocities(velocity, velocityAnal);

        }
        return velocitiesByIteration;
    }

    public PotentialAndWirContainer evaluatePotencjalAndWirowoscAt(int x) {

        PotentialAndWirContainer potentialAndWirContainer = new PotentialAndWirContainer();
        PotentialPoint[] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix()[x];

        for (int i = 0; i <= 90; i++) {

            Double wirAnal = (Q / (2.0 * ni)) * (2.0 * potentialPoints[i].getY() - 0.0 - 0.9);
            Double potenAnal = (Q / (2.0 * ni)) * (Math.pow(potentialPoints[i].getY(), 3.0) / 3.0 - (Math.pow(potentialPoints[i].getY(), 2.0) / 2.0) * (0.0 + 0.9) + (0.0 * 0.9 * potentialPoints[i].getY()));

            potentialAndWirContainer.addPoint(i + 1, potentialPoints[i].getValue(), potenAnal, potentialPoints[i].getWir(), wirAnal);
        }

        return potentialAndWirContainer;
    }

    public MatrixSpace getMatrixSpace() {
        return matrixSpace;
    }
}
