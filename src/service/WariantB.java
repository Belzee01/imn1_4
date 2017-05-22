package service;

import helpers.IterationIntegralContainer;
import helpers.Obstacle;
import helpers.PotentialPoint;

public class WariantB {
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

    public WariantB(MatrixSpace matrixSpace) {
        System.out.println("Creating wariant B");
        this.matrixSpace = matrixSpace;
        this.iterationIntegralContainer = new IterationIntegralContainer();

        evaluateInitialPotentialEdges();
    }

    public void evaluateInitialPotentialEdges() {

        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        //lewy brzeg
        for (int i = 200; i >= 150; i--) {
            potentialPoints[i][0].setValue(
                    (this.Q/(2.0 * this.ni))/((Math.pow(potentialPoints[i][0].getY(), 3.0)/3.0) - (Math.pow(potentialPoints[i][0].getY(), 2.0)/2.0)*
                            (0.0 + 0.5) + potentialPoints[i][0].getY()*0.0*0.5
                    )
            );
        }

        //prawy brzeg
        for (int i = 200; i >= 150; i--) {
            potentialPoints[i][400].setValue(
                    (this.Q/(2.0 * this.ni))/((Math.pow(potentialPoints[i][400].getY(), 3.0)/3.0) - (Math.pow(potentialPoints[i][400].getY(), 2.0)/2.0)*
                            (0.0 + 0.5) + potentialPoints[i][400].getY()*0.0*0.5
                    )
            );
        }

        //krawedz A
        for (int i = 1; i < 125; i++) {
            potentialPoints[150][i].setValue(
                    potentialPoints[150][0].getValue()
            );
        }

        //krawedz B
        for (int i = 0; i <= 150; i++) {
            potentialPoints[i][125].setValue(
                    potentialPoints[150][0].getValue()
            );
        }

        //krawedz C
        for (int i = 125; i < 275; i++) {
            potentialPoints[0][i].setValue(
                    potentialPoints[150][0].getValue()
            );
        }

        //krawedz D
        for (int i = 0; i <= 150; i++) {
            potentialPoints[i][275].setValue(
                    potentialPoints[150][0].getValue()
            );
        }

        //krawedz E
        for (int i = 275; i < 400; i++) {
            potentialPoints[150][i].setValue(
                    potentialPoints[150][0].getValue()
            );
        }

        //krawedz F
        for (int i = 1; i < 400; i++) {
            potentialPoints[200][i].setValue(
                    potentialPoints[200][0].getValue()
            );
        }

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    public void calculatePotential() {

        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        for (int i = potentialPoints.length - 2; i > 0; i--) {
            for (int j = 1; j < potentialPoints[0].length - 1; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()
                        && !matrixSpace.getDoubleMatrix().getMatrix()[i+1][j].getObstacle()
                        && !matrixSpace.getDoubleMatrix().getMatrix()[i-1][j].getObstacle()
                        && !matrixSpace.getDoubleMatrix().getMatrix()[i][j+1].getObstacle()
                        && !matrixSpace.getDoubleMatrix().getMatrix()[i][j-1].getObstacle()) {
                    Double value = (potentialPoints[i - 1][j].getValue() + potentialPoints[i][j - 1].getValue() +
                            potentialPoints[i + 1][j].getValue() + potentialPoints[i][j + 1].getValue() -
                            potentialPoints[i][j].getWir() * matrixSpace.getJump() * matrixSpace.getJump()
                    ) / 4.0;
                    potentialPoints[i][j].setValue(value);
                }
            }
        }

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    public void calculateWirowosc() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        for (int i = potentialPoints.length - 2; i > 0; i--) {
            for (int j = 1; j < potentialPoints[0].length - 1; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()
                        && !matrixSpace.getDoubleMatrix().getMatrix()[i+1][j].getObstacle()
                        && !matrixSpace.getDoubleMatrix().getMatrix()[i-1][j].getObstacle()
                        && !matrixSpace.getDoubleMatrix().getMatrix()[i][j+1].getObstacle()
                        && !matrixSpace.getDoubleMatrix().getMatrix()[i][j-1].getObstacle()) {
                    Double value = ((potentialPoints[i][j+1].getWir() + potentialPoints[i][j - 1].getWir() +
                            potentialPoints[i + 1][j].getWir() + potentialPoints[i-1][j].getWir()) / 4.0) -
                            (1.0 / 16.0) * (
                                    (potentialPoints[i - 1][j].getValue() - potentialPoints[i + 1][j].getValue()) *
                                            (potentialPoints[i][j + 1].getWir() - potentialPoints[i][j - 1].getWir()) -
                                            (potentialPoints[i][j + 1].getValue() - potentialPoints[i][j - 1].getValue()) *
                                                    (potentialPoints[i - 1][j].getWir() - potentialPoints[i + 1][j].getWir())
                            );

                    potentialPoints[i][j].setWir(value);
                }
            }
        }

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    private Double calculateIntegralAtIteration() {

        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        Double a = 0.0;

        for (int i = potentialPoints.length - 2; i > 0 ; i --) {
            for (int j = 1; j < potentialPoints[0].length - 1; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()) {
                    a += (Math.pow(matrixSpace.getJump(), 2.0) / 2.0) *
                            (Math.pow((potentialPoints[i][j + 1].getValue() - potentialPoints[i][j - 1].getValue())/(2*matrixSpace.getJump()), 2.0) +
                            Math.pow((potentialPoints[i - 1][j].getValue() - potentialPoints[i + 1][j].getValue())/(2*matrixSpace.getJump()), 2.0)) -
                            (Math.pow(matrixSpace.getJump(), 2.0)*potentialPoints[i][j].getWir()*potentialPoints[i][j].getValue());
                }
            }
        }
        return a;
    }

    public Double calculateIntegral() {
        calculateWirowosc();
        calculateWirowosc();
        System.out.println("Calculating integral");
        Double currentIntegralValue = calculateIntegralAtIteration();
        Double diff = 0.0;

        int k = 1;
        if (this.iterationIntegralContainer.getMyPairs().size() != 0)
            k = this.iterationIntegralContainer.getMyPairs().get(this.iterationIntegralContainer.getMyPairs().size() - 1).getIteration();
        this.iterationIntegralContainer.add(k++, currentIntegralValue);

        do {
            calculateWirowosc();
            calculateWirowosc();
            Double newIntegralValue = calculateIntegralAtIteration();
            this.iterationIntegralContainer.add(k++, newIntegralValue);

            diff = Math.abs(newIntegralValue - currentIntegralValue) / currentIntegralValue;
            currentIntegralValue = newIntegralValue;
            System.out.println("Calculating integral diff : " + diff);
        } while (diff > 10e-10);
        System.out.println("Finished calculating integral");

        return currentIntegralValue;
    }

    public MatrixSpace getMatrixSpace() {
        return matrixSpace;
    }

    public IterationIntegralContainer getIterationIntegralContainer() {
        return iterationIntegralContainer;
    }

}
