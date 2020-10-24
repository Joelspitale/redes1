import java.util.ArrayList;
import java.util.List;

public class Nodo {

	private String nombre;
	Enlace enlace[] = new Enlace[6];
	private int pesoFinal = 0;
	private int pesoTemporal = 1000;
	private boolean origen = false;
	private boolean recorrido = false;
	private List<Nodo> camino = new ArrayList<Nodo>();

	Nodo(String nombre) {
		this.nombre = nombre;
		enlace[0] = new Enlace();
		enlace[1] = new Enlace();
		enlace[2] = new Enlace();
		enlace[3] = new Enlace();
		enlace[4] = new Enlace();
		enlace[5] = new Enlace();
	}

	public boolean isRecorrido() {
		return recorrido;
	}

	public void setRecorrido(boolean recorrido) {
		this.recorrido = recorrido;
	}

	public List<Nodo> getCamino() {
		
		List<Nodo> nodo = new ArrayList<Nodo>();
		
		for(int i=0;i<this.camino.size(); i ++)
		nodo.add(this.camino.get(i));
		
		return camino;
	}

	public void setCamino(List<Nodo> camino) {
		this.camino = camino;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setEnlace(Enlace[] enlace) {
		this.enlace = enlace;
	}

	public Enlace[] getEnlace() {
		return enlace;
	}

	public int getPesoFinal() {
		return pesoFinal;
	}

	public void setPesoFinal(int pesoFinal) {
		this.pesoFinal = pesoFinal;
	}

	public int getPesoTemporal() {
		return pesoTemporal;
	}

	public void setPesoTemporal(int pesoTemporal) {
		this.pesoTemporal = pesoTemporal;
	}

	public boolean isOrigen() {
		return origen;
	}

	public void setOrigen(boolean origen) {
		this.origen = origen;
	}

	public void cargarEnlaces(int posicionNodo, String line, Nodo arrayNodo[]) {
		int metrica = 0;
		// j -> son las columnas
		for (int j = 1; j < line.length(); j++) {
			if (posicionNodo != (j - 1)) { // respeto diagonalidad
				if (line.charAt(j) != '0' && line.charAt(j) != '#') {
					// seteo metrica de enlace
					metrica = Character.getNumericValue(line.charAt(j));
					arrayNodo[posicionNodo].enlace[j - 1].setMetrica(metrica);
					// seteo nodo destino y origen
					arrayNodo[posicionNodo].enlace[j - 1].setNodoOrigen(arrayNodo[posicionNodo]); // seteo el nodo
																									// origen
					arrayNodo[posicionNodo].enlace[j - 1].setNodoDestino(arrayNodo[j - 1]); // seteo el nodo dst
				}
			}

		}
	}

	public int determinarNumeroNodo(String nombre) {

		switch (nombre) {

		case "A":
			return 0;
		case "B":
			return 1;
		case "C":
			return 2;
		case "D":
			return 3;
		case "E":
			return 4;
		case "F":
			return 5;
		default:
			return 0;
		}
	}

	public void mostrarVecinos() {
		System.out.println("VECINOS");
		for (int j = 0; j < 6; j++) {
			if (enlace[j].getNodoDestino() != null) {
				System.out.println(enlace[j].getNodoDestino().getNombre() + "Metrica =" + enlace[j].getMetrica());
			}

		}
	}

	public List<Enlace> getVecinos() {
		List<Enlace> vecinos = new ArrayList<Enlace>();
		for (int j = 0; j < 6; j++) {
			if (enlace[j].getNodoDestino() != null) {
				vecinos.add(enlace[j]);
			}
		}
		return vecinos;
	}

}
