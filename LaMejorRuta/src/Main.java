import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);

		// Por defecto hay 6 nodos, los inicializo
		Nodo nodo[] = new Nodo[6];

		nodo[0] = new Nodo("A");
		nodo[1] = new Nodo("B");
		nodo[2] = new Nodo("C");
		nodo[3] = new Nodo("D");
		nodo[4] = new Nodo("E");
		nodo[5] = new Nodo("F");

		cargar(nodo);
		String opcion = "";
		do {
			System.out.print("\n");
			System.out.print("Elija la opcion que desee\n");
			System.out.print("Tumbar un Nodo: 1 \n");
			System.out.print("Cambiar Metricas: 2 \n");
			System.out.print("calcular Metricas: 3 \n");
			System.out.print("Mostrar los nodos con sus respectivos vecinos y metricas: 4 \n");
			System.out.print("Terminar: 0 \n");
			opcion = reader.nextLine();

			switch (opcion) {
			case "1":
				tumbarNodo(nodo);
				break;
			case "2":
				cambiarMetricas(nodo);
				break;
			case "3":
				calcularRutas(nodo);
				break;
			case "4":
				mostrar_nodos(nodo);
				break;
			case "0":
				break;

			}

			reset(nodo);
		} while (!opcion.equals("0"));

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
			System.out.println("Nodo : " + nodo[i].getNombre());
			nodo[i].mostrarVecinos();
		}
	}

	public static void algoritmo(Nodo nodoOrigen, Nodo[] nodos) {

		List<Enlace> vecinos = nodoOrigen.getVecinos();
		for (int i = 0; i < vecinos.size(); i++) {

			// Ubico el nodo al que apunta el enlace vecino
			int numeroNodo = nodoOrigen.determinarNumeroNodo(vecinos.get(i).getNodoDestino().getNombre());
			// Seteo el peso temporal del nodo
			int pesoTemporal = vecinos.get(i).getMetrica() + nodoOrigen.getPesoFinal();
			// verifico si la ruta que voy a setear es mejor que la anterior
			if (pesoTemporal < nodos[numeroNodo].getPesoTemporal() && (!nodos[numeroNodo].isOrigen())) {
				nodos[numeroNodo].setPesoTemporal(pesoTemporal);
				List<Nodo> camino = new ArrayList();

				// este "for" fue implementado para pasar la ruta por valor
				for (int j = 0; j < nodoOrigen.getCamino().size(); j++) {
					Nodo aux = new Nodo("");
					aux.setNombre(nodoOrigen.getCamino().get(j).getNombre());
					camino.add(aux);
				}

				camino.add(nodoOrigen);
				nodos[numeroNodo].setCamino(camino);
			}

		}

		Nodo nodoBarato = getNodoMasBarato(nodos);

		if (nodoBarato.getNombre().equals(""))
			return;

		nodoBarato.setRecorrido(true);
		nodoBarato.setPesoFinal(nodoBarato.getPesoTemporal());

		algoritmo(nodoBarato, nodos);
	}

	public static Nodo getNodoMasBarato(Nodo[] nodos) {
		Nodo barato = new Nodo("");
		barato.setPesoTemporal(1000000);

		for (int i = 0; i < nodos.length; i++) {
			// si el peso es 0 es porque todavia no fue seteado, ademas no debe ser el de
			// origen porque ese siempre pesa 0
			if ((!nodos[i].isOrigen()) && (nodos[i].getPesoTemporal() < barato.getPesoTemporal())
					&& (!nodos[i].isRecorrido())) {
				barato = nodos[i];
			}
		}
		return barato;
	}

	public static void mostrarTablaNodos(Nodo[] nodo) {
		System.out.print("\n");
		for (int i = 0; i < nodo.length; i++) {
			System.out.print(nodo[i].getNombre() + "\n\t" + "Peso: " + nodo[i].getPesoFinal() + "\n\t" + "Camino: ");
			for (int j = 0; j < nodo[i].getCamino().size(); j++) {
				System.out.print(nodo[i].getCamino().get(j).getNombre() + "\t");
			}
			System.out.print("\n");
		}
	}

	public static void tumbarNodo(Nodo[] nodos) {
		Scanner reader = new Scanner(System.in);
		String opcion = "";
		System.out.print("Que nodo desea tumbar ?\n");
		System.out.print("A    B	C	D	E	F");
		opcion = reader.nextLine();
		opcion = opcion.toUpperCase().trim();

		for (int i = 0; i < nodos.length; i++) {
			for (int j = 0; j < nodos[i].enlace.length; j++) {
				if (nodos[i].enlace[j].getNodoDestino() != null)
					if (nodos[i].enlace[j].getNodoDestino().getNombre().equals(opcion))
						// System.out.print(nodos[i].getNombre());
						nodos[i].enlace[j].setNodoDestino(null);
			}
		}
		for (int i = 0; i < nodos.length; i++) {
			nodos[nodos[0].determinarNumeroNodo(opcion)].enlace[i].setNodoDestino(null);
		}

		System.out.print("Nodo Tumbado\n");
	}

	public static void reset(Nodo[] nodos) {

		for (int i = 0; i < nodos.length; i++) {
			nodos[i].setPesoFinal(0);
			nodos[i].setPesoTemporal(1000);
			nodos[i].setOrigen(false);
			nodos[i].setRecorrido(false);
			List<Nodo> aux = new ArrayList<Nodo>();
			nodos[i].setCamino(aux);
		}
	}

	public static void calcularRutas(Nodo[] nodo) {
		Scanner reader = new Scanner(System.in);
		String opcion = "";
		System.out.print("Por cual nodo desea empezar ?\n");
		System.out.print("A    B	C	D	E	F");
		opcion = reader.nextLine();
		opcion = opcion.toUpperCase().trim();

		Nodo elElegido = nodo[nodo[0].determinarNumeroNodo(opcion)];
		elElegido.setOrigen(true);

		algoritmo(elElegido, nodo);
		mostrarTablaNodos(nodo);
		System.out.print("\n");
	}

	public static void cambiarMetricas(Nodo[] nodo) {

		Scanner reader = new Scanner(System.in);
		String opcion = "";
		int metricaNueva = 0;
		System.out.print("Cual es el nodo de origen del enlace ?\n");
		System.out.print("A    B	C	D	E	F");
		opcion = reader.nextLine();
		opcion = opcion.toUpperCase().trim();

		List<Enlace> vecinos = nodo[nodo[0].determinarNumeroNodo(opcion)].getVecinos();

		for (int i = 0; i < vecinos.size(); i++) {
			System.out.print("La metrica con el nodo" + vecinos.get(i).getNodoDestino().getNombre() + "es de: "
					+ vecinos.get(i).getMetrica() + "\n");
			System.out.print("Desea cambiarla ?   SI(S)-NO(N) ");
			opcion = reader.nextLine();
			opcion = opcion.toUpperCase().trim();
			if(opcion.equals("S")) {
				System.out.print("Introduzca la nueva metrica");
				 metricaNueva = reader.nextInt();
				 vecinos.get(i).setMetrica(metricaNueva);
			}
		}

	}

}
