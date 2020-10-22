
public class Nodo {
	
	private String nombre;   
    Enlace enlace[] = new Enlace[6];
     
    Nodo(String nombre) {
    	this.nombre = nombre;
    	enlace[0]= new Enlace();
    	enlace[1]= new Enlace();
    	enlace[2]= new Enlace();
    	enlace[3]= new Enlace();
        enlace[4]= new Enlace();
		enlace[5]= new Enlace();
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

	public void cargarEnlaces(int posicionNodo, String line, Nodo arrayNodo[]){
		int metrica = 0;
    	//j -> son las columnas
		for (int j = 1; j < line.length(); j++) {
			if(posicionNodo != (j-1)){	//respeto diagonalidad
				if(line.charAt(j) !='0' && line.charAt(j) !='#' ) {
					//seteo metrica de enlace
					metrica = Character.getNumericValue(line.charAt(j));
					arrayNodo[posicionNodo].enlace[j-1].setMetrica(metrica);
					//seteo nodo destino y origen
					arrayNodo[posicionNodo].enlace[j-1].setNodoOrigen(arrayNodo[posicionNodo]);	//seteo el nodo origen
					arrayNodo[posicionNodo].enlace[j-1].setNodoDestino(arrayNodo[j-1]);	//seteo el nodo dst
				}
			}

		}
	}

	public int determinarNumeroNodo(String nombre) {
		
		switch(nombre) {
		
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
	
    public void mostrarVecinos(){
    	System.out.println("VECINOS");
			for(int j=0;j<6;j++){
				if(enlace[j].getNodoDestino() != null) {
					System.out.println(enlace[j].getNodoDestino().getNombre() +
							"Metrica =" + enlace[j].getMetrica());
				}

			}
	}
     
}
