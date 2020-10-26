import java.util.ArrayList;
import java.util.List;

public class Nodo {
	
	private String nombre;
	private boolean usado = false;
	private int temporal = 1000;
	private int finall = 100;
	private List<String> ruta = new ArrayList<String>(){};
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

	public void setRuta(List<String> ruta) {
		this.ruta = ruta;
	}

	public List<String> getRuta() {
		return ruta;
	}

	public void setUsed(boolean usado) {
		this.usado = usado;
	}

	public boolean isUsed() {
		return usado;
	}

	public void setTemporal(int temporal) {
		this.temporal = temporal;
	}

	public int getTemporal() {
		return temporal;
	}

	public void setFinall(int finall) {
		this.finall = finall;
	}

	public int getFinall() {
		return finall;
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

    public void mostrarVecinos(){
    	System.out.println("VECINOS");
			for(int j=0;j<6;j++){
				if(enlace[j].getNodoDestino() != null) {
					System.out.println(enlace[j].getNodoDestino().getNombre() +
							"Metrica =" + enlace[j].getMetrica());
				}
			}
	}

	//1) recorre los enlaces que estan conectados a un nodo dts
	//2)Pregunta si es nodo dts ya fue usado
	//3) me fijo si el valor de la columna temporal del nodo al que voy, es menor al
	//   valor de la metrica mas en valor del nodo origen, en caso afirmativo, procedo a setear la tabla
	public Nodo [] setearNodosAdyacentes(Nodo nodo[]){
		for(int j=0;j<6;j++){
			int valorDeEnlace = 0,temporal=0, suma=0;

			if(enlace[j].getNodoDestino() != null && enlace[j].getNodoDestino().isUsed() == false ) {
				valorDeEnlace = enlace[j].getMetrica();					//obtengo el valor del enlace
				temporal = enlace[j].getNodoDestino().getTemporal();	//obtengo el valor temportal

				//System.out.println("El valor temporal del nodo adyacente:"+enlace[j].getNodoDestino().getNombre()+
				//			"es :"+ temporal );
				//System.out.println("EL valor de metrica de "+enlace[j].getNodoOrigen().getNombre() +
				//			"--->"+ enlace[j].getNodoDestino().getNombre() +" es: "+ valorDeEnlace);
				suma=valorDeEnlace+getFinall();		//sumo el valor final de nodo origen + la metrica para llegar al nodo ady
				if(suma < temporal  ){
					enlace[j].getNodoDestino().setTemporal(suma);
					//ruta
					List<String> nueva = new ArrayList<String>(){};
					nueva = copiarListas(nueva,enlace[j].getNodoOrigen().getRuta());	//copio la ruta del anterior nodo
					nueva.add(enlace[j].getNodoOrigen().getNombre());	//coloco el nombre del anterior nodo a su anterior ruta
					enlace[j].getNodoDestino().setRuta(nueva);
				}

			}
		}
		setUsed(true);
		return nodo;
	}
	public List<String> copiarListas(List<String> nueva,List<String> anterior){
		for(int i=0;i<anterior.size();i++){
			nueva.add(anterior.get(i));
		}
		return nueva;
	}
	public void mostrarContenido(){
		System.out.println("Usado = "+ isUsed() + "    Temporal:"+ getTemporal()+
				"    Final = "+ getFinall() + getRuta());
	}

     
}
