import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Nodo nodo[] = new Nodo[6];

		nodo[0] = new Nodo("A");
		nodo[1] = new Nodo("B");
		nodo[2] = new Nodo("C");
		nodo[3] = new Nodo("D");
		nodo[4] = new Nodo("E");
		nodo[5] = new Nodo("F");

		cargar(nodo);
		mostrar_nodos(nodo);
		dijkstra(nodo, 0, 4);

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

	private static void mostrar_nodos(Nodo nodo[]) {
		for (int i = 0; i < 6; i++) {
			System.out.println("Nodo :" + nodo[i].getNombre());
			nodo[i].mostrarVecinos();
		}
	}

	public static void dijkstra(Nodo nodo[], int origen, int numeronodos) {
		int largo= (numeronodos - 1 )*2;
		String matrizcostos[][] = new String[largo][largo];
		String vector[] = new String[largo];
		//for(int i=0; i<numeronodos; i++)	//tengo que hacer sumar nodos hasta el numero de nodos -1
		int i = 0;    //hago la prueba solo para el nodo origen "i"-> indica cuantos nodos adicinales coloco
		vector = calcularCostos(origen, nodo, vector, i);
		mostrarVector(vector);
		escribirMatriz(matrizcostos,vector,i);
		mostrarMatriz(matrizcostos,largo);
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

	public static void mostrarVector(String vector[]){
		for (int j=0; j<vector.length;j++){
			if(vector[j] != null)
				System.out.println(vector[j] +"   "+ vector[j+1]);
			j+=1;
		}
	}

	public static String [][] escribirMatriz(String matrizcostos[][],String vector[],int cantNodo) {

		for (int j = 0; j < vector.length; j++)
			matrizcostos [cantNodo][j] = vector[j];
		return matrizcostos;
	}

	public static void mostrarMatriz(String matrizcostos [][],int size){
		System.out.println("matriz");
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if(matrizcostos[i][j]!= null)
					System.out.println(matrizcostos[i][j]);
	}
}
