public class Enlace {
	
	private Nodo nodoOrigen;
	private Nodo nodoDestino;
	private int metrica;

	public Nodo getNodoOrigen() {
		return nodoOrigen;
	}

	public Nodo getNodoDestino() {
		return nodoDestino;
	}

	public int getMetrica() {
		return metrica;
	}

	public void setNodoOrigen(Nodo nodoOrigen) {
		this.nodoOrigen = nodoOrigen;
	}

	public void setNodoDestino(Nodo nodoDestino) {
		this.nodoDestino = nodoDestino;
	}

	public void setMetrica(int metrica) {
		this.metrica = metrica;
	}
}
