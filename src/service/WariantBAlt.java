package service;

import helpers.PotentialPoint;
import helpers.VelocitiesByIteration;

public class WariantBAlt {

    private MatrixSpace matrixSpace;

    private double Q = -1.0;
    private double ni = 1.0;
    private double jump = 0.01;

    private int i1;
    private int i2;

    public WariantBAlt(MatrixSpace matrixSpace, int i1, int i2, double Q) {
        this.matrixSpace = matrixSpace;
        this.jump = matrixSpace.getJump();
        this.i1 = i1;
        this.i2 = i2;
        this.Q = Q;

        evaluateInitialPotential();
        evaluateInitialWirowosc();
    }

    public void evaluateInitialPotential() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        //Wejsce/ wyjscie

        for (int i = 0; i <= 50; i++) {
            potentialPoints[0][i].setValue(
                    calculateInitialPotential(potentialPoints[0][i].getY())
            );

            potentialPoints[400][i].setValue(
                    calculateInitialPotential(potentialPoints[400][i].getY())
            );
        }

        //Krawedz A
        for (int i = 1; i <= this.i1; i++) {
            potentialPoints[i][50].setValue(potentialPoints[0][50].getValue());
        }

        //Krawdz B
        for (int i = 50; i <= 200; i++) {
            potentialPoints[i1][i].setValue(potentialPoints[0][50].getValue());
        }

        //Krawedz C
        for (int i = i1; i <= i2; i++) {
            potentialPoints[i][200].setValue(potentialPoints[0][50].getValue());
        }

        //Krawedz D
        for (int i = 50; i <= 200; i++) {
            potentialPoints[i2][i].setValue(potentialPoints[0][50].getValue());
        }

        //Krawedz E
        for (int i = i2; i < 400; i++) {
            potentialPoints[i][50].setValue(potentialPoints[0][50].getValue());
        }

        //Krawedz F
        for (int i = 1; i < 400; i++) {
            potentialPoints[i][0].setValue(potentialPoints[0][0].getValue());
        }

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    public void evaluateInitialWirowosc() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        // Wejscie/ wyjscie
        for (int i = 0; i <= 50; i++) {
            potentialPoints[0][i].setWir(calculateInitialWir(potentialPoints[0][i].getY()));
            potentialPoints[400][i].setWir(calculateInitialWir(potentialPoints[400][i].getY()));
        }

        //Krawedz A
        for (int i = 1; i < i1; i++) {
            potentialPoints[i][50].setWir(
                    (potentialPoints[i][49].getValue() - potentialPoints[i][50].getValue()) / (2.0 / (jump * jump))
            );
        }

        // Krawedz B
        for (int i = 51; i < 200; i++) {
            potentialPoints[i1][i].setWir(
                    (potentialPoints[i1 + 1][i].getValue() - potentialPoints[i1][i].getValue()) / (2.0 / (jump * jump))
            );
        }

        //Krawedz C
        for (int i = i1 + 1; i < i2 - 1; i++) {
            potentialPoints[i][200].setWir(
                    (potentialPoints[i][199].getValue() - potentialPoints[i][200].getValue()) / (2.0 / (jump * jump))
            );
        }

        //Krawedz D
        for (int i = 51; i < 200; i++) {
            potentialPoints[i2][i].setWir(
                    (potentialPoints[i2 - 1][i].getValue() - potentialPoints[i2][i].getValue()) / (2.0 / (jump * jump))
            );
        }

        //Krawedz E
        for (int i = i2 + 1; i < 400; i++) {
            potentialPoints[i][50].setWir(
                    (potentialPoints[i][49].getValue() - potentialPoints[i][50].getValue())/(2.0/(jump*jump))
            );
        }

        //Krawedz F
        for (int i = 1; i < 400; i++) {
            potentialPoints[i][0].setWir(
                    (potentialPoints[i][1].getValue() - potentialPoints[i][0].getValue())/(2.0/(jump*jump))
            );
        }

        // AB i DE
        potentialPoints[i1][50].setWir(
                (potentialPoints[i1-1][50].getWir() + potentialPoints[i1][51].getWir())/2.0
        );

