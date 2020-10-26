import javax.annotation.processing.SupportedSourceVersion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Nodo nodo[] = new Nodo[6];
		Scanner in = new Scanner(System.in);

		nodo[0] = new Nodo("A");
		nodo[1] = new Nodo("B");
		nodo[2] = new Nodo("C");
		nodo[3] = new Nodo("D");
		nodo[4] = new Nodo("E");
		nodo[5] = new Nodo("F");

		cargar(nodo);
		mostrar_nodosYvecinos(nodo);
		System.out.println("Nombres de los nodos son : A	B	C	D	E	F");
		System.out.println("ingrese el nombre del nodo por el que desea comenzar");
		String origen_palabras = in.nextLine();
		origen_palabras = origen_palabras.toUpperCase();
		int origen = saberNumeroNodo(origen_palabras);
		System.out.println("comienzo por el :"+ origen);
		long startTime = System.nanoTime();
		dijkstra(nodo, origen);
		long endTime = System.nanoTime();
		System.out.println("Usando iteracio  se demoro :"+ ((endTime-startTime)/1e6) +" milisegundos");
		mostrarNodos(nodo);
	}

	private static void cargar(Nodo nodo[]) {
		int i = 0;
		try {
			Scanner input = new Scanner(new File("/home/joel/Escritorio/redes1/LaMejorRuta/tablaNodos.txt"));
			//Leo por fila -> i

			while (input.hasNextLine()) {
				String line = input.nextLine();
				if (i > 0) {
					nodo[i - 1].cargarEnlaces(i - 1, line, nodo);
				}
				i++;
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void mostrar_nodosYvecinos(Nodo nodo[]) {
		for (int i = 0; i < 6; i++) {
			System.out.println("Nodo :" + nodo[i].getNombre());
			nodo[i].mostrarVecinos();
		}
	}

	public static void dijkstra(Nodo nodo[], int origen) {
		//paso 1 y 2 -> marque el cero en la tabla
		nodo[origen].setTemporal(0);
		nodo[origen].setFinall(0);
		int posicion = origen;
		while(true) {
			nodo = setearValoresNodosAdyacentes(posicion, nodo);
			if (menorTemporal(nodo) == null) {		//se finaliza cuando no tengo mas nuevos nodos sin usar osea cuando devuelve null
				moverTemporalHaciaFinal(nodo, posicion);
				System.out.println("finalizo el algoritmo");
				return;
			}
		//System.out.println("Me muevo al nodo:" + menorTemporal(nodo));
		posicion = saberNumeroNodo(menorTemporal(nodo));
		//System.out.println("Me muevo al nodo:" + posicion);
		moverTemporalHaciaFinal(nodo, posicion);
		}


	}
	public static void moverTemporalHaciaFinal(Nodo nodo[],int posicion){
		nodo[posicion].setFinall(nodo[posicion].getTemporal());
		nodo[posicion].setUsed(true);
	}

	public static Nodo[] setearValoresNodosAdyacentes(int posicion,Nodo nodo[]){

		return nodo[posicion].setearNodosAdyacentes(nodo);
	}

	public static String[] calcularCostos(int origen, Nodo nodo[], String vector[], int nronododisponibles) {
		Nodo nodosrc = nodo[origen];
		String src,dst;
		int metrica=0,offset=0;
		//calculamos las metricas de cada enlace dentro del nodo
		for (int j = 0; j < 6; j++) {
			if (nodosrc.enlace[j].getNodoDestino() != null) {
				src = nodosrc.enlace[j].getNodoOrigen().getNombre();	//obtengo nodo origen
				dst = nodosrc.enlace[j].getNodoDestino().getNombre();	//obtengo nodo dst
				metrica = nodosrc.enlace[j].getMetrica();
				vector[offset] = src + "->" + dst;
				vector[offset+1] = metrica+"";
				offset+=2;
			}
		}
		return vector;
	}

	public static void mostrarNodos(Nodo nodo[]){
		for (int j=0;j<6;j++){
			System.out.println("Nodo: "+nodo[j].getNombre());
			nodo[j].mostrarContenido();
		}
	}

	public static String menorTemporal(Nodo nodo[]){
		int aux_campo_temp = 9191919;
		String nombre_next_nodo = null;

		for (int j=0;j<6;j++){

			if(nodo[j].isUsed() == false &&
					nodo[j].getTemporal() != 1000 ) {
					if (nodo[j].getTemporal() < aux_campo_temp) {
						aux_campo_temp = nodo[j].getTemporal();
						nombre_next_nodo = nodo[j].getNombre();
					}
				}
			}
		return nombre_next_nodo;
	}

	public static int saberNumeroNodo(String nombre) {
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
				return -1;
		}
	}
}
