package business;

public class VisitTransport {
    private Site site;
    private Transport transport;
    private double durationVisit; // en heures (durée transport + durée visite)
    private double costVisit;         // coût du transport (pricePerKm * distance) + cout de la visite 

    public VisitTransport(Site site, Transport transport, double durationVisit, double costVisit) {
        this.site = site;
        this.transport = transport;
        this.durationVisit = durationVisit;
        this.costVisit = costVisit;
    }

	@Override
	public String toString() {
		return "VisitTransport [site=" + site + ", transport=" + transport + ", durationVisit=" + durationVisit
				+ ", costVisit=" + costVisit + "]";
	}

	public Site getSite() {
		return site;
	}

	public Transport getTransport() {
		return transport;
	}

	public double getDurationVisit() {
		return durationVisit;
	}

	public double getCostVisit() {
		return costVisit;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public void setDurationVisit(double durationVisit) {
		this.durationVisit = durationVisit;
	}

	public void setCostVisit(double costVisit) {
		this.costVisit = costVisit;
	}

    
}