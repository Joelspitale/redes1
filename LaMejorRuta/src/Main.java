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
			Scanner input = new Scanner(new File("/home/joel/Escritorio/redes1/ProyectoRedes/ProyectoRedes/LaMejorRuta/tablaNodos.txt"));
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
		int matrizcostos[][] = new int[numeronodos - 1][numeronodos - 1];
		int vector[] = new int[numeronodos - 1];
		//for(int i=0; i<numeronodos; i++)	//tengo que hacer sumar nodos hasta el numero de nodos -1
		int i = 0;    //hago la prueba solo para el nodo origen
		calcularCostos(origen, nodo, vector, i);
	}

	public static void calcularCostos(int origen, Nodo nodo[], int vector[], int nronododisponibles) {
		Nodo nodosrc = nodo[origen];
		//calculamos las metricas de cada enlace
		for (int j = 0; j < 6; j++) {
			if (nodosrc.enlace[j].getNodoDestino() != null) {
				System.out.println(nodosrc.enlace[j].getNodoDestino().getNombre() +
						nodosrc.enlace[j].getMetrica());
			}
		}
	}
}
