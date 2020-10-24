import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		Nodo nodo[] = new Nodo[6];

		nodo[0] = new Nodo("A");
		nodo[1] = new Nodo("B");
		nodo[2] = new Nodo("C");
		nodo[3] = new Nodo("D");
		nodo[4] = new Nodo("E");
		nodo[5] = new Nodo("F");

		cargar(nodo);
		mostrar_nodos(nodo);
		// dijkstra(nodo, 0, 4);
		
		String opcion = "";
		System.out.print("Por cual nodo desea empezar ?\n");
		System.out.print("A    B	C	D	E	F");
		opcion = reader.nextLine();
		opcion = opcion.toUpperCase().trim();

		Nodo elElegido = nodo[nodo[0].determinarNumeroNodo(opcion)];
		elElegido.setOrigen(true);
		
		algoritmo(elElegido,nodo);
		mostrarTablaNodos(nodo);

	}

	private static void cargar(Nodo nodo[]) {
		int i = 0;
		try {

			// Scanner input = new Scanner(new
			// File("/home/joel/Escritorio/redes1/ProyectoRedes/ProyectoRedes/LaMejorRuta/tablaNodos.txt"));
			Scanner input = new Scanner(new File("tablaNodos.txt"));

			// Leo por fila -> i

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

	/*
	 * public static void dijkstra(Nodo nodo[], int origen, int numeronodos) { int
	 * largo= (numeronodos - 1 )*2; String matrizcostos[][] = new
	 * String[largo][largo]; String vector[] = new String[largo]; //for(int i=0;
	 * i<numeronodos; i++) //tengo que hacer sumar nodos hasta el numero de nodos -1
	 * int i = 0; //hago la prueba solo para el nodo origen "i"-> indica cuantos
	 * nodos adicinales coloco vector = calcularCostos(origen, nodo, vector, i);
	 * mostrarVector(vector); escribirMatriz(matrizcostos,vector,i);
	 * mostrarMatriz(matrizcostos,largo); }
	 * 
	 * public static String[] calcularCostos(int origen, Nodo nodo[], String
	 * vector[], int nronododisponibles) { Nodo nodosrc = nodo[origen]; String
	 * src,dst; int metrica=0,offset=0; //calculamos las metricas de cada enlace
	 * dentro del nodo for (int j = 0; j < 6; j++) { if
	 * (nodosrc.enlace[j].getNodoDestino() != null) { src =
	 * nodosrc.enlace[j].getNodoOrigen().getNombre(); //obtengo nodo origen dst =
	 * nodosrc.enlace[j].getNodoDestino().getNombre(); //obtengo nodo dst metrica =
	 * nodosrc.enlace[j].getMetrica(); vector[offset] = src + "->" + dst;
	 * vector[offset+1] = metrica+""; offset+=2; } } return vector; }
	 * 
	 * public static void mostrarVector(String vector[]){ for (int j=0;
	 * j<vector.length;j++){ if(vector[j] != null) System.out.println(vector[j]
	 * +"   "+ vector[j+1]); j+=1; } }
	 * 
	 * public static String [][] escribirMatriz(String matrizcostos[][],String
	 * vector[],int cantNodo) {
	 * 
	 * for (int j = 0; j < vector.length; j++) matrizcostos [cantNodo][j] =
	 * vector[j]; return matrizcostos; }
	 * 
	 * public static void mostrarMatriz(String matrizcostos [][],int size){
	 * System.out.println("matriz"); for (int i = 0; i < size; i++) for (int j = 0;
	 * j < size; j++) if(matrizcostos[i][j]!= null)
	 * System.out.println(matrizcostos[i][j]); }
	 */

	public static void algoritmo(Nodo nodoOrigen, Nodo[] nodos) {
		
		List<Enlace> vecinos = nodoOrigen.getVecinos();
		for (int i = 0; i < vecinos.size(); i++) {

			// Ubico el nodo al que apunta el enlace vecino
			int numeroNodo = nodoOrigen.determinarNumeroNodo(vecinos.get(i).getNodoDestino().getNombre());
			// Seteo el peso temporal del nodo
			int pesoTemporal = vecinos.get(i).getMetrica() + nodoOrigen.getPesoFinal();
			//verifico si la ruta que voy a setear es mejor que la anterior
			if (pesoTemporal < nodos[numeroNodo].getPesoTemporal()) {
				nodos[numeroNodo].setPesoTemporal(pesoTemporal);			
				List<Nodo> camino = nodoOrigen.getCamino();
				camino.add(nodoOrigen);	
				nodos[numeroNodo].setCamino(camino);
			}
			
			

		}
		
				
		Nodo nodoBarato = getNodoMasBarato(nodos);
		
		if(nodoBarato.getNombre().equals(""))
			return;
		
		nodoBarato.setRecorrido(true);
		nodoBarato.setPesoFinal(nodoBarato.getPesoTemporal());
			
		algoritmo(nodoBarato,nodos);
	}

	public static Nodo getNodoMasBarato(Nodo[] nodos) {
		Nodo barato = new Nodo("");
	barato.setPesoTemporal(1000000);;
		
		for (int i = 0; i < nodos.length; i++) {
			// si el peso es 0 es porque todavia no fue seteado, ademas no debe ser el de
			// origen porque ese siempre pesa 0
			if ((!nodos[i].isOrigen()) && (nodos[i].getPesoTemporal() < barato.getPesoTemporal()) && (!nodos[i].isRecorrido())) {
				barato = nodos[i];
			}
		}
		return barato;
	}
	
	public static void mostrarTablaNodos(Nodo[] nodo)	{
		System.out.print("\n");
		for(int i = 0 ;i < nodo.length;i++) {
			System.out.print(nodo[i].getNombre() + "\n\t" + "Peso: " + nodo[i].getPesoFinal() + "\n\t" + "Camino: ");
			for(int j = 0;j < nodo[i].getCamino().size();j++) {
				System.out.print(nodo[i].getCamino().get(j).getNombre() + "\t");
			}
			System.out.print("\n");
		}
		
		
	}

}
