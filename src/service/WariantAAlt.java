package service;

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

    public void evaluateInitialEdges() {

        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        // Initialize potential
        for (int i = 0; i <= 300; i++) {
            potentialPoints[i][0].setValue(
                    (this.Q / (2.0 * this.ni)) * (Math.pow(0.0, 3) / 3.0 - Math.pow(0.0, 2) * (0.0 + 0.9) / 2.0 + (0.0) * (0.9) * 0.0)
            );
            potentialPoints[i][90].setValue(
                    (this.Q / (2.0 * this.ni)) * (Math.pow(0.0, 3) / 3.0 - Math.pow(0.0, 2) * (0.0 + 0.9) / 2.0 + (0.0) * (0.9) * 0.0)
            );
        }

        for (int i = 0; i <= 90; i++) {
            potentialPoints[0][i].setValue(
                    (this.Q / (2.0 * this.ni)) * (Math.pow(i * jump, 3) / 3.0 - Math.pow(i * jump, 2) * (0.0 + 0.9) / 2.0 + (0.0) * (0.9) * i)
            );
            potentialPoints[300][i].setValue(
                    (this.Q / (2.0 * this.ni)) * (Math.pow(i * jump, 3) / 3.0 - Math.pow(i * jump, 2) * (0.0 + 0.9) / 2.0 + (0.0) * (0.9) * i)
            );
        }


        // Initialize wirowosc
        for (int i = 0; i <= 300; i++) {
            potentialPoints[i][0].setWir(
                    (this.Q / (2.0 * this.ni)) * (2.0 * 0.0 - (0.0) - (0.9))
            );
            potentialPoints[i][90].setWir(
                    (this.Q / (2.0 * this.ni)) * (2.0 * 0.9 - (0.0) - (0.9))
            );
        }

        for (int i = 0; i <= 90; i++) {
            potentialPoints[0][i].setWir(
                    (this.Q / (2.0 * this.ni)) * (2.0 * i * jump - (0.0) - (0.9))
            );
            potentialPoints[300][i].setWir(
                    (this.Q / (2.0 * this.ni)) * (2.0 * i * jump - (0.0) - (0.9))
            );
        }
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
                            (1.0 / 16.0) * (
                                    (potentialPoints[i][j + 1].getValue() - potentialPoints[i][j - 1].getValue()) *
                                            (potentialPoints[i + 1][j].getWir() - potentialPoints[i - 1][j].getWir()) -
                                            (potentialPoints[i + 1][j].getValue() - potentialPoints[i - 1][j].getValue()) *
                                                    (potentialPoints[i][j + 1].getWir() - potentialPoints[i][j - 1].getWir())
                            );
                    potentialPoints[i][j].setWir(value);
                }
            }
        }

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

        for (int i = 0; i < 4000; i++) {
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

            potentialPoint = newPoint;
            System.out.println("Iteration:" + k++ + " diffStr: " + diffStr + " diffWir: " + diffWir);
        }
        while (diffStr > 10e-7 || diffWir > 10e-7);
    }

    public VelocitiesByIteration generateVelocities() {

        int x = 50;
        VelocitiesByIteration velocitiesByIteration = new VelocitiesByIteration();
        PotentialPoint[] potentialPoints;
        for (int i = 1; i < 90; i++) {
            potentialPoints = matrixSpace.getDoubleMatrix().getMatrix()[x];

            Double velocity = (matrixSpace.getDoubleMatrix().getMatrix()[x][i-1].getValue() - matrixSpace.getDoubleMatrix().getMatrix()[x][i+1].getValue()) / (2*jump);
            Double velocityAnal = evaluateVelocity(potentialPoints[i].getY());
            velocitiesByIteration.addVelocities(velocity, velocityAnal);

        }
        return velocitiesByIteration;
    }

    public MatrixSpace getMatrixSpace() {
        return matrixSpace;
    }
}
