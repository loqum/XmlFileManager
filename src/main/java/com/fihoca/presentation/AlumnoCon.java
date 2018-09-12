package com.fihoca.presentation;

import java.util.Scanner;

import com.fihoca.dao.AlumnoDao;
import com.fihoca.model.Alumno;

public class AlumnoCon {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int op;

		do {
			System.out.println("¿Qué desea realizar? Insertar nuevo alumno (1) Finalizar el programa (2)");

			String num = scanner.nextLine();
			op = Integer.parseInt(num);

			switch (op) {
			case 1:
				System.out.println("Por favor, inserta la id del alumno: ");
				int idAlumno = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Por favor, inserta el nombre del alumno: ");
				String nombre = scanner.nextLine();

				System.out.println("Por favor, inserta el apellido del alumno: ");
				String apellido = scanner.nextLine();

				System.out.println("Por favor, inserta el DNI del alumno: ");
				String dni = scanner.nextLine();

				Alumno alumno = new Alumno(idAlumno, nombre, apellido, dni);
				AlumnoDao alumnoDao = new AlumnoDao();

				try {
					alumnoDao.add(alumno);

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}

				break;

			case 2:
				System.out.println("Fin de la ejecución");
				break;
			default:
				System.out.println("Valor incorrecto");
			}
		} while (op != 2);

		scanner.close();
	}
}
