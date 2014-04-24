package jskills;

import lombok.Data;

/**
 * Parameters about the game for calculating the TrueSkill.
 */
@Data public class GameInfo {

    private static final double defaultInitialMean = 25.0;
    private static final double defaultBeta = defaultInitialMean/6.0;
    private static final double defaultDrawProbability = 0.10;
    private static final double defaultDynamicsFactor = defaultInitialMean/300.0;
    private static final double defaultInitialStandardDeviation = defaultInitialMean/3.0;

    private double initialMean;
    private double initialStandardDeviation;
    private double beta;
    private double dynamicsFactor;
    private double drawProbability;

    public GameInfo(double initialMean, double initialStandardDeviation,
            double beta, double dynamicFactor, double drawProbability) {
        this.initialMean = initialMean;
        this.initialStandardDeviation = initialStandardDeviation;
        this.beta = beta;
        this.dynamicsFactor = dynamicFactor;
        this.drawProbability = drawProbability;
    }

    public static GameInfo getDefaultGameInfo() {
        // We return a fresh copy since we have public setters that can mutate state
        return new GameInfo(defaultInitialMean,
                            defaultInitialStandardDeviation,
                            defaultBeta,
                            defaultDynamicsFactor,
                            defaultDrawProbability);
    }
    
    public Rating getDefaultRating() {
    	return new Rating(initialMean, initialStandardDeviation);
    }
}