package business;

public class TransportFactory {

	/**
     * Choisit le transport en fonction du changement d’île et du budget.
     * - Si on change d’île => on prend Boat si le budget le permet, sinon null (on annule)
     * - Sinon => Bus si le budget le permet, sinon Foot
     */
    public static Transport getTransport(Island currentIsland, Island targetIsland, double remainingBudget) {
        // Vérifie si on change d'île
        boolean changingIsland = !currentIsland.getName().equals(targetIsland.getName());

        if (changingIsland) {
            // On veut prendre un Boat pour changer d’île
            double boatThreshold = 20.0; 
            if (remainingBudget < boatThreshold) {
                // Pas assez de budget pour Boat -> annule
                return null;
            } else {
                return new Boat();
            }
        } else {
            // Même île => on veut prendre un Bus
            double busThreshold = 5.0;
            if (remainingBudget < busThreshold) {
                // Pas assez de budget pour Bus -> on prend OnFoot
                return new OnFoot();
            } else {
                return new Bus();
            }
        }
    }
}