        potentialPoints[i2][50].setWir(
                (potentialPoints[i2+1][50].getWir() + potentialPoints[i2][51].getWir())/2.0
        );

        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);
    }

    private double calculateInitialPotential(double y) {
        return (this.Q / (2.0 * this.ni)) * (Math.pow(y, 3) / 3.0 - (Math.pow(y, 2.0) / 2.0) * (0.0 + 0.5) + (0.0) * (0.5) * y);
    }

    private double calculateInitialWir(double y) {
        return (this.Q / (2.0 * this.ni)) * (2.0 * y - 0.0 - 0.5);
    }

    public void potentialRelaxationAndWirowosc() {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        for (int i = 1; i < 400; i++) {
            for (int j = 1; j < 200; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()) {
                    Double value = ((potentialPoints[i + 1][j].getWir() +
                            potentialPoints[i - 1][j].getWir() +
                            potentialPoints[i][j - 1].getWir() +
                            potentialPoints[i][j + 1].getWir()) / 4.0) -
                            ((potentialPoints[i][j + 1].getValue() - potentialPoints[i][j - 1].getValue()) *
                                    (potentialPoints[i + 1][j].getWir() - potentialPoints[i - 1][j].getWir()) -
                                    (potentialPoints[i + 1][j].getValue() - potentialPoints[i - 1][j].getValue()) *
                                            (potentialPoints[i][j + 1].getWir() - potentialPoints[i][j - 1].getWir())
                            ) / 16.0;
                    potentialPoints[i][j].setWir(value);

                    Double poten = (((potentialPoints[i + 1][j].getValue() +
                            potentialPoints[i - 1][j].getValue() +
                            potentialPoints[i][j - 1].getValue() +
                            potentialPoints[i][j + 1].getValue() -
                            (potentialPoints[i][j].getWir() * jump * jump))) / 4.0);

                    potentialPoints[i][j].setValue(poten);
                }
            }
        }
        matrixSpace.getDoubleMatrix().setMatrix(potentialPoints);

    }


    private Double calculateIntegralAtIteration() {

        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        Double a = 0.0;

        for (int i = 1; i < 400; i++) {
            for (int j = 1; j < 200; j++) {
                if (!matrixSpace.getDoubleMatrix().getMatrix()[i][j].getObstacle()) {
                    a += ((1.0 / 8.0) *
                            (Math.pow(potentialPoints[i + 1][j].getValue() -
                                    potentialPoints[i - 1][j].getValue(), 2.0) +
                                    Math.pow(potentialPoints[i][j + 1].getValue() -
                                            potentialPoints[i][j - 1].getValue(), 2.0))) -
                            (jump * jump * potentialPoints[i][j].getWir() * potentialPoints[i][j].getValue());
                }
            }
        }
        return a;
    }

    public Double calculateIntegral() {
        potentialRelaxationAndWirowosc();
        System.out.println("Calculating integral");
        Double currentIntegralValue = calculateIntegralAtIteration();
        Double diff = 0.0;

        int k = 1;
        do {
            potentialRelaxationAndWirowosc();
            Double newIntegralValue = calculateIntegralAtIteration();

            diff = Math.abs(newIntegralValue - currentIntegralValue) / currentIntegralValue;
            currentIntegralValue = newIntegralValue;
            System.out.println("Calculating integral diff : " + diff);
        } while (diff > 10e-10);
        System.out.println("Finished calculating integral");

        return currentIntegralValue;
    }

    public PotentialPoint[] generatePotentialAtPoint(int x) {
        PotentialPoint[][] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix();

        return potentialPoints[x];
    }

    private Double evaluateVelocity(Double y) {
        return (Q / (2 * ni)) * (y - 0.0) * (y - 2.0);
    }

    public VelocitiesByIteration generateVelocities() {

        int x = 2;
        VelocitiesByIteration velocitiesByIteration = new VelocitiesByIteration();
        PotentialPoint[] potentialPoints = matrixSpace.getDoubleMatrix().getMatrix()[x];

        for (int i = 1; i < 51; i++) {

            Double velocity = (potentialPoints[i + 1].getValue() - potentialPoints[i - 1].getValue()) / (2 * jump);
            Double velocityAnal = evaluateVelocity(potentialPoints[i].getY());
            velocitiesByIteration.addVelocities(velocity, velocityAnal);

        }
        return velocitiesByIteration;
    }

    public MatrixSpace getMatrixSpace() {
        return matrixSpace;
    }
}
