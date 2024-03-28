package asg1;

public class SimulatedAnnealing {
    double MAX_TEMP = 1000;
    double MIN_TEMP = 0;
    double COS_COEF = 3;
    double PASSING_MARK = 0.65;
    double STEP = 1.2;

    double energy(double x) {
        return x * Math.cos(COS_COEF * x);
    }

    boolean accept(double delE, double temp) {
        if (delE > 0) {
            return true;
        } else {
            double boltzmanValue = Math.exp((delE) / temp);
            System.out.println("Boltzman Value : " + boltzmanValue);
            if (boltzmanValue > PASSING_MARK) {
                return true;
            }
            return false;
        }
    }

    boolean isUpdated = true;

    double optimise(double init) {
        double x = init;
        double NRGX = energy(x);
        double t = MAX_TEMP;
        while (t >= MIN_TEMP) {
            double xNew = x + STEP;
            if (isUpdated == false) {
                // xNew += STEP;
                // isUpdated = true;
                break;
            }
            double NRGNEW = energy(xNew);
            double energyDiff = NRGNEW - NRGX;
            System.out.println(
                    "============================================================================================");
            System.out.println("Current Temp : " + t);
            System.out.println("Current X : " + x);
            System.out.println("Current Energy : " + NRGX);
            System.out.println("New X : " + xNew);
            System.out.println("New Energy : " + NRGNEW);
            System.out.println("Delta E : " + energyDiff);
            if (accept(energyDiff, t)) {
                System.out.println("Updtaed");
                x = xNew;
                NRGX = NRGNEW;
            } else {
                isUpdated = false;
            }
            t = t / 15;
            // testBreaker--;
            // if (testBreaker == 0)
            // break;
        }
        return NRGX;
    }

    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing();
        System.out.println(sa.optimise(0.5));
    }
}
